package client.ui.windowed;

import client.ui.windowed.board.Board;
import game.BattleshipGame;
import game.CoinFlipManager;
import game.GameSetup;
import game.board.Pos2D;
import game.board.boardRules.BrokenRuleException;
import game.observer.base.Observer;
import game.observer.battleship.BattleShipGameData;
import game.player.Player;
import game.player.ShipType;
import game.ship.Ship;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;

public class BattleshipPane extends JPanel implements Observer<BattleShipGameData> {

    private final BattleshipGame battleship;
    private final Board board;
    private final Board secondBoard;

    public BattleshipPane() {
        board = new Board();
        secondBoard = new Board();

        setBackground(Color.GREEN);

        final var setup = new GameSetup(10, 10, new Player("1"), new Player("2"));

        battleship = new BattleshipGame(setup, new CoinFlipManager(false));

        battleship.getSubject().register(this);

        add(board);
        add(secondBoard);

        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(() -> {

            try {
                battleship.addShip(new Ship(ShipType.CARRIER, true, new Pos2D(1, 1)));
                battleship.addShip(new Ship(ShipType.BATTLESHIP, false, new Pos2D(3, 1)));
//                battleship.addShip(new Ship(ShipType.FRIGATE, true, new Pos2D(4, 5)));
//                battleship.addShip(new Ship(ShipType.SUBMARINE, true, new Pos2D(8, 8)));
            } catch (BrokenRuleException e) {
                throw new RuntimeException(e);
            }

            battleship.ready(false);
        }, 100, 100, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    @Override
    public void update(BattleShipGameData data) {
        board.updateData(data.playersData());
        secondBoard.updateData(data.opponentsData());
    }
}
