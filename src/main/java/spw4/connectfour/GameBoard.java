package spw4.connectfour;

import java.util.Arrays;

public class GameBoard {
    private int row;
    private int col;
    private Player[][] board;

    public GameBoard() {
        this(6, 7);
    }

    public GameBoard(int row, int col) {
        if (row < 0 || col < 0)
            throw new IllegalArgumentException("Invalid row or column, non-negative values expected");
        this.row = row;
        this.col = col;
        board = new Player[row][col];

        for (Player[] currRow : board)
            Arrays.fill(currRow, Player.none);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    private boolean checkValidPosition(int r, int c) {
        return (0 <= r && r < row) && (0 <= c && c < col);
    }

    public boolean drop(int col, Player player) {
        if (!checkValidPosition(0, col))
            throw new IllegalArgumentException("Invalid column");

        int currRow = 0;
        while (currRow < row && board[currRow][col] != Player.none) {
            currRow++;
        }

        if (currRow >= row)
            return false;

        setPlayerAt(currRow, col, player);
        return true;
    }

    public Player getPlayerAt(int row, int col) {
        if (!checkValidPosition(row, col))
            throw new IllegalArgumentException("Invalid row or column");
        return board[this.row - 1 - row][col];
    }

    private void setPlayerAt(int row, int col, Player player) {
        if (!checkValidPosition(row, col))
            throw new IllegalArgumentException("Invalid row or column");
        board[this.row - 1 - row][col] = player;
    }

    public boolean checkForConnectFour() {
        // horizontal
        for (int r = 0; r < row; r++) {
            for (int c = 0; c <= col - 4; c++) {
                if (board[r][c] != Player.none &&
                        board[r][c] == board[r][c + 1] &&
                        board[r][c] == board[r][c + 2] &&
                        board[r][c] == board[r][c + 3]) {
                    return true;
                }
            }
        }

        // vertical
        for (int r = 0; r <= row - 4; r++) {
            for (int c = 0; c < col; c++) {
                if (board[r][c] != Player.none &&
                        board[r][c] == board[r + 1][c] &&
                        board[r][c] == board[r + 2][c] &&
                        board[r][c] == board[r + 3][c]) {
                    return true;
                }
            }
        }

        // Check diagonal (bottom-left to top-right)
        for (int r = 0; r <= row - 4; r++) {
            for (int c = 0; c <= col - 4; c++) {
                if (board[r][c] != Player.none &&
                        board[r][c] == board[r + 1][c + 1] &&
                        board[r][c] == board[r + 2][c + 2] &&
                        board[r][c] == board[r + 3][c + 3]) {
                    return true;
                }
            }
        }

        // Check anti-diagonal (top-left to bottom-right)
        for (int r = 0; r <= row - 4; r++) {
            for (int c = 3; c < col; c++) {
                if (board[r][c] != Player.none &&
                        board[r][c] == board[r + 1][c - 1] &&
                        board[r][c] == board[r + 2][c - 2] &&
                        board[r][c] == board[r + 3][c - 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int r = row - 1; r >= 0; r--) {
            sb.append("|");
            for (int col = 0; col < board[r].length; col++) {
                if (board[r][col] == Player.none) {
                    sb.append(" . ");
                } else if (board[r][col] == Player.red) {
                    sb.append(" R ");
                } else {
                    sb.append(" Y ");
                }
            }
            sb.append("|\n");
        }
        return sb.toString();
    }
}
