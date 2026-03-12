package client.ui.windowed;

import client.ui.windowed.utils.GridBagBuilder;
import game.player.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerIndicator extends JPanel {

    private final JPanel indicator;

    public PlayerIndicator(Player player) {
        setLayout(new GridBagLayout());

        add(new JLabel(player.name()), new GridBagBuilder().x(0).y(0).build());

        indicator = new JPanel();
        indicator.setPreferredSize(new Dimension(20, 20));
        indicator.setBackground(Color.RED);

        add(indicator, new GridBagBuilder().x(1).y(0).fill(GridBagConstraints.VERTICAL).build());
    }

    public void update(boolean isReady) {
        if (isReady) indicator.setBackground(Color.GREEN);
    }
}
