package DoodleProject;

/**
* DoodleProject/DoodleManagerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Doodle.idl
* lunedì 4 giugno 2012 15.44.47 CEST
*/

public final class DoodleManagerHolder implements org.omg.CORBA.portable.Streamable
{
  public DoodleProject.DoodleManager value = null;

  public DoodleManagerHolder ()
  {
  }

  public DoodleManagerHolder (DoodleProject.DoodleManager initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = DoodleProject.DoodleManagerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    DoodleProject.DoodleManagerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return DoodleProject.DoodleManagerHelper.type ();
  }

}