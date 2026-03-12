package client.ui.windowed;

import client.ui.windowed.utils.GridBagBuilder;
import game.player.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlayerInfoBoard extends JPanel {

    private final PlayerIndicator opponentIndicator;
    private final PlayerIndicator playerIndicator;
    private final JButton btnReady;

    public PlayerInfoBoard(Player player, Player opponent, Runnable onReady, Runnable onCancel) {
        setLayout(new GridBagLayout());

        btnReady = new JButton("Ready");
        btnReady.addActionListener(_ -> onReady.run());
        add(btnReady, new GridBagBuilder().x(0).y(1).fill(GridBagConstraints.BOTH).build());

        final var btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(_ -> onCancel.run());
        add(btnCancel, new GridBagBuilder().x(1).y(1).fill(GridBagConstraints.BOTH).build());

        playerIndicator = new PlayerIndicator(player);
        add(playerIndicator, new GridBagBuilder().x(0).y(0).fill(GridBagConstraints.BOTH).build());

        opponentIndicator = new PlayerIndicator(opponent);
        add(opponentIndicator, new GridBagBuilder().x(1).y(0).fill(GridBagConstraints.BOTH).build());
    }

    public void update(List<Player> players, boolean playersBoardCorrect) {
        opponentIndicator.update(players.getLast().ready());
        playerIndicator.update(players.getFirst().ready());

        btnReady.setEnabled(playersBoardCorrect);
    }
}
