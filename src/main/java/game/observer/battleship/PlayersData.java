package game.observer.battleship;


import game.board.BoardCell;
import game.ship.Ship;

import java.util.List;

public record PlayersData(
        BoardCell[][] board,
        int cols,
        int rows,
        List<Ship> ships,
        boolean won
) {
}
