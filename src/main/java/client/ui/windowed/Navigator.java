package client.ui.windowed;

import client.ui.Windows;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class Navigator {
    private static final Navigator instance = new Navigator();
    private CardLayout layout;
    private JPanel mainPanel;

    private Navigator() {
    }

    public void init(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.layout = mainPanel.getLayout() instanceof CardLayout ? (CardLayout) mainPanel.getLayout() : null;

        if (layout == null) {
            throw new IllegalStateException("Main panel does not use CardLayout");
        }
    }

    public static Navigator get() {
        return instance;
    }

    public void navigate(Windows window) {
        if (layout == null) {
            throw new IllegalStateException("Navigator layout not initialized");
        }

        layout.show(mainPanel, window.getName());
    }
}
