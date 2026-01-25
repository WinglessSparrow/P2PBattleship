package game.board.boardRules;

import game.board.Board;
import game.board.Pos2D;
import game.player.ShipType;
import game.ship.Ship;
import game.ship.ShipCell;
import game.utils.Utils2D;

import java.util.ArrayList;
import java.util.List;

public class ShipDistanceRule implements BoardRule {
    @Override
    public void verify(Board board, Ship newShip) throws BrokenRuleException {
        final List<Pos2D> shipPoints = newShip.cells().stream().map(ShipCell::position).toList();

        final List<Pos2D> surroundingPoints =
                new ArrayList<>(getFrontEdge(newShip, shipPoints));

        final var cells = board.getBoardCells();

        getAllIntermidiateEdges(newShip, shipPoints, surroundingPoints);

        surroundingPoints.addAll(getBackEdges(newShip, shipPoints));

        final boolean noOverlap = surroundingPoints
                .stream()
                .filter(p -> !Utils2D.isOutbound(p, board))
                .noneMatch(p -> cells[p.x()][p.y()].shipType() != ShipType.EMPTY);

        if (!noOverlap) {
            throw new BrokenRuleException(this.getClass(), "Ship overlaps with another ship, min distance is 1 cell");
        }
    }

    private void getAllIntermidiateEdges(Ship newShip, List<Pos2D> shipPoints, List<Pos2D> surroundingPoints) {
        for (Pos2D point : shipPoints) {
            surroundingPoints.add(
                    newShip.vertical() ?
                    new Pos2D(point.x() + 1, point.y()) :
                    new Pos2D(point.x(), point.y() + 1)
            );
            surroundingPoints.add(
                    newShip.vertical() ?
                    new Pos2D(point.x() - 1, point.y()) :
                    new Pos2D(point.x(), point.y() - 1)
            );
        }
    }

    private List<Pos2D> getFrontEdge(
            Ship newShip,
            List<Pos2D> shipPoints
    ) {
        final var point = shipPoints.getFirst();
        final var frontalEdges = new ArrayList<Pos2D>();

        frontalEdges.add(
                newShip.vertical() ?
                new Pos2D(point.x(), point.y() - 1) :
                new Pos2D(point.x() - 1, point.y())
        );

        frontalEdges.add(new Pos2D(point.x() - 1, point.y() - 1));

        frontalEdges.add(
                newShip.vertical() ?
                new Pos2D(point.x() + 1, point.y() - 1) :
                new Pos2D(point.x() - 1, point.y() + 1)
        );

        return frontalEdges;
    }


    private List<Pos2D> getBackEdges(
            Ship newShip,
            List<Pos2D> shipPoints
    ) {
        final var point = shipPoints.getLast();
        final var frontalEdges = new ArrayList<Pos2D>();

        frontalEdges.add(
                newShip.vertical() ?
                new Pos2D(point.x(), point.y() + 1) :
                new Pos2D(point.x() + 1, point.y())
        );

        frontalEdges.add(new Pos2D(point.x() + 1, point.y() + 1));

        frontalEdges.add(
                newShip.vertical() ?
                new Pos2D(point.x() - 1, point.y() + 1) :
                new Pos2D(point.x() + 1, point.y() - 1)
        );

        return frontalEdges;
    }
}
