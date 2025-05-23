package rule;

import card.Card;
import card.ListOfCards;

public class ThirteenNRule extends ThirteenRule {
    public static boolean checkPair(ListOfCards cards) {
        return cards.getCardAt(0).equals(cards.getCardAt(1)) &&
                cards.getCardAt(0).checkSameColour(cards.getCardAt(1));
    }

    public static boolean checkSequence(ListOfCards cards) {
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
                if(checkThreeOfAKind(cards)) return "ThreeOfAKind";
                else if(checkSequence(cards)) return "Sequence";
                else return "Invalid";
            case 4:
                if(checkFourOfAKind(cards)) return "FourOfAKind";
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

    public boolean checkValidPlay(ListOfCards playCards, ListOfCards tableCards) {
        if (playCards.getSize()==0)
            return false;
        if(tableCards.getSize() == 0) {
            playCards.sortRankSuit();
            return !handType(playCards).equals("Invalid");
        }

        if(playCards.getSize() != tableCards.getSize()) {
            if(tableCards.getCardAt(0).getRank() != 15) return false;
            if(tableCards.getSize() == 1) {
                if(checkFourOfAKind(playCards) || checkDoubleSequence(playCards)) return true;
            }
            else if(tableCards.getSize() == 2) {
                if(checkDoubleSequence(playCards) && playCards.getSize() >= 8) return true;
            }
            else if(tableCards.getSize() == 3) {
                if(checkDoubleSequence(playCards) && playCards.getSize() >= 10) return true;
            }
            else return false;
        }

        playCards.sortRankSuit();
        tableCards.sortRankSuit();
        String typePlayCards = handType(playCards);
        String typeTableCards = handType(tableCards);
        if(!typePlayCards.equals(typeTableCards)) return false;

        return playCards.getCardAt(playCards.getSize() - 1).compareCard(tableCards.getCardAt(tableCards.getSize() - 1)) > 0;
    }
    public boolean checkWinCondition(ListOfCards handCards) {
        // win with no card on hand
        return handCards.getCardList().isEmpty();
    }
}
