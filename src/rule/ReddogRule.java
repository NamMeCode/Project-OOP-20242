package rule;

import card.ListOfCards;
import player.Player;

public class ReddogRule extends GameRule {

    public boolean checkWinCondition(ListOfCards handCards)
    {
        if (handCards.getSize()!=3)
            return false;
        int bottom= Math.min(handCards.getCardAt(0).getRank(), handCards.getCardAt(1).getRank());
        int above= Math.max(handCards.getCardAt(0).getRank(), handCards.getCardAt(1).getRank());
        if (bottom<=handCards.getCardAt(2).getRank()&&handCards.getCardAt(2).getRank()<=above)
        {
            return true;
        }
        return false;
    };
    int getSpread(ListOfCards cardsOnHand) {
        if (cardsOnHand.getSize()!=2)
            return -2;
        else
        {
            return Math.abs(cardsOnHand.getCardAt(0).getRank() -cardsOnHand.getCardAt(1).getRank())-1;
        }
    }

}
