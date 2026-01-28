package client.ui.windowed;

import game.BattleshipGame;
import game.CoinFlipManager;
import game.GameSetup;
import game.player.Player;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UI extends JFrame {
    public UI() {
        super("Battleship Client");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(2200, 1200);
        setResizable(false);

        final var setup = new GameSetup(10, 10, new Player("1"), new Player("2"));
        final var battleship = new BattleshipGame(setup, new CoinFlipManager(false));

        add(new BoardBuilder(battleship));

        final var executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate(this::repaint, 0, 10, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(battleship::notifyUpdate, 0, 300, TimeUnit.MILLISECONDS);
    }
}
