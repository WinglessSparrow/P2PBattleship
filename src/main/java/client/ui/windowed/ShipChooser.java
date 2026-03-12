package client.ui.windowed;

import game.player.ShipType;
import game.ship.SpritesLoader;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ShipChooser extends JPanel {

    private static final String LABEL_TEXT = "%s: %s / %s";

    private final JLabel label;
    private int amountOfShipUsed = 0;
    private final ShipType ship;

    public ShipChooser(ShipType ship, Consumer<ShipType> onClick) {
        this.ship = ship;

        setPreferredSize(new Dimension(100, 100));

        final var btn = new JButton();
        btn.setPreferredSize(new Dimension(40, 40));
        btn.setIcon(new ImageIcon(SpritesLoader.getShipSprite(ship)));
        btn.addActionListener(_ -> onClick.accept(ship));

        add(btn);

        label = new JLabel(LABEL_TEXT.formatted(ship, amountOfShipUsed, ship.getMaxAmount()));
        add(label);
    }

    public void incrementAmountOfShipUse() {
        amountOfShipUsed++;
        label.setText(LABEL_TEXT.formatted(ship, amountOfShipUsed, ship.getMaxAmount()));
    }


    public void decrementAmountOfShipUse() {
        amountOfShipUsed--;
        label.setText(LABEL_TEXT.formatted(ship, amountOfShipUsed, ship.getMaxAmount()));
    }
}
