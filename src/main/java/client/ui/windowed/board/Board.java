package client.ui.windowed.board;

import game.observer.battleship.PlayersData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board extends JPanel {

    private List<PaintableBoardCell> cells = new ArrayList<>();
    private List<PaintableShip> ships = new ArrayList<>();

    public Board() {
        setPreferredSize(new Dimension(1000, 1000));
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
}
