package client.ui.console;

import game.BattleshipGame;
import game.CoinFlipManager;
import game.GameSetup;
import game.GameState;
import game.board.BoardCell;
import game.board.Pos2D;
import game.board.boardRules.BrokenRuleException;
import game.observer.base.Observer;
import game.observer.battleship.BattleShipGameData;
import game.player.Player;
import game.player.ShipType;
import game.ship.Ship;

import java.util.Scanner;

public class Console implements Observer<BattleShipGameData> {

    private final BattleshipGame battleship;
    private final Player player = new Player("1");
    private final Player opponent = new Player("2");

    public Console() {
        final var setup = new GameSetup(10, 10, player, opponent);

        battleship = new BattleshipGame(setup, new CoinFlipManager(false));

        battleship.getSubject().register(this);
    }

    public void start() {
        var reader = new Scanner(System.in);

        update(BattleShipGameData.from(battleship));

        for (int i = 0; i < 2; i++) {
            IO.print("chose Ship (C-5, B-4, F-3, D-2, S-1): ");
            var ship = reader.nextLine().charAt(0);
            IO.print("chose Point (x/y): ");
            var point = reader.nextLine();
            IO.print("chose Direction (V/H): ");
            var direction = reader.nextLine().equals("V");

            Ship newShip = new Ship(
                    ShipType.fromChar(ship), direction,
                    new Pos2D(
                            Integer.parseInt(point.charAt(0) + ""),
                            Integer.parseInt(point.charAt(2) + "")
                    )
            );

            try {
                battleship.addShip(newShip);
            } catch (BrokenRuleException e) {
                IO.println(e.getMessage());
                IO.println("Try again");

                i = -1;
            }
        }

        battleship.ready(false);
        battleship.start();

        IO.println("Game started");

        while (battleship.getGameState() != GameState.FINISHED) {
            if (battleship.getCurrentPlayer().equals(player)) {
                IO.print("attack at (x/y): ");
                var line = reader.nextLine();
                var point = new Pos2D(Integer.parseInt(line.charAt(0) + ""), Integer.parseInt(line.charAt(2) + ""));

                battleship.markPlayersAttack(point, Math.random() < 0.5, false, null);
            } else {
                battleship.markOpponentsAttack(new Pos2D((int) (Math.random() * battleship.getCols()),
                                                         (int) (Math.random() * battleship.getRows())));
            }
        }
    }

    private char getValue(int j, BoardCell[] board) {
        return !board[j].isHit() && board[j].shipType() == ShipType.EMPTY ? '*' :
               board[j].isHit() && board[j].shipType() == ShipType.EMPTY ? '☼' : board[j].shipType().getValue();
    }

    @Override
    public void update(BattleShipGameData data) {

        var board = data.playersData().board();
        var oBoard = data.opponentsData().board();

        var cols = data.playersData().cols();
        var rows = data.playersData().rows();

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                System.out.print(getValue(i, board[j]) + "\t");
            }

            System.out.print("|\t");

            for (int j = 0; j < rows; j++) {
                System.out.print(getValue(i, oBoard[j]) + "\t");
            }

            System.out.println();
        }

        IO.println("===");
    }
}
