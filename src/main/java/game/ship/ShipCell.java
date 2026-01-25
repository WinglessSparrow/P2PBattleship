package game.ship;

import game.board.Pos2D;

public record ShipCell(Pos2D position, boolean isHit) {
    public ShipCell(Pos2D position) {
        this(position, false);
    }

    public ShipCell hit() {
        return new ShipCell(position, true);
    }
}
