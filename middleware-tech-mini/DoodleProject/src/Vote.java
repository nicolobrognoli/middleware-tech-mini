import java.util.List;

import DoodleProject.*;

public class Vote {
	private String client;
	private String choice;
	
	public Vote(){
	}
	
	public Vote(String client, String choice){
		this.client = client;
		this.choice = choice;
	}
	
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client= client;
	}
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	
	@Override
	public boolean equals(Object o){
		Vote temp=(Vote)o;
		if(temp.getChoice().equals(this.choice)&&(temp.getClient().equals(this.client))){
			return true;
		}
		return false;
	}
}
