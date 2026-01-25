package game.board.boardRules;

import game.ship.Ship;
import game.board.Board;

public interface BoardRule {
    void verify(Board board, Ship newShip) throws BrokenRuleException;
}
