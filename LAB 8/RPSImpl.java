// RPSImpl.java
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RPSImpl extends UnicastRemoteObject implements RPSContract {
    private String player1 = null;
    private String player2 = null;
    private String player1Move = "";
    private String player2Move = "";
    private int player1Score = 0;
    private int player2Score = 0;
    private final List<String> gameHistory = new ArrayList<>();
    private boolean gameOver = false;

    public RPSImpl() throws RemoteException {}

    @Override
    public synchronized boolean registerPlayer(String name) throws RemoteException {
        if (player1 == null) {
            player1 = name;
            return true;
        } else if (player2 == null) {
            player2 = name;
            return true;
        }
        return false;
    }

    @Override
    public synchronized String makeMove(String playerName, String move) throws RemoteException {
        if (gameOver) return "Game Over.";

        if (playerName.equals(player1)) {
            player1Move = move;
        } else if (playerName.equals(player2)) {
            player2Move = move;
        }

        if (!player1Move.isEmpty() && !player2Move.isEmpty()) {
            String roundResult = determineRoundWinner();
            gameHistory.add(player1 + " played " + moveFullName(player1Move) +
                            " | " + player2 + " played " + moveFullName(player2Move) +
                            " => " + roundResult);

            player1Move = "";
            player2Move = "";

            if (player1Score == 5 || player2Score == 5) {
                gameOver = true;
            }

            return roundResult;
        }

        return "Move registered. Waiting for other player...";
    }

    private String determineRoundWinner() {
        if (player1Move.equals(player2Move)) {
            return "It's a draw!";
        }

        boolean p1Wins = (player1Move.equals("R") && player2Move.equals("S")) ||
                         (player1Move.equals("P") && player2Move.equals("R")) ||
                         (player1Move.equals("S") && player2Move.equals("P"));

        if (p1Wins) {
            player1Score++;
            return player1 + " wins the round!";
        } else {
            player2Score++;
            return player2 + " wins the round!";
        }
    }

    private String moveFullName(String move) {
        switch (move) {
            case "R": return "Rock";
            case "P": return "Paper";
            case "S": return "Scissors";
            default: return "Unknown";
        }
    }

    @Override
    public int getPlayer1Score() throws RemoteException {
        return player1Score;
    }

    @Override
    public int getPlayer2Score() throws RemoteException {
        return player2Score;
    }

    @Override
    public List<String> getGameHistory() throws RemoteException {
        return gameHistory;
    }

    @Override
    public boolean isGameOver() throws RemoteException {
        return gameOver;
    }

    @Override
    public synchronized void resetGame() throws RemoteException {
        player1 = null;
        player2 = null;
        player1Move = "";
        player2Move = "";
        player1Score = 0;
        player2Score = 0;
        gameHistory.clear();
        gameOver = false;
    }

    @Override
    public String getCurrentTurn() throws RemoteException {
        return (player1Move.isEmpty() && player2Move.isEmpty()) ? player1 : player2;
    }
}
