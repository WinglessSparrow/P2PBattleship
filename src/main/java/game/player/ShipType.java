package game.player;

import java.util.Arrays;

public enum ShipType {
    CARRIER('C', 5, 1),
    BATTLESHIP('B', 4, 2),
    FRIGATE('F', 3, 3),
    DESTROYER('D', 2, 4),
    SUBMARINE('S', 1, 1),
    EMPTY('E', 0, 0),
    UNKNOWN('?', 0, 0);

    private final char value;
    private final int size;
    private final int maxAmount;

    ShipType(char value, int size, int maxAmount) {
        this.value = value;
        this.size = size;
        this.maxAmount = maxAmount;
    }

    public char getValue() {
        return value;
    }

    public int getSize() {
        return size;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public static ShipType fromChar(char value) {
        return Arrays.stream(ShipType.values()).filter(s -> s.getValue() == value).findFirst().orElse(UNKNOWN);
    }
}
