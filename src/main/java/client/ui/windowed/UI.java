package client.ui.windowed;

import client.ui.Windows;
import client.ui.windowed.packages.Game;
import client.ui.windowed.packages.HostGame;
import client.ui.windowed.packages.JoinGame;
import client.ui.windowed.packages.MainMenu;

import javax.swing.*;
import java.awt.*;

public class UI extends JFrame {
    public UI() {
        super("Battleship Client");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(2200, 1200);
        setResizable(false);

        CardLayout layout = new CardLayout();

        getContentPane().setLayout(layout);

        Navigator.get().init((JPanel) getContentPane());

        add(Windows.MAIN_MENU.getName(), new MainMenu());
        add(Windows.JOIN_GAME.getName(), new JoinGame());
        add(Windows.HOST_GAME.getName(), new HostGame());
        add(Windows.GAME.getName(), new Game());

        //        final var setup = new GameSetup(10, 10);
//
//        final var player = new Player("self", PlayerType.SELF_GUEST, false);
//        final var opponent = new Player("opponent", PlayerType.REMOTE_HOST, false);
//
//        final var battleship = new BattleshipGame(setup, new CoinFlipManager(false), new PlayerManager(player, opponent));
//
//        add(new BoardBuilder(battleship));
//
//        final var executor = Executors.newScheduledThreadPool(2);
//        executor.scheduleAtFixedRate(this::repaint, 0, 10, TimeUnit.MILLISECONDS);
//        executor.scheduleAtFixedRate(battleship::notifyUpdate, 0, 300, TimeUnit.MILLISECONDS);
    }
}
