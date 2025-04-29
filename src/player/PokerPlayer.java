package player;

import card.ListOfCards;

public class PokerPlayer extends Player{
    private String handType;
    private ListOfCards bestCards;

    public PokerPlayer(String gameType) {
        super(gameType);
    }

    public String getHandType() {
        return handType;
    }

    public void setHandType(String handType) {
        this.handType = handType;
    }

    public ListOfCards getBestCards() {
        return bestCards;
    }

    public void setBestCards(ListOfCards bestCards) {
        this.bestCards = bestCards;
    }
}
