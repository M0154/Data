import java.math.BigInteger;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class FactorialImpl extends UnicastRemoteObject implements Factorial {

    public FactorialImpl() throws RemoteException {
        super();
    }

    public BigInteger fact(int num) throws RemoteException {
        BigInteger factorial = BigInteger.ONE;
        for (int i = 1; i <= num; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        return factorial;
    }
}
