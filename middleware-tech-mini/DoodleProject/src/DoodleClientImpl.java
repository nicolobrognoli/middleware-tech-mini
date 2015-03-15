
import DoodleProject.*;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import java.io.*;
import java.util.List;

public class DoodleClientImpl extends DoodleClientPOA {
	
	InputStreamReader reader;
	BufferedReader systemInput;
	String userName;
	
	private void menu(NamingContextExt ncRef, DoodleClientImpl client, ORB orb, POA rootpoa){
		try {	
			// activate the servant associating it to the rootpoa and getting the object reference
			 org.omg.CORBA.Object ref=rootpoa.servant_to_reference(this);

			reader = new InputStreamReader (System.in);
			systemInput = new BufferedReader (reader);	
			// resolve the object Reference in naming
			String name = "MyDoodleManager";
			DoodleManager doodleManager = DoodleManagerHelper.narrow(ncRef.resolve_str(name));
			String str;
			int choice;
			System.out.println("Insert username: ");		
			userName = systemInput.readLine();
		      // bind the object reference in naming
		      NameComponent path[] = ncRef.to_name("MyDoodleManager"+this.userName);
		      ncRef.rebind(path, ref);
			while(doodleManager.registerUser(_this())){
				System.out.println("Username already exist, insert another username: ");		
				userName = systemInput.readLine();
			}
			do{
				System.out.println("Menu: ");
				System.out.println("1 - Create new doodle");
				System.out.println("2 - Vote doodle");
				System.out.println("3 - Close doodle");
				System.out.println("4 - Exit");
				str = systemInput.readLine();
				choice = client.getChoice(str);
				switch(choice){
				case 1:
				{
					client.createNewDoodle(doodleManager);
					break;
				}
				case 2:
				{
					client.voteDoodle(doodleManager);
					break;
				}
				case 3:
				{
					client.closeDoodle(doodleManager);
					break;
				}
				case 4:
				{
					client.exit(doodleManager);
					break;
				}
				default:
				{
					System.out.println("Typing error. Retry..");
					break;
				}	
				}
			}while(choice != 4);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFound e) {
			e.printStackTrace();
		} catch (CannotProceed e) {
			e.printStackTrace();
		} catch (InvalidName e) {
			e.printStackTrace();
		} catch (ServantNotActive e) {
			e.printStackTrace();
		} catch (WrongPolicy e) {
			e.printStackTrace();
		} 
	}

	private void exit(DoodleManager doodleManager) {
		doodleManager.deleteUser(_this());
	}

	public static void main(String args[]) {				
		try{			
			DoodleClientImpl client = new DoodleClientImpl();
				
		
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);
	
			// get the root naming context
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			
			// get reference to rootpoa & activate the POAManager
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			
			
			
			
			//application menu
			client.menu(ncRef, client, orb, rootpoa);
	
		}catch(Exception e){}
	}
		
	private void closeDoodle(DoodleManager doodleManager) {
		try {
			String[] doodleList=doodleManager.getDoodleList();
			int i,choice;
			int[] winnerArray;
			String str;
			Doodle temp;
			System.out.println("Doodles: ");
			for(i=0;i<doodleList.length;i++){
				System.out.println(i+" "+doodleList[i].toString());
			}
			if(doodleList.length>0){
				do{
					System.out.println("Select a doodle: ");
					str = systemInput.readLine();					
					choice = Integer.parseInt(str);
				}while(choice<0 || choice>=doodleList.length);
				temp = doodleManager.getDoodle(doodleList[choice]);
				if(!temp.details().owner.equals(this.userName)){
					System.out.println("You are not the owner or the doodle doesn't exist.");
				}else{
					winnerArray=doodleManager.closeDoodle(temp);
					String[] choices = temp.getChoices();
					if(winnerArray.length>1)
						System.out.println("The winner are: ");
					else
						System.out.println("The winner is: ");
					for(i=0; i< winnerArray.length; i++){
						System.out.println("\t- " + choices[winnerArray[i]]);
					}
										
				}
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void voteDoodle(DoodleManager doodleManager) {
		try {
			String[] doodleList=doodleManager.getDoodleList();
			String[] choicesOfDoodle;
			int i,choice;
			String str;
			Doodle temp;
			
			System.out.println("Doodles: ");
			for(i=0;i<doodleList.length;i++){
				System.out.println(i+" "+doodleList[i]);
			}
			if(doodleList.length>0){
				do{
					System.out.println("Select a doodle: ");
					str = systemInput.readLine();					
					choice = Integer.parseInt(str);
				}while(choice<0 || choice>=doodleList.length);
				temp = doodleManager.getDoodle(doodleList[choice]);
				System.out.println("Choices of Doodle " + temp.details().name);
				//
				choicesOfDoodle = temp.getChoices();				
				for(i=0;i<choicesOfDoodle.length;i++){
					System.out.println(i+" "+choicesOfDoodle[i]);
				}
				if(choicesOfDoodle.length>0){
					do{
						System.out.println("Select a choice: ");
						str = systemInput.readLine();
						choice = Integer.parseInt(str);
					}while(choice<0 || choice>=choicesOfDoodle.length);
					temp.vote(_this(), choicesOfDoodle[choice]);
					System.out.println("Vote done.");
				}else{
					System.out.println("No choice.");
				}
			}else{
				System.out.println("No doodle.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UserAlreadyVoted e) {
			System.out.println("You have already voted for this choice.");
		}

	}

	private void createNewDoodle(DoodleManager doodleManager) {		
		try {
			String input;
			Doodle temp;
			System.out.println("Doodle name: ");
			input = systemInput.readLine();		
			temp = doodleManager.createNewDoodle(input, this.userName);
			if(temp!=null){
				System.out.println("Insert a new choice:(type \"exit\" to exit)");
				input = systemInput.readLine();
				while(!input.equals("exit")){
					temp.addChoice(input);
					System.out.println("Insert a new choice:(type \"exit\" to exit)");
					input = systemInput.readLine();
				}
			}else{
				System.out.println("Doodle already exists.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private int getChoice(String str) {
		if(str.equals("1"))
			return 1;
		if(str.equals("2"))
			return 2;
		if(str.equals("3"))
			return 3;
		if(str.equals("4"))
			return 4;
		return -1;
	}
	public void printMsg(String message){
		System.out.println(message);
		
	}
	
	public String getUsername(){
		return this.userName;
	}
}
