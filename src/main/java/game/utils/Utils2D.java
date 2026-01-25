package game.utils;

import game.board.Board;
import game.board.Pos2D;

public class Utils2D {
    private Utils2D() {}

    public static boolean isOutbound(Pos2D p, Board board) {
        return p.y() < 0 || p.x() < 0 || p.y() >= board.getRows() || p.x() >= board.getCols();
    }
}
