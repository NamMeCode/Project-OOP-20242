package rule;

import card.ListOfCards;

public abstract class GameRule {
    public static boolean checkTwoCardsSameRank(ListOfCards cards) {
        return cards.getCardAt(0).equals(cards.getCardAt(1));
    }

    public static boolean checkThreeCardsSameRank(ListOfCards cards) {
        return cards.getCardAt(0).equals(cards.getCardAt(1)) &&
                cards.getCardAt(1).equals(cards.getCardAt(2));
    }

    public static boolean checkFourCardsSameRank(ListOfCards cards) {
        return cards.getCardAt(0).equals(cards.getCardAt(1)) &&
                cards.getCardAt(1).equals(cards.getCardAt(2)) &&
                cards.getCardAt(2).equals(cards.getCardAt(3));
    }

    public static boolean checkContinuousRank(ListOfCards cards) {
        for(int i = 0; i < cards.getSize() - 1; i++) {
            if(cards.getCardAt(i).getRank() + 1 != cards.getCardAt(i+1).getRank())
                return false;
        }
        return true;
    }

    public static boolean checkFlush(ListOfCards cards) {
        for(int i = 0; i<cards.getSize() - 1; i++) {
            if(cards.getCardAt(i).getSuit() != cards.getCardAt(i+1).getSuit())
                return false;
        }
        return true;
    }

}