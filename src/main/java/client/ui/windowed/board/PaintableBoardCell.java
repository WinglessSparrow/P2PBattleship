package client.ui.windowed.board;

import game.board.BoardCell;

import java.awt.*;

public class PaintableBoardCell {

    private final BoardCell cell;
    private final int width;
    private final int height;
    private final Point relativePosition;

    public PaintableBoardCell(BoardCell cell, int width, int height) {
        this.cell = cell;
        this.width = width;
        this.height = height;
        relativePosition = new Point(cell.position().x() * width, cell.position().y() * height);
    }

    public void paintBackground(Graphics g, Point mousePosition) {
        final var mousePos = Grid2DUtils.getMouseGridPosition(mousePosition, width, height);

        g.setColor(cell.isHit() ? new Color(80, 215, 172) : Color.WHITE);

        if (cell.position().x() == mousePos.x && cell.position().y() == mousePos.y) {
            g.setColor(Color.BLUE);
            g.fillRect(relativePosition.x, relativePosition.y, width - 1, height - 1);
        }
    }


    public void paintForeground(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(cell.position().x() * width, cell.position().y() * height, width - 1, height - 1);
    }

    public Point getCenter() {
        return new Point(cell.position().x() * width + width / 2, cell.position().y() * height + height / 2);
    }

    public BoardCell getCell() {
        return cell;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Point getRelativePosition() {
        return relativePosition;
    }
}
