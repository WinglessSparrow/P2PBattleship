package game.board.boardRules;

public class OutOfBoundsException extends BrokenRuleException {
    public OutOfBoundsException(String message) {
        super(BoardRule.class, message);
    }
}
