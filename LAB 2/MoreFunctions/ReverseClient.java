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

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter String=");
            String str = br.readLine();

            System.out.println("Choose Operation: 1-Reverse, 2-Upper, 3-Lower, 4-Capitalize");
            int choice = Integer.parseInt(br.readLine());

            String result = "";

            switch (choice) {
                case 1:
                    result = ReverseImpl.reverse_string(str);
                    break;
                case 2:
                    result = ReverseImpl.to_upper(str);
                    break;
                case 3:
                    result = ReverseImpl.to_lower(str);
                    break;
                case 4:
                    result = ReverseImpl.capitalize(str);
                    break;
                default:
                    result = "Invalid choice.";
            }

            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
