package DoodleProject;


/**
* DoodleProject/listDoodleClientHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Doodle.idl
* lunedì 4 giugno 2012 14.49.25 CEST
*/

public final class listDoodleClientHolder implements org.omg.CORBA.portable.Streamable
{
  public DoodleProject.DoodleClient value[] = null;

  public listDoodleClientHolder ()
  {
  }

  public listDoodleClientHolder (DoodleProject.DoodleClient[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = DoodleProject.listDoodleClientHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    DoodleProject.listDoodleClientHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return DoodleProject.listDoodleClientHelper.type ();
  }

}
