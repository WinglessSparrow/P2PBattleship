package game.ship;

import game.player.ShipType;
import game.board.Pos2D;

import java.util.ArrayList;
import java.util.List;

public record Ship(
        ShipType type,
        boolean vertical,
        List<ShipCell> cells,
        boolean isSunk
) {
    public Ship(ShipType type, boolean vertical, List<ShipCell> cells) {
        final boolean isSunk = cells.stream().allMatch(ShipCell::isHit);

        this(type, vertical, cells, isSunk);
    }

    public Ship(ShipType type, boolean vertical, Pos2D at) {
        var cells = new ArrayList<ShipCell>();

        for (int i = 0; i < type.getSize(); i++) {
            var point = vertical ?
                        new Pos2D(at.x(), at.y() + i) :
                        new Pos2D(at.x() + i, at.y());

            cells.add(new ShipCell(point));
        }

        this(type, vertical, cells, false);
    }

    public Ship(ShipType type, boolean vertical) {
        this(type, vertical, null, false);
    }

    public Ship rotate(boolean vertical) {
        return new Ship(type, vertical, cells, isSunk);
    }

    public Ship hit(final Pos2D at) {
        if (cells.stream().noneMatch(c -> c.position().equals(at))) {
            throw new IllegalArgumentException("Point is not part of the ship");
        }

        return updateCell(new ShipCell(at, true));
    }

    public Ship updateCell(ShipCell cell) {
        final var cells = this.cells
                .stream()
                .map(c -> c.position().equals(cell.position()) ? cell.hit() : c)
                .toList();

        return new Ship(this.type, vertical, cells);
    }
}

