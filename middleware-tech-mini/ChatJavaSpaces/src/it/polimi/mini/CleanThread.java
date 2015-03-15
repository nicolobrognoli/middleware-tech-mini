package it.polimi.mini;

import java.rmi.RemoteException;

import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

public class CleanThread implements Runnable {
	
	private JavaSpace js;
	private String chatRoomName;

	public CleanThread(JavaSpace js, String chatRoomName){
		this.js=js;
		this.chatRoomName=chatRoomName;
	}

	@Override
	public void run() {
		try {
			MessageEntry message;
			Integer i=0;
			do{				
				MessageEntry messageTemplate=new MessageEntry(null,this.chatRoomName,null,i);
				message=(MessageEntry)js.take(messageTemplate, null,5000);
				i++;
			}while(message!=null);
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

}
