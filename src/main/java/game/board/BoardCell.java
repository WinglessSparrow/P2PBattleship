package game.board;

import game.player.ShipType;

public record BoardCell(ShipType shipType, Pos2D position, boolean isHit) {

    public BoardCell hit() {
        return new BoardCell(shipType, position, true);
    }

    public BoardCell setShipType(ShipType shipType) {
        return new BoardCell(shipType, position, isHit);
    }

    @Override
    public String toString() {
        return shipType.getValue() + "";
    }
}
