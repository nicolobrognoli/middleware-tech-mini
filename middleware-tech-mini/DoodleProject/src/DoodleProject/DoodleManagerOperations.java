package DoodleProject;


/**
* DoodleProject/DoodleManagerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Doodle.idl
* lunedì 4 giugno 2012 15.44.47 CEST
*/

public interface DoodleManagerOperations 
{
  boolean registerUser (DoodleProject.DoodleClient client);
  void deleteUser (DoodleProject.DoodleClient client);
  DoodleProject.Doodle getDoodle (String name);
  DoodleProject.Doodle createNewDoodle (String name, String owner);
  String[] getDoodleList ();
  int[] closeDoodle (DoodleProject.Doodle doodle);
} // interface DoodleManagerOperations