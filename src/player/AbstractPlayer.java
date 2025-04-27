package player;
import card.Card;
import card.ListOfCards;
import rule.GameRule;
import rule.ThirteenSRuleRemake;

public abstract class AbstractPlayer {
    private ListOfCards cardsOnHand= new ListOfCards();
    private String gameType;
    GameRule rule;

    public void setCardsOnHand(ListOfCards cardsOnHand) {
        this.cardsOnHand = cardsOnHand;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
    public void setRule(String rule) {
        switch (rule) {
            case "ThirteenS":
                this.rule=new ThirteenSRuleRemake();
        }
    }

    public void selectCard(int index) {
        Card cardChosen= cardsOnHand.getCardAt(index);
        if (cardChosen!=null) {
            cardChosen.setSelected(true);

        }


    }
    public void unSelectCard(int index) {
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





}
