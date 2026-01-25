package game.player;

import java.util.Arrays;

public enum ShipType {
    CARRIER('C', 5),
    BATTLESHIP('B', 4),
    FRIGATE('F', 3),
    DESTROYER('D', 2),
    SUBMARINE('S', 1),
    EMPTY('E', 0),
    UNKNOWN('?', -1);

    private final char value;
    private final int size;

    ShipType(char value, int size) {
        this.value = value;
        this.size = size;
    }

    public char getValue() {
        return value;
    }

    public int getSize() {
        return size;
    }

    public static ShipType fromChar(char value) {
        return Arrays.stream(ShipType.values()).filter(s -> s.getValue() == value).findFirst().orElse(UNKNOWN);
    }
}
