package client.ui.windowed.pages;

import client.ui.Windows;
import client.ui.windowed.BattleshipPanel;
import client.ui.windowed.BoardBuilderPanel;
import client.ui.windowed.Navigator;
import client.ui.windowed.utils.BattleshipStore;
import game.BattleshipGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.Closeable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Game extends JPanel implements Closeable {
    private final ScheduledExecutorService executor;

    private BoardBuilderPanel boardBuilder;
    private BattleshipPanel battleship;
    private BattleshipGame game;

    public Game() {

        //TODO move to a handler
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                removeAll();

                final var gameOpt = BattleshipStore.get();
                if (gameOpt.isEmpty()) {
                    Navigator.get().navigate(Windows.MAIN_MENU);
                    return;
                }

                game = gameOpt.get();

                boardBuilder = new BoardBuilderPanel(game, () -> {
                    //todo set up the playing board
                    System.out.println("start");
                });

                add(boardBuilder);
                revalidate();
            }
        });

        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            if (game != null) {
                game.notifyUpdate();
            }
        }, 0, 300, java.util.concurrent.TimeUnit.MILLISECONDS);

    }

    @Override
    public void close() {
        executor.shutdown();
        executor.close();
    }
}
