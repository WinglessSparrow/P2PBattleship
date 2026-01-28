package game.board.boardRules;

import game.ship.Ship;
import game.player.ShipType;
import game.board.Board;

import java.util.Arrays;

public class MaxShipsAmountRule implements BoardRule {

    private static final int maxShipsAmount = Arrays.stream(ShipType.values())
                                                    .reduce(0, (acc, shipType) -> acc + shipType.getMaxAmount(), Integer::sum);

    @Override
    public void verify(Board board, Ship newShip) throws BrokenRuleException {
        final var ships = board.getAllShips();

        if (ships.size() + 1 >= maxShipsAmount) {
            throw new BrokenRuleException(this.getClass(), "Maximum number of ships reached");
        }

        final boolean tooManyShipsOfType =
                ships.stream().filter(s -> s.type() == newShip.type())
                     .count() + 1 > newShip.type().getMaxAmount();

        if (tooManyShipsOfType) {
            throw new BrokenRuleException(this.getClass(), "Too many ships of the type " + newShip.type());
        }
    }
}
