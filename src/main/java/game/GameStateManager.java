package game;

public class GameStateManager {
    private GameState state = GameState.SETUP;
    private boolean won = false;

    public void setNextState(GameState nextState) {
        state = switch (state) {
            case SETUP -> nextState == GameState.READY ? nextState : state;
            case READY -> nextState == GameState.PLAYING ? nextState : state;
            case PLAYING -> nextState == GameState.FINISHED ? nextState : state;
            case FINISHED -> nextState;
        };

        if (state != nextState) {
            throw new IllegalStateException("Invalid state transition from " + state + " to " + nextState);
        }
    }

    public void finish(boolean won) {
        this.won = won;

        setNextState(GameState.FINISHED);
    }

    public boolean isWon() {
        return won;
    }

    public GameState getState() {
        return state;
    }
}
