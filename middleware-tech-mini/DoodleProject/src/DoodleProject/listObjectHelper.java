package DoodleProject;


/**
* DoodleProject/listObjectHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Doodle.idl
* giovedì 17 maggio 2012 11.45.06 CEST
*/

abstract public class listObjectHelper
{
  private static String  _id = "IDL:DoodleProject/listObject:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.CORBA.Object[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.CORBA.Object[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ObjectHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (DoodleProject.listObjectHelper.id (), "listObject", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.CORBA.Object[] read (org.omg.CORBA.portable.InputStream istream)
  {
    org.omg.CORBA.Object value[] = null;
    int _len0 = istream.read_long ();
    value = new org.omg.CORBA.Object[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = org.omg.CORBA.ObjectHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.CORBA.Object[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      org.omg.CORBA.ObjectHelper.write (ostream, value[_i0]);
  }

}
