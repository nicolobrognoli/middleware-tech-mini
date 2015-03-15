package it.polimi.mini;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

import net.jini.core.discovery.LookupLocator;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.lease.Lease;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

public class Chat {

	private JavaSpace js;
	private String username;
	
	public static void main(String[] args) throws IOException {
		InputStreamReader reader = new InputStreamReader (System.in);
		BufferedReader systemInput = new BufferedReader (reader);
		String choice = "", username = ""; 
		int c = 0;
		boolean registered, failed;
		Chat chat = new Chat();
		chat.discoverJavaSpace();
		//register the user to the system
		do{
			System.out.println("Insert username: ");	
			username = systemInput.readLine();
			registered = chat.registerUser(username);
		}while(!registered);		
		do{
			System.out.println("Select the action:");
			System.out.println("\t1 - Create chat room");
			System.out.println("\t2 - Enter existing chat room");
			System.out.println("\t3 - Delete chat room");
			System.out.println("\t0 - Exit");
			
			choice = systemInput.readLine();
			c =  getChoice(choice);
			switch(c){
				case 1:
				{
					chat.createChatRoom();
					break;
				}
				case 2:
				{
					failed = chat.enterChatRoom();
					if(failed)
						System.out.println("Chat room doesn't exist.");
					break;
				}
				case 3:
				{
					chat.deleteChatRoom();
					break;
				}
			}
		}while(c != 0);
		//delete the user from the system
        chat.deleteAllChatroom(username);
		chat.deleteUser(username);
		System.exit(0);
	}
		

