package DoodleProject;

/**
* DoodleProject/DoodleClientHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Doodle.idl
* lunedì 4 giugno 2012 15.44.47 CEST
*/

public final class DoodleClientHolder implements org.omg.CORBA.portable.Streamable
{
  public DoodleProject.DoodleClient value = null;

  public DoodleClientHolder ()
  {
  }

  public DoodleClientHolder (DoodleProject.DoodleClient initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = DoodleProject.DoodleClientHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    DoodleProject.DoodleClientHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return DoodleProject.DoodleClientHelper.type ();
  }

}
