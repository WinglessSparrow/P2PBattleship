package client.ui.windowed;

import client.ui.windowed.board.PlayingBoard;
import game.BattleshipGame;
import game.CoinFlipManager;
import game.GameSetup;
import game.GameState;
import game.observer.base.Observer;
import game.observer.battleship.BattleShipGameData;
import game.player.Player;
import game.player.ShipType;

import javax.swing.*;
import java.awt.*;

public class BattleshipPane extends JPanel implements Observer<BattleShipGameData> {

    private final BattleshipGame battleship;
    private final PlayingBoard playersBoard;
    private final PlayingBoard opponentsBoard;
    private boolean displayOpponentBoard = false;

    public BattleshipPane() {
        playersBoard = new PlayingBoard(new Dimension(400, 400), null);
        opponentsBoard = new PlayingBoard(new Dimension(400, 400), null);

        setBackground(Color.GREEN);

        final var setup = new GameSetup(10, 10, new Player("1"), new Player("2"));

        battleship = new BattleshipGame(setup, new CoinFlipManager(false));

        battleship.getSubject().register(this);

        add(playersBoard);
    }

    @Override
    public void update(BattleShipGameData data) {
        playersBoard.updateData(data.playersData());

        if (data.gameState() != GameState.SETUP) {

            if (!displayOpponentBoard) {
                displayOpponentBoard = true;
                add(opponentsBoard);
            }

            opponentsBoard.updateData(data.opponentsData());
        }

    }
}
