package it.polimi.mini;

import java.rmi.RemoteException;

import net.jini.core.entry.UnusableEntryException;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

public class ChatReader implements Runnable{
	private boolean exit=false;
	private JavaSpace js;
	private String chatRoomName;
	private String username;
	
	public ChatReader(JavaSpace js,String chatRoomName,String username){
		this.js=js;
		this.chatRoomName=chatRoomName;
		this.username=username;
	}
	
	@Override
	public void run() {
		try {
			boolean deleted = false;
			ChatRoomEntry chatTemplate=new ChatRoomEntry(this.chatRoomName,null,null);
			ChatRoomEntry chat=(ChatRoomEntry)js.read(chatTemplate, null, Lease.FOREVER);
			do{	
				chat=(ChatRoomEntry)js.read(chatTemplate, null, Lease.FOREVER);
				MessageEntry messageTemplate=new MessageEntry(null,this.chatRoomName,null,chat.msgNumber);
				MessageEntry message=(MessageEntry)js.read(messageTemplate, null,Lease.FOREVER);
				if(message!=null){
					synchronized(this){
						if(!message.sender.equals(this.username)&&!exit)
							message.printMessage();
					}
					if(message.text.equals("Chatroom deleted, press enter to return to the menu...")){
						deleted=true;
					}
				}	
			}while(!exit && !deleted);
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
	
	public synchronized void exit(){
		exit=true;
	}
}
