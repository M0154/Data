import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class MyClient extends JFrame {
    private RPSContract rps;
    private String playerName;
    private JButton rockButton, paperButton, scissorsButton;
    private JLabel statusLabel, player1ScoreLabel, player2ScoreLabel, timerLabel;
    private JTextArea historyArea;
    private Timer gameTimer;
    private int remainingTime = 15;

    public MyClient(String playerName) throws Exception {
        this.playerName = playerName;
        rps = (RPSContract) Naming.lookup("rpsGame");

        setTitle("Rock-Paper-Scissors - " + playerName);
        setLayout(new BorderLayout());

        // Buttons
        rockButton = new JButton("🪨 Rock (R)");
        paperButton = new JButton("📄 Paper (P)");
        scissorsButton = new JButton("✂️ Scissors (S)");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);

        rockButton.addActionListener(e -> makeMove("R"));
        paperButton.addActionListener(e -> makeMove("P"));
        scissorsButton.addActionListener(e -> makeMove("S"));

        // Status + Scores + Timer
        statusLabel = new JLabel("Waiting for another player...");
        player1ScoreLabel = new JLabel("Player1: 0");
        player2ScoreLabel = new JLabel("Player2: 0");
        timerLabel = new JLabel("⏳ Time: 15s");

        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        topPanel.add(player1ScoreLabel);
        topPanel.add(player2ScoreLabel);
        topPanel.add(timerLabel);
        topPanel.add(statusLabel);

        // Game History
        historyArea = new JTextArea(10, 30);
        historyArea.setEditable(false);
        JScrollPane historyScroll = new JScrollPane(historyArea);
        historyScroll.setBorder(BorderFactory.createTitledBorder("📜 Game History"));

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(historyScroll, BorderLayout.CENTER);

        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Register player
        rps.registerPlayer(playerName);

        // Start update loop
        startUpdater();
        startTimer();
    }

    private void makeMove(String move) {
        try {
            String result = rps.makeMove(playerName, move);
            statusLabel.setText("📢 " + result);
            remainingTime = 15; // Reset timer
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void startUpdater() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    updateGameState();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateGameState() throws RemoteException {
        int player1Score = rps.getPlayer1Score();
        int player2Score = rps.getPlayer2Score();
        List<String> history = rps.getGameHistory();

        player1ScoreLabel.setText("Player1: " + player1Score);
        player2ScoreLabel.setText("Player2: " + player2Score);

        StringBuilder historyText = new StringBuilder();
        for (String h : history) {
            historyText.append(h).append("\n");
        }
        historyArea.setText(historyText.toString());

        if (rps.isGameOver()) {
            String winner = (player1Score > player2Score) ? "Player1" : "Player2";
            statusLabel.setText("🎉 Game Over! Winner: " + winner);
            rockButton.setEnabled(false);
            paperButton.setEnabled(false);
            scissorsButton.setEnabled(false);
            gameTimer.stop();
        }
    }

    private void startTimer() {
        gameTimer = new Timer(1000, e -> {
            remainingTime--;
            timerLabel.setText("⏳ Time: " + remainingTime + "s");
            if (remainingTime <= 0) {
                statusLabel.setText("⏳ Time's up! Waiting for opponent...");
                remainingTime = 15;
            }
        });
        gameTimer.start();
    }

    public static void main(String[] args) {
        try {
            String name = JOptionPane.showInputDialog("Enter your player name:");
            new MyClient(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
