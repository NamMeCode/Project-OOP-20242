package rule;

import card.ListOfCards;
import player.PokerPlayer;

import java.util.List;

public class PokerRule {
    public ListOfCards checkStraightFlush(ListOfCards cards) {
        cards.sortSuitRank();
        ListOfCards tempCards = new ListOfCards();
        for(int i = cards.getSize() - 5; i >= 0; i--) {
            for(int j = 0; j < 5; j++) {
                tempCards.addCard(cards.getCardAt(i + j));
            }
            if(GameRule.checkSequence(tempCards) && GameRule.checkFlush(tempCards))
                return tempCards;
            tempCards.clear();
        }
        return null;
    }

    public ListOfCards checkFourOfAKind(ListOfCards cards) {
        cards.sortRankSuit();
        ListOfCards tempCards = new ListOfCards();
        for(int i = cards.getSize() - 4; i >= 0; i--) {
            for(int j = 0; j < 4; j++) {
                tempCards.addCard(cards.getCardAt(i + j));
            }
            if(GameRule.checkFourOfAKind(tempCards)) {
                for(int j = cards.getSize() - 1; j >= 0; j--) {
                    if(!tempCards.contains(cards.getCardAt(j))) {
                        tempCards.addCard(cards.getCardAt(j));
                        return tempCards;
                    }
                }
            }
            tempCards.clear();
        }
        return null;
    }

    public ListOfCards checkFullHouse(ListOfCards cards) {
        cards.sortRankSuit();
        ListOfCards tempCards = new ListOfCards();
        for(int i = cards.getSize() - 3; i >= 0; i--) {
            for(int j = 0; j < 3; j++) {
                tempCards.addCard(cards.getCardAt(i + j));
            }
            if (GameRule.checkThreeOfAKind(tempCards)) {
                ListOfCards tempCards2 = new ListOfCards();
                for(int j = cards.getSize() - 2; j >= 0; j--) {
                    for (int k = 0; k < 3; k++) {
                        tempCards2.addCard(cards.getCardAt(j + k));
                    }
                    if(tempCards.contains(tempCards2.getCardAt(0)) && GameRule.checkPair(tempCards2)) {
                        tempCards.addAll(tempCards2);
                        return tempCards;
                    }
                    tempCards2.clear();
                }
            }
            tempCards.clear();
        }
        return null;
    }

    public ListOfCards checkFlush(ListOfCards cards) {
        cards.sortSuitRank();
        ListOfCards tempCards = new ListOfCards();
        for(int i = cards.getSize() - 5; i >= 0; i--) {
            for (int j = 0; j < 5; j++) {
                tempCards.addCard(cards.getCardAt(i + j));
            }
            if (GameRule.checkFlush(tempCards)) return tempCards;
            tempCards.clear();
        }
        return null;
    }

    public ListOfCards checkStraight(ListOfCards cards) {
        cards.sortRankSuit();
        ListOfCards tempCards = new ListOfCards();
        for(int i = cards.getSize() - 5; i >= 0; i--) {
            for (int j = 0; j < 5; j++) {
                if(cards.getCardAt(j).equals(cards.getCardAt(j+1))) {
                    cards.removeCard(cards.getCardAt(j));
                    tempCards.clear();
                    break;
                }
                else tempCards.addCard(cards.getCardAt(i + j));
            }
            if(tempCards.getSize() != 0 && GameRule.checkSequence(tempCards)) return tempCards;
        }
        return null;
    }

    public ListOfCards checkThreeOfAKind(ListOfCards cards) {
        cards.sortRankSuit();
        ListOfCards tempCards = new ListOfCards();
        for(int i = cards.getSize() - 3; i >= 0; i--) {
            for(int j = 0; j < 3; j++) {
                tempCards.addCard(cards.getCardAt(i + j));
            }
            if(GameRule.checkThreeOfAKind(tempCards)) {
                for(int j = cards.getSize() - 1; j >= 0; j--) {
                    if(!tempCards.contains(cards.getCardAt(j))) {
                        tempCards.addCard(cards.getCardAt(j));
                        if (tempCards.getSize() == 5) return tempCards;
                    }
                }
            }
            tempCards.clear();
        }
        return null;
    }

