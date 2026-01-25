package game.board.boardRules;

public class BrokenRuleException extends Exception {
    private final String brokenRule;

    public BrokenRuleException(Class<? extends BoardRule> brokenRule, String message) {
        this.brokenRule = brokenRule.getSimpleName();
        super(message);
    }

    public String getBrokenRule() {
        return brokenRule;
    }
}
