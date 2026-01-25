package game.board.boardRules;

import game.ship.Ship;
import game.player.ShipType;
import game.board.Board;

import java.util.Map;

public class MaxShipsAmountRule implements BoardRule {

    private static final Map<ShipType, Number> SHIPS_AMOUNTS_MAP = Map.of(
            ShipType.CARRIER, 1,
            ShipType.BATTLESHIP, 2,
            ShipType.FRIGATE, 2,
            ShipType.DESTROYER, 3,
            ShipType.SUBMARINE, 1
    );

    private static final int maxShip = SHIPS_AMOUNTS_MAP.values().stream().mapToInt(Number::intValue).sum();

    @Override
    public void verify(Board board, Ship newShip) throws BrokenRuleException {
        final var ships = board.getAllShips();

        if (ships.size() + 1 >= maxShip) {
            throw new BrokenRuleException(this.getClass(), "Maximum number of ships reached");
        }

        final boolean tooManyShipsOfType =
                ships.stream().filter(s -> s.type() == newShip.type())
                     .count() + 1 > SHIPS_AMOUNTS_MAP.get(newShip.type()).intValue();

        if (tooManyShipsOfType) {
            throw new BrokenRuleException(this.getClass(), "Too many ships of the type " + newShip.type());
        }
    }
}
