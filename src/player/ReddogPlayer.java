package player;

public class ReddogPlayer extends ChipActor {
    public ReddogPlayer(int chipStack) {
        super("Reddog", chipStack);
    }
    public int getSpread()
    {
        if (cardsOnHand.getSize()!=3)
            return -2;
        return Math.abs(cardsOnHand.getCardAt(0).getRank()-cardsOnHand.getCardAt(1).getRank())-1;
    }
}
