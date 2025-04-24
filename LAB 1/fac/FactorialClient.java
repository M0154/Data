import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;

public class FactorialClient {

    public static void main(String[] args) {
        try {
            Factorial f = (Factorial) Naming.lookup("rmi://localhost/FactorialService");
            int num = 30; // You can take input from args if needed
            System.out.println("Factorial of " + num + " is: " + f.fact(num));
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException: " + e);
        } catch (RemoteException e) {
            System.out.println("RemoteException: " + e);
        } catch (NotBoundException e) {
            System.out.println("NotBoundException: " + e);
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException: " + e);
        }
    }
}
