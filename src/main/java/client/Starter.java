package client;

import client.ui.console.Console;
import client.ui.windowed.UI;

import java.util.concurrent.Executors;

public class Starter {
    static void main() {
//        new Console().start();

        final var ui = new UI();
        ui.setVisible(true);

        final var executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(ui::repaint, 0, 10, java.util.concurrent.TimeUnit.MILLISECONDS);
    }
}
