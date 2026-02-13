package game.player;

public record Player(String name, PlayerType type, boolean ready) {
    public Player setReady(boolean ready) {
        return new Player(name, type, ready);
    }
}
