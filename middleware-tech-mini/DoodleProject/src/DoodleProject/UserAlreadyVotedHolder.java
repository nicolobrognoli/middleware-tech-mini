package DoodleProject;

/**
* DoodleProject/UserAlreadyVotedHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Doodle.idl
* lunedì 4 giugno 2012 15.44.47 CEST
*/

public final class UserAlreadyVotedHolder implements org.omg.CORBA.portable.Streamable
{
  public DoodleProject.UserAlreadyVoted value = null;

  public UserAlreadyVotedHolder ()
  {
  }

  public UserAlreadyVotedHolder (DoodleProject.UserAlreadyVoted initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = DoodleProject.UserAlreadyVotedHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    DoodleProject.UserAlreadyVotedHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return DoodleProject.UserAlreadyVotedHelper.type ();
  }

}
