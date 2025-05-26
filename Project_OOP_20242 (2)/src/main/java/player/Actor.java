package player;
import card.Card;
import card.ListOfCards;
import rule.*;

public abstract class Actor {
    ListOfCards cardsOnHand= new ListOfCards();
    String actorType; //player hoặc bot
    String gameType;
    GameRule rule;
    private static int idGenerator=0;
    private final int id;

    public Actor(String gameType) {
        this.gameType = gameType;
        this.id = ++idGenerator;
    }

    public void setRule(String gameType) {
        switch (gameType) {
            case "ThirteenS": {
                this.rule=new ThirteenSRule();
                break;
            }
            case "ThirteenN": {
                this.rule=new ThirteenNRule();
                break;
            }
            case "Reddog": {
                this.rule=new ReddogRule();
                break;
            }
            case "Poker": {
                this.rule=new PokerRule();
            }
        }
    }

    public String getActorType()
    {
        return actorType;
    }

    public int getId() {
        return id;
    }

    public void setCardsOnHand(ListOfCards cardsOnHand) {
        this.cardsOnHand = cardsOnHand;
        cardsOnHand.sortRankSuit();
    }

    public ListOfCards getCardsOnHand() {
        return cardsOnHand;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public void selectCard(int index) {
        Card cardChosen= cardsOnHand.getCardAt(index);
        if (cardChosen!=null) {
            cardChosen.setSelected(true);
        }
    }

    public void unselectCard(int index) {
        Card cardChosen= cardsOnHand.getCardAt(index);
        if (cardChosen!=null) {
            cardChosen.setSelected(false);
        }
    }

    public void addCard(Card card) {
        cardsOnHand.addCard(card);
    }

    public void removeCard(Card card) {
        cardsOnHand.removeCard(card);
    }

    public String toStringCardsOnHand() {
        return cardsOnHand.toString();
    }

    public String toStringCardsSelected() {
        return cardsOnHand.cardsSelected().toString();
    }

    private boolean checkValidPlay(ListOfCards cardsPlayed, ListOfCards cardsOnTable) {
        return switch (gameType) {
            case "ThirteenN" -> ((ThirteenNRule) rule).checkValidPlay(cardsPlayed, cardsOnTable);
            case "ThirteenS" -> ((ThirteenSRule) rule).checkValidPlay(cardsPlayed, cardsOnTable);
            default -> false;
        };
    }

    public boolean playCards(ListOfCards cardsOnTable) {
        ListOfCards cardsPlayed = cardsOnHand.cardsSelected();

        if(cardsPlayed.getSize() == 0) return false;
        if (checkValidPlay(cardsPlayed, cardsOnTable))
        {
            cardsOnTable.replacedBy(cardsPlayed);
            cardsOnHand.replacedBy(cardsOnHand.cardsNotSelected());
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isWin()
    {
        switch(gameType) {
            case "ThirteenN":
                return ((ThirteenNRule) rule).checkWinCondition(cardsOnHand);
            case "ThirteenS":
                return ((ThirteenSRule) rule).checkWinCondition(cardsOnHand);
            case "Reddog":
                return ((ReddogRule) rule).checkWinCondition(cardsOnHand);
        }
        return false;
    }

    public void sortCardsOnHand()
    {
        cardsOnHand.sortRankSuit();
    }
}
