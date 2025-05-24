package rule;

import card.ListOfCards;
import card.Card;

public class ThirteenSRule extends GameRule {
    public static boolean checkSequence(ListOfCards cards) {
        for(int i = 0; i < cards.getSize() - 1; i++) {
            if(cards.getCardAt(i).getRank() == 15) return false;
            if(cards.getCardAt(i).getRank() != cards.getCardAt(i+1).getRank() + 1)
                return false;
        }
        return true;
    }

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

    // should be static
    public boolean checkValidPlay(ListOfCards playCards, ListOfCards tableCards) {
        if (playCards.getSize()==0)
            return false;
        if(tableCards.getSize() == 0) {
            playCards.sortRankSuit();
            return !handType(playCards).equals("Invalid");
        }

        // check bombs
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
        if(handCards.getCardList().isEmpty()) return true;

        if(handCards.getSize() != 13) return false;
        // win with four 2's
        if(handCards.getCardAt(handCards.getSize()).getRank() == 15) {
            ListOfCards tempCards = new ListOfCards();
            for(int i=1; i<=4; i++) {
                tempCards.addCard(handCards.getCardAt(handCards.getSize() - i));
            }
            if(checkFourOfAKind(tempCards)) return true;
        }

        //win with dragon sequence
        int rank = 3, duplicateCardNum = 0;
        for(int i=0; i<handCards.getSize(); i++) {
            if(rank == 14) return true;
            if(duplicateCardNum > 1) break;
            Card card = handCards.getCardAt(i);
            if(card.getRank() == rank) rank++;
            else if(card.equals(handCards.getCardAt(i-1))) duplicateCardNum++;
            else break;
        }
        return false;
    }
}
