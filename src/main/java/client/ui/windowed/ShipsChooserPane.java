package client.ui.windowed;

import game.player.ShipType;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.function.Consumer;

public class ShipsChooserPane extends JPanel {

    private final EnumMap<ShipType, ShipChooser> shipChoosers = new EnumMap<>(ShipType.class);

    public ShipsChooserPane(Consumer<ShipType> onClick) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final var ships = Arrays
                .stream(ShipType.values())
                .filter(type -> type != ShipType.UNKNOWN && type != ShipType.EMPTY)
                .toList();

        for (var ship : ships) {
            final var shipGroup = new ShipChooser(ship, onClick);

            shipChoosers.put(ship, shipGroup);
            add(shipGroup);
        }

        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
    }

    public void update(ShipType setShip) {
        final var shipChooser = shipChoosers.get(setShip);

        if (shipChooser != null) {
            shipChooser.incrementAmountOfShipUse();
        }

    }
}
