package client.ui.windowed.board;

import game.player.ShipType;
import game.ship.Ship;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PaintableShip {
    private final Ship ship;
    private final PaintableBoardCell cell;

    public PaintableShip(Ship ship, PaintableBoardCell cell) {
        this.ship = ship;
        this.cell = cell;
    }

    void paint(Graphics g) {
        final var width = 10;
        final var center = cell.getCenter();

        //TODO submarine size < 2 exception

        final var posX = ship.vertical() ? center.x - width / 2 : center.x;
        final var posY = ship.vertical() ? center.y : center.y - width / 2;

        final var shipWidth = ship.vertical() ? width : ship.type().getSize() * cell.getWidth();
        final var shipHeight = ship.vertical() ? ship.type().getSize() * cell.getHeight() : width;

        if (ship.type() == ShipType.CARRIER) {
            try {
                var pos = cell.getRelativePosition();
                var images = ImageIO.read(PaintableShip.class.getResource("/ships/carrier.png"));

                g.drawImage(images, pos.x, pos.y, cell.getWidth(), cell.getHeight() * 5, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (ship.type() == ShipType.BATTLESHIP) {
            try {
                var pos = cell.getRelativePosition();
                var images = ImageIO.read(PaintableShip.class.getResource("/ships/battleship.png"));

                if (!ship.vertical()) {
                    ((Graphics2D) g).rotate(Math.toRadians(-90), pos.x + cell.getWidth() / 2,
                                            pos.y + cell.getHeight() / 2);
                }

                g.drawImage(images, pos.x, pos.y, cell.getWidth(), cell.getHeight() * 4, null);

                if (!ship.vertical()) {
                    ((Graphics2D) g).rotate(Math.toRadians(90), pos.x + cell.getWidth() / 2,
                                            pos.y + cell.getHeight() / 2);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            g.setColor(Color.GRAY);
            g.fillRect(posX, posY, shipWidth, shipHeight);
        }


    }
}
