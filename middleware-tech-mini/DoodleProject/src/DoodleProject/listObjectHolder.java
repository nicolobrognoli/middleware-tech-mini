package DoodleProject;


/**
* DoodleProject/listObjectHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Doodle.idl
* giovedì 17 maggio 2012 11.45.06 CEST
*/

public final class listObjectHolder implements org.omg.CORBA.portable.Streamable
{
  public org.omg.CORBA.Object value[] = null;

  public listObjectHolder ()
  {
  }

  public listObjectHolder (org.omg.CORBA.Object[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = DoodleProject.listObjectHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    DoodleProject.listObjectHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return DoodleProject.listObjectHelper.type ();
  }

}
