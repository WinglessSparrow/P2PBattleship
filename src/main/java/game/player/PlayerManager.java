package game.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private static final int SELF = 0, OPPONENT = 1;

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

    public void setPlayerReady(boolean self) {
        final var playerIdx = self ? SELF : OPPONENT;

        players.set(playerIdx, players.get(playerIdx).setReady(true));
    }
}
