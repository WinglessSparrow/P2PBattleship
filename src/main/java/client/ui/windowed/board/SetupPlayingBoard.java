package client.ui.windowed.board;

import game.player.ShipType;
import game.ship.Ship;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SetupPlayingBoard extends PlayingBoard {

    private Ship chosenShip = null;

    public SetupPlayingBoard(Dimension preferredSize, BiConsumer<PaintableBoardCell, MouseEvent> onClick) {
        super(preferredSize, onClick);

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);

                if (chosenShip != null) {
                    chosenShip = chosenShip.rotate(e.getWheelRotation() > 0);
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (chosenShip == null || data == null) return;

        final var hoveredCell = getHoveredCell();

        if (hoveredCell != null) {
            final var paintableShip = new PaintableShip(chosenShip, hoveredCell, true);
            paintableShip.paint(g);
        }
    }

    private PaintableBoardCell getHoveredCell() {
        final var mousePos = Grid2DUtils.getMouseGridPosition(
                getMousePosition(),
                getWidth() / data.cols(),
                getHeight() / data.rows()
        );

        return cells.stream()
                    .filter(c -> c.getGridPosition().equals(mousePos))
                    .findFirst()
                    .orElse(null);
    }

    public void setChosenShip(ShipType chosenShip) {
        if (chosenShip == null) {
            this.chosenShip = null;
            return;
        }

        this.chosenShip = new Ship(chosenShip, false);
    }

    public Ship getChosenShip() {
        return chosenShip;
    }
}
