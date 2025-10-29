public class Board {
    public static final int ROWS = 3;
    public static final int COLS = 3;
    private final String[][] grid;

    public Board() {
        grid = new String[ROWS][COLS];
        clear();
    }

    public void clear() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                grid[r][c] = " ";
            }
        }
    }

    public boolean isValidMove(int r, int c) {
        if (r < 0 || r >= ROWS || c < 0 || c >= COLS) return false;
        return " ".equals(grid[r][c]);
    }

    public void setCell(int r, int c, String player) {
        grid[r][c] = player;
    }

    public String getCell(int r, int c) {
        return grid[r][c];
    }

    public boolean isWin(String player) {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private boolean isRowWin(String player) {
        for (int r = 0; r < ROWS; r++) {
            if (player.equals(grid[r][0]) &&
                    player.equals(grid[r][1]) &&
                    player.equals(grid[r][2])) {
                return true;
            }
        }
        return false;
    }

    private boolean isColWin(String player) {
        for (int c = 0; c < COLS; c++) {
            if (player.equals(grid[0][c]) &&
                    player.equals(grid[1][c]) &&
                    player.equals(grid[2][c])) {
                return true;
            }
        }
        return false;
    }

    private boolean isDiagonalWin(String player) {
        return (player.equals(grid[0][0]) &&
                player.equals(grid[1][1]) &&
                player.equals(grid[2][2])) ||
                (player.equals(grid[0][2]) &&
                        player.equals(grid[1][1]) &&
                        player.equals(grid[2][0]));
    }

    // Returns true if no possible win lines for either player.
    public boolean isTie() {
        if (isWin("X") || isWin("O")) return false;

        // If board is full -> tie
        boolean full = true;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == null || " ".equals(grid[r][c]) ) {
                    full = false;
                    break;
                }
            }
            if (!full) break;
        }
        if (full) return true;

        boolean xPossible = false;
        boolean oPossible = false;

        String[][] lines = {
                {grid[0][0], grid[0][1], grid[0][2]},
                {grid[1][0], grid[1][1], grid[1][2]},
                {grid[2][0], grid[2][1], grid[2][2]},
                {grid[0][0], grid[1][0], grid[2][0]},
                {grid[0][1], grid[1][1], grid[2][1]},
                {grid[0][2], grid[1][2], grid[2][2]},
                {grid[0][0], grid[1][1], grid[2][2]},
                {grid[0][2], grid[1][1], grid[2][0]}
        };

        for (String[] line : lines) {
            boolean hasX = false, hasO = false;
            for (String cell : line) {
                if ("X".equals(cell)) hasX = true;
                if ("O".equals(cell)) hasO = true;
            }
            if (!hasO) xPossible = true;
            if (!hasX) oPossible = true;
        }

        return !xPossible && !oPossible;
    }
}
