package player;
import card.Card;
import card.ListOfCards;
import rule.GameRule;
import rule.ThirteenSRule;

import java.util.List;

public abstract class AbstractPlayer {
    private ListOfCards cardsOnHand= new ListOfCards();
    private String gameType;

    public void setCardsOnHand(ListOfCards cardsOnHand) {
        this.cardsOnHand = cardsOnHand;
        cardsOnHand.sort();
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

    public ListOfCards playCards() {
        return cardsOnHand.cardsSelected();
    }

    public String toStringCardsOnHand() {
        StringBuilder s = new StringBuilder();
        for (int i=0; i<cardsOnHand.getSize(); i++) {
            Card card = cardsOnHand.getCardAt(i);
            s.append(" | ");
            s.append(card.toString());
        }
        s.append(" | ");
        return s.toString();
    }

    public String toStringCardsSelected() {
        StringBuilder s = new StringBuilder();
        for (int i=0; i<cardsOnHand.getSize(); i++)
        {
            Card card= cardsOnHand.getCardAt(i);
            if (card.isSelected()) {
                s.append(" | ");
                s.append(card.toString());
            }
        }
        s.append(" | ");
        return s.toString();
    }





}
