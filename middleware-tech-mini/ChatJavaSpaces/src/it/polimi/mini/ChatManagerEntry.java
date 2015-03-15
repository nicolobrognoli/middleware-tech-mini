package it.polimi.mini;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.jini.core.discovery.LookupLocator;
import net.jini.core.lease.Lease;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.entry.AbstractEntry;
import net.jini.space.JavaSpace;

public class ChatManagerEntry  extends AbstractEntry{
	public List<String> chatrooms;
	
	public ChatManagerEntry(){
		chatrooms= new ArrayList<String>();
	}
	
	public ChatManagerEntry(List<String> chatrooms) {
		this.chatrooms=chatrooms;
	}
}
