package game.board;

import game.ship.ShipCell;
import game.player.ShipType;
import game.utils.Utils2D;
import game.player.Player;
import game.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public class UnknownBoard extends Board {
    public UnknownBoard(Player owner, int cols, int rows) {
        super(owner, cols, rows, new ArrayList<>());
    }

    public void markHit(Pos2D at, boolean hasHitSomething) {
        final var cell = getBoard()[at.x()][at.y()];

        getBoard()[at.x()][at.y()] = cell.hit().setShipType(hasHitSomething ? ShipType.UNKNOWN : ShipType.EMPTY);
    }

    public Ship addSunkShip(Pos2D at, ShipType type) {
        final var shipCells = new ArrayList<ShipCell>();
        final boolean vertical = isVertical(at);
        final var boardCells = getBoard();

        var point = getFirstShipCell(at, vertical);

        for (int i = 0; i < type.getSize(); i++) {
            final var cell = boardCells[point.x()][point.y()];

            getBoard()[point.x()][point.y()] = cell.setShipType(type);

            shipCells.add(new ShipCell(point, true));

            point = vertical ?
                    new Pos2D(point.x(), point.y() - 1) :
                    new Pos2D(point.x() - 1, point.y());
        }

        return new Ship(type, vertical, shipCells.reversed());
    }

    private Pos2D getFirstShipCell(Pos2D at, boolean vertical) {
        ShipType limit = null;
        Pos2D point = at;

        while (limit != ShipType.UNKNOWN) {
            point = vertical ?
                    new Pos2D(point.x(), at.y() - 1) :
                    new Pos2D(point.x() - 1, at.y());

            final var cell = getBoardCell(point);

            limit = cell.isPresent() ? cell.get().shipType() : ShipType.EMPTY;
        }

        return vertical ? new Pos2D(point.x(), point.y() + 1) : new Pos2D(point.x() + 1, point.y());
    }

    public boolean isVertical(Pos2D at) {
        final var boardCells = getBoard();
        final var pointsOnTheSides = List.of(
                new Pos2D(at.x() + 1, at.y()),
                new Pos2D(at.x() - 1, at.y()),
                new Pos2D(at.x(), at.y() + 1),
                new Pos2D(at.x(), at.y() - 1)
        );

        final var opt = pointsOnTheSides
                .stream()
                .filter(p -> !Utils2D.isOutbound(p, this))
                .filter(p -> boardCells[p.x()][p.y()].shipType() != ShipType.EMPTY)
                .findFirst();

        if (opt.isPresent()) {
            final var vector = opt.get();

            return vector.x() == at.x();
        }

        return false;
    }
}