// RPSClient.java (Client Interface)
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RPSClient extends Remote {
    void notifyTurn() throws RemoteException;
    void receiveResult(String result) throws RemoteException;
    void updateOpponentStatus(String status) throws RemoteException;
}