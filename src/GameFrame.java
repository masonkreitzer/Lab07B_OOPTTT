import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private final TTTTileButton[][] tiles;
    private final int ROWS = Board.ROWS;
    private final int COLS = Board.COLS;

    private final Game game;

    public GameFrame() {
        game = new Game();

        setTitle("Tic Tac Toe - OOP Version");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(ROWS, COLS));
        tiles = new TTTTileButton[ROWS][COLS];

        TileListener listener = new TileListener();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                tiles[r][c] = new TTTTileButton(r, c);
                tiles[r][c].setFont(new Font("Arial", Font.BOLD, 60));
                tiles[r][c].addActionListener(listener);
                boardPanel.add(tiles[r][c]);
            }
        }

        JPanel south = getJPanel();

        add(boardPanel, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel getJPanel() {
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            int opt = JOptionPane.showConfirmDialog(this, "Reset current game?", "Reset", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                game.reset();
                resetBoardUI();
            }
        });

        JPanel south = new JPanel(new BorderLayout());
        JPanel southButtons = new JPanel();
        southButtons.add(resetButton);
        southButtons.add(quitButton);
        south.add(southButtons, BorderLayout.CENTER);
        return south;
    }

    private class TileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TTTTileButton tile = (TTTTileButton) e.getSource();
            int row = tile.getRow();
            int col = tile.getCol();

            Game.MoveResult result = game.makeMove(row, col);
            switch (result) {
                case INVALID:
                    JOptionPane.showMessageDialog(GameFrame.this, "Illegal Move! Try again.");
                    break;
                case OK:
                    updateTileUI(row, col);
                    break;
                case WIN:
                    updateTileUI(row, col);
                    String winner = game.getBoard().isWin("X") ? "X" : "O"; // or game.getCurrentPlayer().other()
                    JOptionPane.showMessageDialog(GameFrame.this, "Player " + winner + " wins!");
                    promptPlayAgain();
                    break;
                case TIE:
                    updateTileUI(row, col);
                    JOptionPane.showMessageDialog(GameFrame.this, "It's a tie!");
                    promptPlayAgain();
                    break;
            }
        }
    }

    private void updateTileUI(int r, int c) {
        tiles[r][c].setText(game.getBoard().getCell(r, c));
    }

    private void resetBoardUI() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                tiles[r][c].setText("");
            }
        }
    }

    private void promptPlayAgain() {
        int option = JOptionPane.showConfirmDialog(this, "Play again?", "Tic Tac Toe", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            game.reset();
            resetBoardUI();
        } else {
            System.exit(0);
        }
    }
}