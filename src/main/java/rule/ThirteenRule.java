package rule;

import card.ListOfCards;

abstract public class ThirteenRule extends GameRule {
    abstract public boolean checkDoubleSequence(ListOfCards cards);

    abstract public boolean checkSequence(ListOfCards cards);

    abstract public String handType(ListOfCards cards);

    abstract public boolean checkWinCondition(ListOfCards cards);

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
                if(checkFourCardsSameRank(playCards) || checkDoubleSequence(playCards)) return true;
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
}