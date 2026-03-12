package client.ui.windowed;

import client.ui.Windows;
import client.ui.windowed.board.PaintableBoardCell;
import client.ui.windowed.board.SetupPlayingBoard;
import client.ui.windowed.utils.GridBagBuilder;
import game.BattleshipGame;
import game.board.boardRules.BrokenRuleException;
import game.observer.base.Observer;
import game.observer.battleship.BattleShipGameData;
import game.ship.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Closeable;

public class BoardBuilderPanel extends JPanel implements Observer<BattleShipGameData>, Closeable {

    private final BattleshipGame battleship;
    private final SetupPlayingBoard playerBoard;
    private final ShipsChooserPane shipsChooserPane;
    private final PlayerInfoBoard playerInfoBoard;

    private final Runnable onStart;

    public BoardBuilderPanel(BattleshipGame battleship, Runnable onStart) {
        this.battleship = battleship;
        this.onStart = onStart;
        this.playerBoard = new SetupPlayingBoard(new Dimension(400, 400), this::onHandleClick);
        this.shipsChooserPane = new ShipsChooserPane(playerBoard::setChosenShip);

        battleship.subject.register(this);

        setLayout(new GridBagLayout());

        add(shipsChooserPane, new GridBagBuilder().x(0).y(0).rowSpan(3).fill(GridBagConstraints.BOTH).build());
        add(new JLabel("Setting Up"), new GridBagBuilder().x(1).y(0).fill(GridBagConstraints.BOTH).build());
        add(playerBoard, new GridBagBuilder().x(1).y(1).anchor(GridBagConstraints.NORTH).build());

        playerInfoBoard = new PlayerInfoBoard(battleship.getPlayers().getFirst(), battleship.getPlayers()
                                                                                            .getLast(), () -> {
            final var player = battleship.getPlayers().getFirst();

            battleship.setPlayerReady(player, !player.ready());
        }, () -> Navigator.get().navigate(Windows.MAIN_MENU));

        add(playerInfoBoard, new GridBagBuilder().x(1).y(2).fill(GridBagConstraints.BOTH).build());
    }

    public void onHandleClick(PaintableBoardCell cell, MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            addAShip(cell);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            removeShip(cell);
        }

    }

    private void addAShip(PaintableBoardCell cell) {
        final var cShipOpt = playerBoard.getChosenShip();

        if (cShipOpt.isEmpty()) {
            return;
        }

        final var cShip = cShipOpt.get();
        final var ship = new Ship(cShip.type(), cShip.vertical(), cell.getCell().position());

        try {
            battleship.addShip(ship);

            shipsChooserPane.update(ship.type(), false);
        } catch (BrokenRuleException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

        playerBoard.setChosenShip(null);
    }

    public void removeShip(PaintableBoardCell cell) {
        final var shipOpt = battleship.removeShipAtPosition(cell.getCell().position());

        shipOpt.ifPresent(ship -> shipsChooserPane.update(ship.type(), true));
    }

    @Override
    public void update(BattleShipGameData data) {
        playerBoard.updateData(data.playersData());
        playerInfoBoard.update(battleship.getPlayers(), battleship.isPlayerSetupCorrectly());

        if (data.playersData().player().ready() && data.opponentsData().player().ready()) {
            onStart.run();
        }

    }

    @Override
    public void close() {
        battleship.getSubject().deregister(this);
    }
}
