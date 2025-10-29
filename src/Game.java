public class Game {
    public enum MoveResult { INVALID, OK, WIN, TIE }

    private final Board board;
    private Player currentPlayer;
    private int moveCount;

    public Game() {
        board = new Board();
        currentPlayer = Player.X;
        moveCount = 0;
    }

    public MoveResult makeMove(int row, int col) {
        if (!board.isValidMove(row, col)) {
            return MoveResult.INVALID;
        }

        board.setCell(row, col, currentPlayer.toString());
        moveCount++;

        if (moveCount >= 5 && board.isWin(currentPlayer.toString())) {
            return MoveResult.WIN;
        }

        if (moveCount >= 7 && board.isTie()) {
            return MoveResult.TIE;
        }

        // swap current player
        currentPlayer = currentPlayer.other();
        return MoveResult.OK;
    }

    public void reset() {
        board.clear();
        moveCount = 0;
        currentPlayer = Player.X;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public int getMoveCount() {
        return moveCount;
    }
}
