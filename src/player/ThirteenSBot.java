package player;

import card.ListOfCards;
import card.Card;
import rule.ThirteenSRule;

    public class ThirteenSBot extends Actor {
    public ThirteenSBot(String gameType) {
        super(gameType);
        setRule(gameType);
    }

    public boolean autoPlayCards(ListOfCards cardsOnTable) {

        if (cardsOnHand.getSize()<cardsOnTable.getSize()) {
            return false;
        }
        int length=cardsOnTable.getSize();
        ListOfCards autoPlayedCards = new ListOfCards();

        String cardsType = ((ThirteenSRule)rule).handType(cardsOnTable);

        cardsOnTable.sortRankSuit();
        cardsOnHand.sortRankSuit();

        for (int i = 0; i < length; i++) {
            autoPlayedCards.addCard(cardsOnHand.getCardAt(i));
        }
        int last_index=length-1;

        outerwhile: while (last_index<cardsOnHand.getSize()) {
            if (((ThirteenSRule) rule).handType(autoPlayedCards).equals(((ThirteenSRule) rule).handType(cardsOnTable))) {
                if (autoPlayedCards.getCardAt(length-1).getRank()>cardsOnTable.getCardAt(length-1).getRank()) {
                    break outerwhile;
                }
                else if (autoPlayedCards.getCardAt(length-1).getRank()==cardsOnTable.getCardAt(length-1).getRank() &&
                        cardsOnHand.getCardAt(length-1).getSuit() > cardsOnTable.getCardAt(length-1).getSuit())
                {
                    break outerwhile;
                }

            }
            last_index++;
            if (last_index<length) {
                autoPlayedCards.removeCard(0);
                autoPlayedCards.addCard(cardsOnHand.getCardAt(last_index));
            }
        }
        if(autoPlayedCards.getSize() == 0) return false;
        for (int i=0; i<autoPlayedCards.getSize(); i++) {
            Card card = autoPlayedCards.getCardAt(i);
            card.setSelected(true);
        }
        return playCards(cardsOnTable);

    }
}
