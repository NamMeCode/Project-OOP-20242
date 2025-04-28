package player;

import card.ListOfCards;
import card.Card;
public class Bot extends AbstractPlayer {
    public Bot(String gameType) {
        setGameType(gameType);

    }
    public void autoPlayCards(ListOfCards cardsOnTable) {
        ListOfCards autoPlayedCards = new ListOfCards();
        //algorithm to find autoPlayedCards
        //......
        //



        for (int i=0; i<autoPlayedCards.getSize(); i++) {
            Card card = autoPlayedCards.getCardAt(i);
            card.setSelected(true);
        }
        playCards(cardsOnTable);


    };
}
