package client.ui.windowed.pages;

import client.ui.Windows;
import client.ui.windowed.Navigator;
import client.ui.windowed.utils.BattleshipStore;
import game.BattleshipGame;
import game.CoinFlipManager;
import game.GameSetup;
import game.player.Player;
import game.player.PlayerManager;
import game.player.PlayerType;

import javax.swing.*;

public class JoinGame extends JPanel {
    public JoinGame() {
        add(new JLabel("Join Game"));

        final var btn = new JButton("Join");
        btn.addActionListener(_ -> {
            Navigator.get().navigate(Windows.GAME);

            final var gameSetup = new GameSetup(10, 10);
            final var player = new Player("self guest", PlayerType.SELF_GUEST, false);
            final var player2 = new Player("remote host", PlayerType.REMOTE_HOST, false);

            BattleshipStore.set(new BattleshipGame(gameSetup, new CoinFlipManager(false), new PlayerManager(player, player2)));
        });

        add(btn);
    }
}