    public ListOfCards checkTwoPair(ListOfCards cards) {
        cards.sortRankSuit();
        ListOfCards tempCards = new ListOfCards();
        for(int i = cards.getSize() - 2; i >= 0; i--) {
            for(int j = 0; j < 2; j++) {
                tempCards.addCard(cards.getCardAt(i + j));
            }
            if(GameRule.checkPair(tempCards)) {
                ListOfCards tempCards2 = new ListOfCards();
                for(int j = i - 2; j >= 0; j--) {
                    for(int k = 0; k < 2; k++) {
                        tempCards.addCard(cards.getCardAt(j + k));
                    }
                    if(GameRule.checkPair(tempCards2)) {
                        tempCards.addAll(tempCards2);
                        for(int k = cards.getSize() - 1; k >= 0; k--) {
                            if(!tempCards.contains(cards.getCardAt(k)) &&
                                    !tempCards2.contains(cards.getCardAt(k))) {
                                tempCards.addCard(cards.getCardAt(k));
                                return tempCards;
                            }
                        }
                    }
                    tempCards2.clear();
                }
                return null;
            }
            tempCards.clear();
        }
        return null;
    }

    public ListOfCards checkPair(ListOfCards cards) {
        cards.sortRankSuit();
        ListOfCards tempCards = new ListOfCards();
        for(int i = cards.getSize() - 2; i >= 0; i--) {
            for (int j = 0; j < 2; j++) {
                tempCards.addCard(cards.getCardAt(i + j));
            }
            if(GameRule.checkPair(tempCards)) {
                for(int j = cards.getSize() - 1; j >= 0; j--) {
                    if (!tempCards.contains(cards.getCardAt(j))) {
                        tempCards.addCard(cards.getCardAt(j));
                        if (tempCards.getSize() == 5) return tempCards;
                    }
                }
            }
            tempCards.clear();
        }
        return null;
    }

    public ListOfCards checkHighCard(ListOfCards cards) {
        cards.sortRankSuit();
        ListOfCards tempCards = new ListOfCards();
        tempCards.addCard(cards.getCardAt(cards.getSize()));
        return tempCards;
    }

    public void handType(PokerPlayer player, ListOfCards cardsOnTable) {
        ListOfCards mergeCards = new ListOfCards(player.getCardsOnHand().getCardList());
        mergeCards.addAll(cardsOnTable);
        ListOfCards bestCards;
        if((bestCards = checkStraightFlush(mergeCards)) != null) {
            player.setHandType("Straight Flush");
            player.setBestCards(bestCards);
        }
        else if((bestCards = checkFourOfAKind(mergeCards)) != null) {
            player.setHandType("Four Of A Kind");
            player.setBestCards(bestCards);
        }
        else if((bestCards = checkFullHouse(mergeCards)) != null) {
            player.setHandType("Full House");
            player.setBestCards(bestCards);
        }
        else if((bestCards = checkFlush(mergeCards)) != null) {
            player.setHandType("Flush");
            player.setBestCards(bestCards);
        }
        else if((bestCards = checkStraight(mergeCards)) != null) {
            player.setHandType("Straight Flush");
            player.setBestCards(bestCards);
        }
        else if((bestCards = checkStraight(mergeCards)) != null) {
            player.setHandType("Straight Flush");
            player.setBestCards(bestCards);
        }
        else if((bestCards = checkThreeOfAKind(mergeCards)) != null) {
            player.setHandType("Three Of A Kind");
            player.setBestCards(bestCards);
        }
        else if((bestCards = checkTwoPair(mergeCards)) != null) {
            player.setHandType("Two Pair");
            player.setBestCards(bestCards);
        }
        else if((bestCards = checkPair(mergeCards)) != null) {
            player.setHandType("Pair");
            player.setBestCards(bestCards);
        }
        else {
            player.setHandType("High Card");
            player.setBestCards(checkHighCard(mergeCards));
        }
    }
}
