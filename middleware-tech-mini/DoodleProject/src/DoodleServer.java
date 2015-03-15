import DoodleProject.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.io.*;
import java.net.ConnectException;

public class DoodleServer {
  public static void main(String args[]) {
    try{
      // create the servant
      DoodleManagerImpl doodleManagerImpl = new DoodleManagerImpl();

      // create and initialize the ORB
      ORB orb = ORB.init(args, null);

      // get reference to rootpoa & activate the POAManager
      POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      rootpoa.the_POAManager().activate();

      // activate the servant associating it to the rootpoa and getting the object reference
      org.omg.CORBA.Object ref = rootpoa.servant_to_reference(doodleManagerImpl);
          
      // get the root naming context
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
      
      // bind the object reference in naming
      NameComponent path[] = ncRef.to_name("MyDoodleManager");
      ncRef.rebind(path, ref);
      // wait for invocations from clients
      System.out.println("MyDoodleManager server ready and waiting ...");
      orb.run();
    } catch (Exception e) {
		e.printStackTrace();
	} 
  }
}
