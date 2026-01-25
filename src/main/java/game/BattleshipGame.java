package game;

import game.board.*;
import game.board.boardRules.BoardRule;
import game.board.boardRules.BrokenRuleException;
import game.board.boardRules.MaxShipsAmountRule;
import game.board.boardRules.ShipDistanceRule;
import game.observer.battleship.BattleShipSubject;
import game.observer.battleship.BattleShipGameData;
import game.player.Player;
import game.player.PlayerManager;
import game.player.ShipType;
import game.ship.Ship;

import java.util.List;
import java.util.Optional;

public class BattleshipGame {
    public final static List<BoardRule> rules = List.of(new ShipDistanceRule(), new MaxShipsAmountRule());

    public final Board playersBoard;
    public final UnknownBoard opponentsBoard;
    public final CoinFlipManager coinFlipManager;
    public final PlayerManager playerManager;
    public final BattleShipSubject subject = new BattleShipSubject();

    public GameStateManager stateManager = new GameStateManager();

    public BattleshipGame(GameSetup setup, CoinFlipManager coinFlipManager) {
        this.playerManager = new PlayerManager(setup.player(), setup.opponent());
        this.coinFlipManager = coinFlipManager;

        playersBoard = new Board(setup.player(), setup.boardWidth(), setup.boardHeight(), rules);

        opponentsBoard = new UnknownBoard(setup.opponent(), setup.boardWidth(), setup.boardHeight());
    }

    public void ready(boolean opponentsCoinFlip) {
        if (GameState.SETUP != stateManager.getState()) {
            throw new IllegalStateException("Cannot finish the setup while not in the setup phase");
        }

        final boolean first = coinFlipManager.isFirstMove(opponentsCoinFlip);

        if (!first) playerManager.nextPlayer();

        stateManager.setNextState(GameState.READY);

        subject.notify(BattleShipGameData.from(this));
    }

    public void start() {
        if (GameState.READY != stateManager.getState()) {
            throw new IllegalStateException("Cannot start the game while not in the ready phase");
        }

        stateManager.setNextState(GameState.PLAYING);

        subject.notify(BattleShipGameData.from(this));
    }

    public Player getCurrentPlayer() {
        return playerManager.currentPlayer();
    }

    public void addShip(Ship ship) throws BrokenRuleException {
        if (GameState.SETUP != stateManager.getState()) {
            throw new IllegalStateException("Cannot place a ship outside, ships can only be placed during the setup");
        }

        playersBoard.addShip(ship);

        subject.notify(BattleShipGameData.from(this));
    }

    public Optional<Ship> markOpponentsAttack(Pos2D at) {
        if (GameState.PLAYING != stateManager.getState()) {
            throw new IllegalStateException("Cannot strike a ship while not playing");
        }

        if (!playerManager.currentPlayer().equals(opponentsBoard.getOwner())) {
            throw new IllegalStateException("It's not your turn to strike a ship");
        }

        final var hitShip = playersBoard.hit(at);

        playerManager.nextPlayer();

        verifyGame();

        subject.notify(BattleShipGameData.from(this));

        return hitShip;
    }

    public Optional<Ship> markPlayersAttack(Pos2D at, boolean hasHitSomething, boolean sunk, ShipType sunkShip) {
        if (GameState.PLAYING != stateManager.getState()) {
            throw new IllegalStateException("Cannot strike a ship while not playing");
        }

        if (!playerManager.currentPlayer().equals(playersBoard.getOwner())) {
            throw new IllegalStateException("It's not your turn to strike a ships");
        }

        opponentsBoard.markHit(at, hasHitSomething);

        Optional<Ship> hitShip = Optional.empty();

        if (sunk) {
            hitShip = Optional.of(opponentsBoard.addSunkShip(at, sunkShip));
        }

        playerManager.nextPlayer();

        verifyGame();

        subject.notify(BattleShipGameData.from(this));

        return hitShip;
    }

    public BoardCell[][] getBoard() {
        return playersBoard.getBoardCells();
    }

    public BoardCell[][] getOpponentsBoard() {
        return opponentsBoard.getBoardCells();
    }

    public int getRows() {
        return playersBoard.getRows();
    }

    public int getCols() {
        return playersBoard.getCols();
    }

    public void verifyGame() {
        final boolean lose = playersBoard.getAllShips().stream().allMatch(Ship::isSunk);

        if (lose) {
            finishGame(false);
        }
    }

    public void finishGame(boolean won) {
        stateManager.finish(won);
        subject.notify(BattleShipGameData.from(this));
    }

    public void setOpponentsBoats(BoardCell[][] board, List<Ship> ships) {
        if (stateManager.getState() == GameState.FINISHED) {
            if (stateManager.isWon()) {

            } else {

            }
        } else {
            throw new IllegalStateException("Opponents board can only be set after the game has finished");
        }
    }

    public BattleShipSubject getSubject() {
        return subject;
    }

    public boolean hasPlayerWon() {
        return stateManager.isWon();
    }

    public GameState getGameState() {
        return stateManager.getState();
    }

    public List<Ship> getAllShips() {
        return playersBoard.getAllShips();
    }

    public List<Ship> getAllOpponentsShips() {
        return opponentsBoard.getAllShips();
    }
}
