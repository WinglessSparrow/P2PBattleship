package client.ui.windowed.board;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Grid2DUtils {
    public static Point getMouseGridPosition(Point mousePos, int cellWidth, int cellHeight) {
        if (mousePos == null || cellWidth <= 0 || cellHeight <= 0) return new Point(-1, -1);

        final int xPos = mousePos.x / cellWidth;
        final int yPos = mousePos.y / cellHeight;

        return new Point(xPos, yPos);
    }
}
