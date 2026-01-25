package client.ui.windowed.board;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Grid2DUtils {
    public static Point getMouseGridPosition(Point mousePos, int width, int height) {
        if (mousePos == null) return new Point(-1, -1);

        final int xPos = mousePos.x / width;
        final int yPos = mousePos.y / height;

        return new Point(xPos, yPos);
    }
}
