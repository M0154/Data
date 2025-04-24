// ReverseClient.java

import ReverseModule.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.io.*;

class ReverseClient {
    public static void main(String args[]) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Reverse ReverseImpl = ReverseHelper.narrow(ncRef.resolve_str("Reverse"));

            System.out.println("Enter String=");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String str = br.readLine();

            String tempStr = ReverseImpl.reverse_string(str);
            System.out.println(tempStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
