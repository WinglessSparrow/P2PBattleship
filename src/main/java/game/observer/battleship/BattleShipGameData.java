package game.observer.battleship;

import game.BattleshipGame;
import game.GameState;
import game.player.Player;

public record BattleShipGameData(
        PlayersData playersData,
        PlayersData opponentsData,
        GameState gameState,
        Player victor
) {
    public static BattleShipGameData from(BattleshipGame battleship) {
        final var players = battleship.getPlayers();

        final var playerData = new PlayersData(
                battleship.getBoard(),
                battleship.getCols(),
                battleship.getRows(),
                battleship.getAllShips(),
                players.getFirst()
        );

        final var opponentsData = new PlayersData(
                battleship.getOpponentsBoard(),
                battleship.getCols(),
                battleship.getRows(),
                battleship.getAllOpponentsShips(),
                players.getLast()
        );

        final var victor = battleship.hasPlayerWon() ? players.getFirst() : players.getLast();

        return new BattleShipGameData(playerData, opponentsData, battleship.getGameState(), victor);
    }
}
