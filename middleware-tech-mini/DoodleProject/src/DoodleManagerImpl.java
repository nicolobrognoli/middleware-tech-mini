
import DoodleProject.*;

import java.util.*;

public class DoodleManagerImpl extends DoodleManagerPOA {
	private List<DoodleProject.DoodleClient> listOfClients;
	private List<Doodle> doodles;

	public DoodleManagerImpl() {
		doodles= new ArrayList<Doodle>();
		this.listOfClients=new ArrayList<DoodleProject.DoodleClient>();
	}
	public Doodle createNewDoodle(String name,String owner){
		try {
			int number = doodles.size(),i;
			for(i=0;i<doodles.size();i++){
				if(doodles.get(i).details().name.equals(name)){
					return null;
				}
			}
			DoodleImpl accImpl = new DoodleImpl(name, owner);
			System.out.println(_poa().getClass());
			org.omg.CORBA.Object ref = _poa().servant_to_reference(accImpl);
			Doodle doodle = DoodleHelper.narrow(ref);      
			doodles.add(doodle);
			return doodle;
		} catch(Exception e) { e.printStackTrace(); return null; }
	}
	
	public String[] getDoodleList(){
		int i=0;
		Doodle temp;
		String toReturn="";
		String[] stringArray= new String[doodles.size()];
		Iterator iter=doodles.iterator();
		while(iter.hasNext()){
			temp=(Doodle) iter.next();
			stringArray[i]=temp.details().name;
			i++;
		}
		return stringArray;
	}
	
	public int[] closeDoodle(Doodle doodle){
		DoodleClient temp=null;
		boolean found=false;
		int[] winnerArray = null;
		String[] votingClients;
		int i,j;
		winnerArray = doodle.calculateWinner();
		//prendo lista di tutti i client che hanno votato per tale doodle
		votingClients = doodle.getVotingClients();
		//ciclo sulla lista e prendo il client di ogni vote per chiamarci la printMSG
		for(i=0; i<votingClients.length;i++)
		{
			found=false;
			for(j=0;j<listOfClients.size() && !found;j++){
				temp=listOfClients.get(j);
				if(temp.getUsername().equals(votingClients[i])){
					found=true;
				}
			}
			if((!votingClients[i].equals(doodle.details().owner))&&found){
				temp.printMsg("Winner is:");
				for(j=0; j< winnerArray.length; j++){
					temp.printMsg("\t- " + doodle.getChoices()[winnerArray[j]]);
				}
			}
		}
		doodles.remove(doodle);
		return winnerArray;		
		
	}
	@Override
	public Doodle getDoodle(String name) {
		Doodle temp;
		int i;
		for(i=0;i<doodles.size();i++){
			temp=doodles.get(i);
			if(temp.details().name.equals(name)){
				return temp;
			}
		}
		return null;
	}


	public void deleteUser(DoodleProject.DoodleClient client) {		
		this.listOfClients.remove(client);
	}

	public boolean registerUser(DoodleProject.DoodleClient client) {
		int i;
		for(i=0;i<this.listOfClients.size();i++){
			if(this.listOfClients.get(i).getUsername().equals(client.getUsername())){
				return true;
			}
		}
		this.listOfClients.add(client);
		return false;
	}
}
