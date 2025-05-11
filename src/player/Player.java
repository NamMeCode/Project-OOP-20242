package player;

public class Player extends AbstractPlayer {

    public Player(String gameType) {

        setGameType(gameType);
        setRule(gameType);
    }
    public Player(String gameType, int chipStack) {
        setChipStack(chipStack);
        setGameType(gameType);
        setRule(gameType);
    }

}
