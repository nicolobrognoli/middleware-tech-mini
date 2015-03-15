package it.polimi.mini;

import net.jini.entry.AbstractEntry;

public class ChatRoomEntry extends AbstractEntry{
	private static final long serialVersionUID = 1L;
	public String name;
	public String creatorName;
	public Integer msgNumber;
	
	
	public ChatRoomEntry(){
		this.name=null;
		this.creatorName=null;
		this.msgNumber = 0;
	}
	
	public ChatRoomEntry(String name, String creatorName, Integer num) {
		super();
		this.name = name;
		this.creatorName = creatorName;
		this.msgNumber = num;
	}
}
