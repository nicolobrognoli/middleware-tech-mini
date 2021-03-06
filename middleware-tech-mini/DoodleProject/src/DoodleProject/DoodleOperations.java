package DoodleProject;


/**
* DoodleProject/DoodleOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Doodle.idl
* lunedì 4 giugno 2012 15.44.47 CEST
*/

public interface DoodleOperations 
{
  String[] getChoices ();
  void addChoice (String choice);
  int vote (DoodleProject.DoodleClient client, String choice) throws DoodleProject.UserAlreadyVoted;
  DoodleProject.DoodleDetails details ();
  String _toString ();
  int[] calculateWinner ();
  String[] getVotingClients ();
} // interface DoodleOperations
