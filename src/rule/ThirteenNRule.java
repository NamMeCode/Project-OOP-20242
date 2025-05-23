package rule;

import card.Card;
import card.ListOfCards;

public class ThirteenNRule extends ThirteenRule {
    public boolean checkPair(ListOfCards cards) {
        return cards.getCardAt(0).equals(cards.getCardAt(1)) &&
                cards.getCardAt(0).checkSameColour(cards.getCardAt(1));
    }

    public boolean checkSequence(ListOfCards cards) {
        for(int i = 0; i < cards.getSize() - 1; i++) {
            if(cards.getCardAt(i).getRank() == 15) return false;
            Card card1 = cards.getCardAt(i), card2 = cards.getCardAt(i + 1);
            if((card1.getRank() != card2.getRank() + 1) || (!card1.checkSameColour(card2)))
                return false;
        }
        return true;
    }

    public boolean checkDoubleSequence(ListOfCards cards) {
        if(cards.getSize() % 2 == 1) return false;
        for(int i = 0; i < cards.getSize() - 1; i++) {
            Card card1 = cards.getCardAt(i), card2 = cards.getCardAt(i + 1);
            if(i % 2 == 0) {
                if((card1.getRank() != card2.getRank()) || (!card1.checkSameColour(card2)))
                    return false;
            }
            else if((card1.getRank() != (card2.getRank() + 1)) || (!card1.checkSameColour(card2)))
                return false;
        }
        return true;
    }

    public String handType(ListOfCards cards) {
        switch (cards.getSize()) {
            case 1: return "Single";
            case 2: if(checkPair(cards)) return "Pair";
            case 3:
                if(checkThreeCardsSameRank(cards)) return "ThreeOfAKind";
                else if(checkSequence(cards)) return "Sequence";
                else return "Invalid";
            case 4:
                if(checkFourCardsSameRank(cards)) return "FourOfAKind";
                else if(checkSequence(cards)) return "Sequence";
                else return "Invalid";
            case 5:
                if(checkSequence(cards)) return "Sequence";
                else return "Invalid";
            default:
                if(checkSequence(cards)) return "Sequence";
                else if(checkDoubleSequence(cards)) return "DoubleSequence";
                else return "Invalid";
        }
    }

    public boolean checkWinCondition(ListOfCards handCards) {
        // win with no card on hand
        return handCards.getCardList().isEmpty();
    }
}
