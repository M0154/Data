import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RPSContract extends Remote {
    boolean registerPlayer(String playerName) throws RemoteException;
    String getCurrentTurn() throws RemoteException;
    String makeMove(String playerName, String move) throws RemoteException;
    int getPlayer1Score() throws RemoteException;
    int getPlayer2Score() throws RemoteException;
    List<String> getGameHistory() throws RemoteException;
    boolean isGameOver() throws RemoteException;
    void resetGame() throws RemoteException;
}
