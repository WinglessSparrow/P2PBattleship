package game.player;

public class PlayerManager {

    private final Player[] players;
    private int currentPlayerIndex;

    public PlayerManager(Player player1, Player player2) {
        players = new Player[]{player1, player2};
    }

    public Player nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;

        return currentPlayer();
    }

    public Player currentPlayer() {
        return players[currentPlayerIndex];
    }
}
