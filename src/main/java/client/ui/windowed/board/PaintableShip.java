package client.ui.windowed.board;

import game.ship.Ship;
import game.ship.SpritesLoader;

import java.awt.*;

public class PaintableShip {
    private final Ship ship;
    private final PaintableBoardCell cell;
    private final boolean ghosting;

    public PaintableShip(Ship ship, PaintableBoardCell cell) {
        this.ship = ship;
        this.cell = cell;
        this.ghosting = false;
    }

    public PaintableShip(Ship ship, PaintableBoardCell cell, boolean ghosting) {
        this.ship = ship;
        this.cell = cell;
        this.ghosting = ghosting;
    }

    void paint(Graphics g) {
        final var pos = cell.getRelativePosition();
        final var pivot = new Point(pos.x + cell.getWidth() / 2, pos.y + cell.getHeight() / 2);
        final var g2d = (Graphics2D) g;

        if (ghosting) {
            final var alpha = 0.5f;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        }

        if (!ship.vertical()) g2d.rotate(Math.toRadians(-90), pivot.x, pivot.y);

        g.drawImage(SpritesLoader.getShipSprite(ship.type()),
                    pos.x,
                    pos.y,
                    cell.getWidth(),
                    cell.getHeight() * ship.type().getSize(),
                    null
        );

        if (!ship.vertical()) g2d.rotate(Math.toRadians(90), pivot.x, pivot.y);

        if (ghosting) {
            final var alpha = 1f;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        }
    }
}