	private void deleteUser(String username) {
		UserEntry userTemplate = new UserEntry(username);
		try {
			this.js.take(userTemplate, null, Lease.FOREVER);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (UnusableEntryException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean registerUser(String username) {
		try {
			UserEntry userTemplate = new UserEntry(username);
			UserEntry user = (UserEntry) js.readIfExists(userTemplate, null, Lease.FOREVER);
			if(user == null)
			{
				js.write(userTemplate, null, Lease.FOREVER);
				this.username = username;
				System.out.println("User created.");
				return true;
			}
			else
			{
				System.out.println("User already exists.");
				return false;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (UnusableEntryException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		return false;		
	}

	public static int getChoice(String str) {
		if(str.equals("1"))
			return 1;
		if(str.equals("2"))
			return 2;
		if(str.equals("3"))
			return 3;
		if(str.equals("0"))
			return 0;
		return -1;
	}

	private void discoverJavaSpace() {
		while (js==null) {
			try {
				LookupLocator locator = new LookupLocator("jini://localhost");
				ServiceRegistrar registrar = locator.getRegistrar();
				js = (JavaSpace) registrar.lookup(
						new ServiceTemplate(null, new Class[] {JavaSpace.class}, null));
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}
	
	private void createChatRoom() throws IOException{
		InputStreamReader reader = new InputStreamReader (System.in);
		BufferedReader systemInput = new BufferedReader (reader);
		System.out.println("Specify the name of the new chat room: ");
		String input = systemInput.readLine();
		ChatRoomEntry chatRoom;
		try {
			ChatRoomEntry template = new ChatRoomEntry(input, null, null);
			chatRoom = (ChatRoomEntry) js.readIfExists(template, null, Lease.FOREVER);
			if(chatRoom == null){
				template = new ChatRoomEntry(input, this.username, 0);
				js.write(template, null, Lease.FOREVER);
				System.out.println("Chat Room created.");
				ChatManagerEntry chatmanager=new ChatManagerEntry(),templateManager=new ChatManagerEntry(null);
				chatmanager=(ChatManagerEntry)js.takeIfExists(templateManager, null, 1000);
				if(chatmanager==null)
					chatmanager=new ChatManagerEntry();
				chatmanager.chatrooms.add(template.name);
				js.write(chatmanager, null, Lease.FOREVER);
			}
			else
				System.out.println("Chat Room already exists.");		
		} catch (UnusableEntryException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private boolean enterChatRoom() throws IOException{
		boolean failed = true;
		try {			
			String chatRoom,message;
			int i;
			InputStreamReader reader = new InputStreamReader (System.in);
			BufferedReader systemInput = new BufferedReader (reader);
			
			ChatManagerEntry templateManager=new ChatManagerEntry(null),chatmanager;
			chatmanager=(ChatManagerEntry)js.take(templateManager, null, Lease.FOREVER);
			
			js.write(chatmanager, null, Lease.FOREVER);
			
			for(i=0;i<chatmanager.chatrooms.size();i++)
				System.out.println("Chatroom "+i+" : "+chatmanager.chatrooms.get(i));
			
			System.out.println("Specify the chatroom name: ");
			chatRoom = systemInput.readLine();
			ChatRoomEntry template = new ChatRoomEntry(chatRoom, null, null);
			ChatRoomEntry chat;		
			chat = (ChatRoomEntry) js.readIfExists(template, null, Lease.FOREVER);			
			if(chat != null){
				//correctly accessed the chatroom
				failed = false;
				System.out.println("How many messages do you want from this chat room ?");
				int n=Integer.parseInt(systemInput.readLine());
				ChatRoomEntry chatRoomObject = (ChatRoomEntry) js.take(template, null, Lease.FOREVER);	
				if(n>chatRoomObject.msgNumber)
					n=chatRoomObject.msgNumber;
				for(i=0;i<n;i++){
					MessageEntry messageTemplate=new MessageEntry(null,chatRoom,null,(Integer)chatRoomObject.msgNumber-n+i);
					MessageEntry messageObj=(MessageEntry)js.read(messageTemplate, null,Lease.FOREVER);
					messageObj.printMessage();
				}
				js.write(chatRoomObject, null, Lease.FOREVER);	
				ChatReader chatReader=new ChatReader(js,chatRoom,this.username);
				Thread t = new Thread(chatReader);
		        t.start();
				do{
					message=systemInput.readLine();
					if(!message.equals("exit")){					
						template = new ChatRoomEntry(chatRoom, null, null);
						chatRoomObject = (ChatRoomEntry) js.take(template, null, 2000);
						//if chatRoomObject is null then the chatroom has been deleted
						if(chatRoomObject!=null){
							MessageEntry messageObject=new MessageEntry(message,chatRoom,this.username, chatRoomObject.msgNumber);							
							chatRoomObject.msgNumber++;
							js.write(messageObject, null, Lease.FOREVER);
							js.write(chatRoomObject, null, Lease.FOREVER);		
						}
					}
				}while(!message.equals("exit") && chatRoomObject!=null);				
				chatReader.exit();
			}			
		} catch (UnusableEntryException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		return failed;
	}
	
	private void deleteChatRoom()throws IOException{
		int i;
		ChatRoomEntry chatRoom;
		InputStreamReader reader = new InputStreamReader (System.in);
		BufferedReader systemInput = new BufferedReader (reader);
		try {
			ChatManagerEntry templateManager=new ChatManagerEntry(null),chatmanager;
			chatmanager=(ChatManagerEntry)js.take(templateManager, null, Lease.FOREVER);
			
			js.write(chatmanager, null, Lease.FOREVER);
			
			for(i=0;i<chatmanager.chatrooms.size();i++)
				System.out.println("Chatroom "+i+" : "+chatmanager.chatrooms.get(i));
			
			System.out.println("Select a chat room to delete: ");
			String input = systemInput.readLine();
			
			ChatRoomEntry template = new ChatRoomEntry(input, this.username, null);
			chatRoom = (ChatRoomEntry) js.take(template, null, 2000);	
			if(chatRoom != null){
				MessageEntry messageObject=new MessageEntry("Chatroom deleted, press enter to return to the menu...",chatRoom.name,this.username, chatRoom.msgNumber);
				js.write(messageObject, null, Lease.FOREVER);
				templateManager=new ChatManagerEntry(null);
				chatmanager=(ChatManagerEntry)js.take(templateManager, null, Lease.FOREVER);
				System.out.println("Rimosso: "+chatmanager.chatrooms.remove(chatRoom.name));
				js.write(chatmanager, null, Lease.FOREVER);
				System.out.println("ChatRoom deleted");
				CleanThread clean = new CleanThread(this.js, chatRoom.name);
				Thread th = new Thread(clean);
				th.start();
			}
			else
				System.out.println("Chat Room not exists or you are not the creator of this chat room");	

		} catch (UnusableEntryException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	private void deleteAllChatroom(String username) {
		ChatRoomEntry chatRoom;
		boolean finished=false;
		try {
			do{
			ChatRoomEntry template = new ChatRoomEntry(null, this.username, null);
			chatRoom = (ChatRoomEntry) js.take(template, null, 2000);	
			if(chatRoom != null){
				MessageEntry messageObject=new MessageEntry("Chatroom deleted, press enter to return to the menu...",chatRoom.name,this.username, chatRoom.msgNumber);
				js.write(messageObject, null, Lease.FOREVER);
				System.out.println("ChatRoom deleted");
				CleanThread clean = new CleanThread(this.js, chatRoom.name);
				Thread th = new Thread(clean);
				th.start();
			}
			else
				finished=true;
			}while(!finished);

		} catch (UnusableEntryException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
