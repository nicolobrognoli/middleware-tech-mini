
import java.util.ArrayList;
import java.util.List;

import DoodleProject.*;

public class DoodleImpl extends DoodlePOA {
	private List<String> choiceList;
	private List<Vote> voteList;
	private DoodleDetails details;
	
	public DoodleImpl(String name, String owner) {
		choiceList = new ArrayList<String>();
		voteList = new ArrayList<Vote>();
		details=new DoodleDetails(name,owner);
	}
	
	
	
	public void addChoice(String choice){	
		if(!this.choiceList.contains(choice))
			this.choiceList.add(choice);
	}
	
	public String[] getChoices(){
		int i,size=choiceList.size();
		String[] arrayString= new String[size];
		for(i=0;i<size;i++){
			arrayString[i]=choiceList.get(i);
		}
		return arrayString;
	}
	public int vote(DoodleProject.DoodleClient client, String choice) throws UserAlreadyVoted{
		if(!voteList.contains(new Vote(client.getUsername(),choice))&&choiceList.contains(choice)){
				voteList.add(new Vote(client.getUsername(),choice));
				return 1;
		}else{
			throw new UserAlreadyVoted();
		}
	} 
	public DoodleDetails details() {
		return details;
	}
	
	public int[] calculateWinner(){
		List<Integer> winnerList = new ArrayList<Integer>();		
		int i,j, k, max = 0;
		int [] voteOfChoices = new int[choiceList.size()];;
		for(i=0; i < choiceList.size(); i++)
		{
			voteOfChoices[i] = 0;
			for(j=0; j < voteList.size(); j++){
				if(voteList.get(j).getChoice().equals(choiceList.get(i)))
					voteOfChoices[i]++;
			}	
		}
		for(max=i=0; i < voteOfChoices.length; i++)
		{
			if(voteOfChoices[i]>=max){
				max = voteOfChoices[i] ;
			}
		}
		for(i=0; i < voteOfChoices.length; i++)
		{
			if(voteOfChoices[i]==max){
				winnerList.add(i);
			}
		}
		//generate the winner array
		int[] winnerArray = new int[winnerList.size()];
		for(i=0; i<winnerList.size(); i++)
			winnerArray[i] = winnerList.get(i);
		return winnerArray;	
	}

	@Override
	public String _toString() {
		
		return this.details.name+" "+this.details.owner;
	}

	
	public String[] getVotingClients() {
		String tempClient;
		List<String> temp= new ArrayList<String>();
		for(int i=0; i<this.voteList.size();i++){
			tempClient=this.voteList.get(i).getClient();
			if(!temp.contains(tempClient)){
				temp.add(tempClient);
			}
		}
		String[] clients = new String[temp.size()];
		for(int i=0; i<temp.size();i++){
			clients[i]=temp.get(i);
		}
		return clients;
	}

}
