package client.ui.windowed.utils;

import game.BattleshipGame;

import java.util.Optional;

public class BattleshipStore {
    private static BattleshipGame battleshipGame = null;

    public static void set(BattleshipGame battleshipGame) {
        BattleshipStore.battleshipGame = battleshipGame;
    }

    public static Optional<BattleshipGame> get() {
        return Optional.ofNullable(battleshipGame);
    }
}
