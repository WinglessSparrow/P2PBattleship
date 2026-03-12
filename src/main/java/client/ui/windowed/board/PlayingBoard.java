package client.ui.windowed.board;

import game.observer.battleship.PlayersData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class PlayingBoard extends JPanel {

    protected List<PaintableBoardCell> cells = new ArrayList<>();
    protected List<PaintableShip> ships = new ArrayList<>();
    protected PlayersData data;
    private final BiConsumer<PaintableBoardCell, MouseEvent> onClick;

    public PlayingBoard(Dimension preferredSize, BiConsumer<PaintableBoardCell, MouseEvent> onClick) {
        setPreferredSize(preferredSize);
        this.onClick = onClick;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (data != null) {
                    final var width = getWidth() / data.cols();
                    final var height = getHeight() / data.rows();
                    final var mousePos = Grid2DUtils.getMouseGridPosition(e.getPoint(), width, height);
                    final var cell = cells.stream().filter(c -> c.getGridPosition().equals(mousePos)).findFirst()
                                          .orElse(null);

                    invokeClick(cell, e);
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (cells.isEmpty()) return;

        final var mousePos = getMousePosition();

        cells.forEach(c -> c.paintBackground(g, mousePos));
        ships.forEach(s -> s.paint(g));
        cells.forEach(c -> c.paintForeground(g));
    }

    public void updateData(PlayersData data) {
        this.data = data;

        final var width = getWidth() / data.cols();
        final var height = getHeight() / data.rows();

        this.cells = Arrays.stream(data.board()).flatMap(Arrays::stream)
                           .map(bc -> new PaintableBoardCell(bc, width, height)).toList();

        ships = data.ships().stream().map(s -> {
            final var firstCell = cells.stream()
                                       .filter(c -> c.getCell().position().equals(s.cells().getFirst().position()))
                                       .findFirst();

            return new PaintableShip(s, firstCell.orElseThrow());
        }).toList();

        revalidate();
    }

    protected void invokeClick(PaintableBoardCell cell, MouseEvent e) {
        if (onClick != null) {
            onClick.accept(cell, e);
        }
    }
}
