package client.ui.windowed.pages;

import client.ui.Windows;
import client.ui.windowed.Navigator;
import client.ui.windowed.utils.GridBagBuilder;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    public static final int BTN_WIDTH = 400;
    public static final int BTN_HEIGHT = 100;
    public static final Font FONT = new Font("Arial", Font.PLAIN, 25);

    private final JButton btn_join = new JButton("Join Game");
    private final JButton btn_host = new JButton("Host Game");
    private final JButton btn_close = new JButton("Close");

    public MainMenu() {
        setLayout(new GridBagLayout());
        setUpButton(btn_join);
        setUpButton(btn_host);
        setUpButton(btn_close);

        final var nav = Navigator.get();
        btn_join.addActionListener(_ -> nav.navigate(Windows.JOIN_GAME));
        btn_host.addActionListener(_ -> nav.navigate(Windows.HOST_GAME));
        btn_close.addActionListener(_ -> System.exit(0));

        var c = new GridBagBuilder().x(0).y(0).insets(10, 0, 10, 0).build();
        add(btn_join, c);

        c = new GridBagBuilder().x(0).y(1).insets(10, 0, 10, 0).build();
        add(btn_host, c);

        c = new GridBagBuilder().x(0).y(2).insets(10, 0, 10, 0).build();
        add(btn_close, c);
    }

    private void setUpButton(JButton btn) {
        btn.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
        btn.setSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));

        btn.setFont(FONT);
    }
}
