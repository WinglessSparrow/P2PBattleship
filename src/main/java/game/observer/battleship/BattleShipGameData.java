package game.observer.battleship;

import game.BattleshipGame;
import game.GameState;

public record BattleShipGameData(PlayersData playersData, PlayersData opponentsData, GameState gameState) {
    public static BattleShipGameData from(BattleshipGame battleship) {
        var playerData = new PlayersData(
                battleship.getBoard(),
                battleship.getCols(),
                battleship.getRows(),
                battleship.getAllShips(),
                battleship.hasPlayerWon()
        );

        var opponentsData = new PlayersData(
                battleship.getOpponentsBoard(),
                battleship.getCols(),
                battleship.getRows(),
                battleship.getAllOpponentsShips(),
                !battleship.hasPlayerWon()
        );

        return new BattleShipGameData(playerData, opponentsData, battleship.getGameState());
    }
}
