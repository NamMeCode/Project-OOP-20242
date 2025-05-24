package player;

public class Player extends Actor {

    public Player(String gameType) {
        setGameType(gameType);
        setRule(gameType);
    }

    public Player(String gameType, int initialStack) {
        super(gameType, initialStack);
    }
}