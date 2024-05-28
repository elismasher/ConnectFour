package spw4.connectfour;

public class ConnectFourImpl implements ConnectFour {
    private Player currentPlayer;
    private GameBoard gameBoard;

    public ConnectFourImpl(Player playerOnTurn) {
        this.currentPlayer = playerOnTurn;
        this.gameBoard = new GameBoard();
    }

    public Player getPlayerAt(int row, int col) {
        return gameBoard.getPlayerAt(row, col);
    }

    public Player getPlayerOnTurn() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameBoard.checkForConnectFour();
    }

    public Player getWinner() {
        if (isGameOver())
            return currentPlayer == Player.red ? Player.yellow : Player.red;
        return Player.none;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nPlayer: ").append(currentPlayer == Player.red ? "RED" : "YELLOW").append("\n");
        sb.append(gameBoard);
        return sb.toString();
    }

    public void reset(Player playerOnTurn) {
        currentPlayer = playerOnTurn;
        gameBoard = new GameBoard();
    }

    public void drop(int col) {
        if (gameBoard.drop(col, currentPlayer))
            switchCurrentPlayer();
    }

    private void switchCurrentPlayer() {
        currentPlayer = currentPlayer == Player.red ? Player.yellow : Player.red;
    }
}
