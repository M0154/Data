import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.net.MalformedURLException;

public class MyServer {
    public static void main(String[] args) {
        try {
            // Start RMI registry
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Create and bind the remote object
            RPSImpl rps = new RPSImpl();
            Naming.rebind("rpsGame", rps); // This can throw MalformedURLException
            System.out.println("RPS Server is ready.");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
