package client.ui.windowed;

import javax.swing.*;
import java.util.concurrent.Executors;

public class UI extends JFrame {
    public UI() {
        super("Battleship Client");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(1200, 1200);
        setResizable(false);

        add(new BattleshipPane());

        final var executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::repaint, 0, 10, java.util.concurrent.TimeUnit.MILLISECONDS);
    }
}
