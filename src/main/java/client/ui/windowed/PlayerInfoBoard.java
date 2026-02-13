package client.ui.windowed;

import client.ui.windowed.utils.GridBagBuilder;
import game.player.Player;
import game.player.PlayerType;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class PlayerInfoBoard extends JPanel {

    private final JPanel readyIndicator;
    private final JButton readyBtn;

    public PlayerInfoBoard(Player player, Consumer<Player> onReady) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setLayout(new GridBagLayout());

        var c = new GridBagBuilder().x(0).y(0).fill(GridBagConstraints.BOTH).build();

        add(new JLabel(player.name()), c);

        readyIndicator = new JPanel();
        readyIndicator.setBackground(Color.RED);
        readyIndicator.setPreferredSize(new Dimension(20, 20));
        readyIndicator.setOpaque(true);

        c = new GridBagBuilder().x(1).y(0).fill(GridBagConstraints.BOTH).build();
        add(readyIndicator, c);

        if (player.type() == PlayerType.SELF_GUEST || player.type() == PlayerType.SELF_HOST) {
            readyBtn = new JButton("Ready");
            readyBtn.addActionListener(_ -> onReady.accept(player));

            c = new GridBagBuilder().x(0).y(1).fill(GridBagConstraints.BOTH).colSpan(2).build();
            add(readyBtn, c);
        } else {
            readyBtn = null;
        }
    }

    public void update(Player player) {
        if (player.ready()) {
            readyIndicator.setBackground(Color.GREEN);

            if (readyBtn != null) readyBtn.setEnabled(false);
        }
    }
}
