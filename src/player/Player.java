package player;

public class Player extends Actor {

    public Player(String gameType) { //Player
        actorType = "player";
        setGameType(gameType);
        setRule(gameType);
    }
    public Player(String gameType, int chipStack) {
        actorType= "player";//PLayer with bet
        setChipStack(chipStack);
        setGameType(gameType);
        setRule(gameType);
    }

}
