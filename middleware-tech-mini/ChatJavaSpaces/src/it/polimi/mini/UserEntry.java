package it.polimi.mini;

import net.jini.entry.AbstractEntry;

public class UserEntry extends AbstractEntry{

	private static final long serialVersionUID = 1L;
	public String name;
	
	public UserEntry() {
		this.name = null;
	}
	
	public UserEntry(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
