package client.ui.windowed.board;

import game.player.ShipType;
import game.ship.Ship;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.function.Consumer;

public class SetupPlayingBoard extends PlayingBoard {

    private final Consumer<Ship> onSetShip;
    private Ship chosenShip = null;

    public SetupPlayingBoard(Dimension preferredSize, Consumer<Ship> onSetShip) {
        super(preferredSize, null);

        this.onSetShip = onSetShip;

        setOnClick((cell) -> {
            if (chosenShip != null) {
                onSetShip.accept(new Ship(chosenShip.type(), chosenShip.vertical(), cell.getCell().position()));
                chosenShip = null;
            }
        });

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
    protected void invokeClick(PaintableBoardCell cell) {
        if (chosenShip != null) {
            final var ship = new Ship(chosenShip.type(), chosenShip.vertical(), cell.getCell().position());

            onSetShip.accept(ship);

            chosenShip = null;
        }
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
}
