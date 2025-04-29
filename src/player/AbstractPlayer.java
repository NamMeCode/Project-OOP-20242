package player;
import card.Card;
import card.ListOfCards;
import rule.GameRule;
import rule.ThirteenNRule;
import rule.ThirteenSRule;

public abstract class AbstractPlayer {
    private ListOfCards cardsOnHand= new ListOfCards();
    private String gameType;
    GameRule rule;
    private static int idGenerator=0;
    public int id;
    public AbstractPlayer() {
        idGenerator++;
        id = idGenerator;
    }
    public int getId() {
        return id;
    }
    public void setCardsOnHand(ListOfCards cardsOnHand) {
        this.cardsOnHand = cardsOnHand;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
    public void setRule(String gameType) {
        switch (gameType) {
            case "ThirteenS":
            {
                this.rule=new ThirteenSRule();
                break;
            }

            case "ThirteenN":
            {
                this.rule=new ThirteenNRule();
                break;
            }
        }
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
    public boolean playCards(ListOfCards cardsOnTable) {
        ListOfCards cardsPlayed = cardsOnHand.cardsSelected();

        if (rule.checkValidPlay(cardsPlayed,cardsOnTable))
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
    public String toStringCardsOnHand()
    {

        return cardsOnHand.toString();
    }
    public String toStringCardsSelected()
    {

        return cardsOnHand.cardsSelected().toString();
    }
    public boolean isWin()
    {
        return rule.checkWinCondition(cardsOnHand);
    }
    public void sortCardsOnHand()
    {
        cardsOnHand.sort();
    }




}
