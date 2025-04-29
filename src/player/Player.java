package player;

public class Player extends AbstractPlayer {

    public Player(String gameType) {
        setGameType(gameType);
        setRule(gameType);
    }
}
