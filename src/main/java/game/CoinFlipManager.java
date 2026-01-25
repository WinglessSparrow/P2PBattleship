package game;

public class CoinFlipManager {
    private final boolean coinFlip;
    private final boolean isHost;

    public CoinFlipManager(boolean isHost) {
        this.isHost = isHost;

        coinFlip = Math.random() < 0.5;
    }


    public boolean isFirstMove(boolean opponentsCoinFlip) {
        return isHost == (coinFlip != opponentsCoinFlip);
    }
}
