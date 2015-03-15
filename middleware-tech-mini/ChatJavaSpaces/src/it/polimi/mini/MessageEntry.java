package it.polimi.mini;

import net.jini.entry.AbstractEntry;

public class MessageEntry extends AbstractEntry{
	
	private static final long serialVersionUID = 1L;
	public String text;
	public String chatRoomName;
	public String sender;
	public Integer id;
	
	public MessageEntry() {
		super();
	}

	public MessageEntry(String text, String chatRoomName, String sender, Integer id) {
		super();
		this.text = text;
		this.chatRoomName = chatRoomName;
		this.sender = sender;
		this.id = id;
	}	
	
	public void printMessage(){
		System.out.println(this.sender+": "+this.text);
	}
	
}
