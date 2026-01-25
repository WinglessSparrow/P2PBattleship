package game.board;

import game.board.boardRules.BoardRule;
import game.board.boardRules.BrokenRuleException;
import game.player.Player;
import game.player.ShipType;
import game.ship.Ship;
import game.utils.Utils2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Board {
    public static final int NO_SHIP_FOUND = -1;

    private final Player owner;
    private final int cols;
    private final int rows;

    private final BoardCell[][] board;
    private final List<BoardRule> rules;

    private final List<Ship> ships = new ArrayList<>();

    public Board(Player owner, int cols, int rows, List<BoardRule> rules) {
        this.owner = owner;
        this.cols = cols;
        this.rows = rows;
        this.rules = rules;

        this.board = populateBoard();
    }

    protected BoardCell[][] populateBoard() {
        final var newBoard = new BoardCell[cols][rows];

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                newBoard[x][y] = new BoardCell(ShipType.EMPTY, new Pos2D(x, y), false);
            }
        }

        return newBoard;
    }

    public void addShip(final Ship ship) throws BrokenRuleException {
        checkShipBounds(ship);

        for (BoardRule rule : rules) {
            rule.verify(this, ship);
        }

        ship.cells().forEach(c -> {
            board[c.position().x()][c.position().y()] = new BoardCell(ship.type(), c.position(), false);
        });

        ships.add(ship);
    }

    public Optional<Ship> hit(final Pos2D at) {
        checkBounds(at);

        final var cell = board[at.x()][at.y()];

        if (cell.isHit()) {
            return Optional.empty();
        }

        board[at.x()][at.y()] = cell.hit();

        if (cell.shipType() != ShipType.EMPTY) {
            final var shipIdx = getShipIdx(at);

            if (shipIdx > NO_SHIP_FOUND) {
                final var ship = ships.get(shipIdx).hit(at);

                ships.set(shipIdx, ship);

                return Optional.of(ship);
            }
        }

        return Optional.empty();
    }

    public BoardCell[][] getBoardCells() {
        BoardCell[][] boardState = new BoardCell[cols][rows];

        for (int i = 0; i < cols; i++) {
            boardState[i] = Arrays.copyOf(board[i], rows);
        }

        return boardState;
    }

    public String getStringRepresentation() {
        return Arrays.deepToString(board);
    }

    protected Optional<BoardCell> getBoardCell(Pos2D at) {
        if (!Utils2D.isOutbound(at, this)) {
            return Optional.of(board[at.x()][at.y()]);
        }

        return Optional.empty();
    }

    public Optional<Ship> getAShip(Pos2D at) {
        return Optional.of(ships.get(getShipIdx(at)));
    }

    public List<Ship> getAllShips() {
        return new ArrayList<>(ships);
    }

    protected List<Ship> getShips() {
        return ships;
    }

    protected BoardCell[][] getBoard() {
        return board;
    }


    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public Player getOwner() {
        return owner;
    }

    private int getShipIdx(final Pos2D at) {
        final var ship = ships.stream().filter(s -> s.cells().stream().anyMatch(c -> c.position().equals(at)))
                              .findFirst();

        return ship.map(ships::indexOf).orElse(NO_SHIP_FOUND);

    }

    private void checkBounds(final Pos2D at) {
        if (at.x() >= cols || at.y() >= rows) {
            throw new IllegalArgumentException("Point is out of bounds");
        }
    }

    private void checkShipBounds(final Ship ship) {
        final var at = ship.cells().getFirst().position();

        final var outOfBounds = ship.vertical() ?
                                at.y() + ship.type().getSize() > rows :
                                at.x() + ship.type().getSize() > cols;
        if (outOfBounds) {
            throw new IllegalArgumentException("Ship is out of bounds");
        }
    }
}
