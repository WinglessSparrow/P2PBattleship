package game.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    public static final int SELF = 0;
    private final List<Player> players;

    private int currentPlayerIndex;

    public PlayerManager(Player self, Player opponent) {
        players = new ArrayList<>(List.of(self, opponent));
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void setPlayerReady(Player player, boolean ready) {
        final var playerIdx = players.indexOf(player);

        if (playerIdx == -1) {
            throw new IllegalArgumentException("Supplied player not registered in player manager " + player);
        }

        players.set(playerIdx, players.get(playerIdx).setReady(ready));
    }

    public boolean isSelf(Player player) {
        return players.indexOf(player) == SELF;
    }
}
