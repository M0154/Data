import java.math.BigInteger;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Factorial extends Remote {
    BigInteger fact(int num) throws RemoteException;
}
 
    

