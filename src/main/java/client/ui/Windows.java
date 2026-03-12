package client.ui;

public enum Windows {
    MAIN_MENU("mainMenu"), JOIN_GAME("joinGame"), HOST_GAME("hostGame"), SETUP_GAME("setupGame"), GAME("game");

    private final String name;

    Windows(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
