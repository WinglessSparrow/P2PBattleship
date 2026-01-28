package client.ui.windowed;

import client.ui.windowed.board.SetupPlayingBoard;
import game.BattleshipGame;
import game.board.boardRules.BrokenRuleException;
import game.observer.base.Observer;
import game.observer.battleship.BattleShipGameData;
import game.ship.Ship;

import javax.swing.*;
import java.awt.*;
import java.io.Closeable;
import java.io.IOException;

public class BoardBuilder extends JPanel implements Observer<BattleShipGameData>, Closeable {

    private final BattleshipGame battleship;
    private final SetupPlayingBoard playersBoard;
    private final ShipsChooserPane shipsChooserPane;

    public BoardBuilder(BattleshipGame battleship) {
        this.battleship = battleship;

        battleship.subject.register(this);

        this.playersBoard = new SetupPlayingBoard(new Dimension(400, 400), this::setShip);
        this.shipsChooserPane = new ShipsChooserPane(playersBoard::setChosenShip);

        add(shipsChooserPane);
        add(playersBoard);
    }

    public void setShip(Ship ship) {
        try {
            battleship.addShip(ship);

            shipsChooserPane.update(ship.type());
        } catch (BrokenRuleException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(BattleShipGameData data) {
        playersBoard.updateData(data.playersData());
    }

    @Override
    public void close() throws IOException {
        battleship.getSubject().deregister(this);
    }
}
