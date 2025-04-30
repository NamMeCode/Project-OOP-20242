package rule;

import card.ListOfCards;

public class ThirteenNRule extends GameRule {
    public boolean checkDoubleSequence(ListOfCards cards) {
        if(cards.getSize() % 2 == 1) return false;
        for(int i = 0; i < cards.getSize() - 1; i++) {
            if(i % 2 == 0) {
                if(cards.getCardAt(i).getRank() != cards.getCardAt(i+1).getRank())
                    return false;
            }
            else if(cards.getCardAt(i).getRank() != (cards.getCardAt(i+1).getRank() + 1))
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
        if(playCards.getSize() != tableCards.getSize()) return false;

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
