package rule;

import card.ListOfCards;
import player.PokerPlayer;

public class PokerRule extends GameRule  {
    public ListOfCards cardsFromStraightFlush(ListOfCards cards) {
        cards.sortSuitRank();
        ListOfCards tempCards = new ListOfCards();
        for(int i = cards.getSize() - 5; i >= 0; i--) {
            for(int j = 0; j < 5; j++) {
                tempCards.addCard(cards.getCardAt(i + j));
            }
            if(GameRule.checkContinuousRank(tempCards) && GameRule.checkFlush(tempCards))
                return tempCards;
            tempCards.clear();
        }
        return null;
    }

    public ListOfCards cardsFromFourOfAKind(ListOfCards cards) {
        cards.sortRankSuit();
        ListOfCards tempCards = new ListOfCards();
        for(int i = cards.getSize() - 4; i >= 0; i--) {
            for(int j = 0; j < 4; j++) {
                tempCards.addCard(cards.getCardAt(i + j));
            }
            if(GameRule.checkFourCardsSameRank(tempCards)) {
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

    public ListOfCards cardsFromFullHouse(ListOfCards cards) {
        cards.sortRankSuit();
        ListOfCards tempCards = new ListOfCards();
        for(int i = cards.getSize() - 3; i >= 0; i--) {
            for(int j = 0; j < 3; j++) {
                tempCards.addCard(cards.getCardAt(i + j));
            }
            if (GameRule.checkThreeCardsSameRank(tempCards)) {
                ListOfCards tempCards2 = new ListOfCards();
                for(int j = cards.getSize() - 2; j >= 0; j--) {
                    for (int k = 0; k < 2; k++) {
                        tempCards2.addCard(cards.getCardAt(j + k));
                    }
                    if(!tempCards.contains(tempCards2.getCardAt(0)) && GameRule.checkTwoCardsSameRank(tempCards2)) {
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

    public ListOfCards cardsFromFlush(ListOfCards cards) {
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

    public ListOfCards cardsFromStraight(ListOfCards cards) {
        ListOfCards listOfCards = new ListOfCards(cards.getCardList());
        listOfCards.sortRankSuit();
        ListOfCards tempCards = new ListOfCards();
        for(int i = listOfCards.getSize() - 5; i >= 0; i--) {
            tempCards.addCard(listOfCards.getCardAt(i));
            for (int j = 1; j < 5; j++) {
                if(listOfCards.getCardAt(i + j).equals(listOfCards.getCardAt(i + j - 1))) {
                    listOfCards.removeCard(listOfCards.getCardAt(i + j - 1));
                    tempCards.clear();
                    break;
                }
                else {
                    tempCards.addCard(listOfCards.getCardAt(i + j));
                }
            }
            if(tempCards.getSize() != 0 && GameRule.checkContinuousRank(tempCards)) return tempCards;
            tempCards.clear();
        }
        return null;
    }

    public ListOfCards cardsFromThreeOfAKind(ListOfCards cards) {
        cards.sortRankSuit();
        ListOfCards tempCards = new ListOfCards();
        for(int i = cards.getSize() - 3; i >= 0; i--) {
            for(int j = 0; j < 3; j++) {
                tempCards.addCard(cards.getCardAt(i + j));
            }
            if(GameRule.checkThreeCardsSameRank(tempCards)) {
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

    public ListOfCards cardsFromTwoPair(ListOfCards cards) {
        cards.sortRankSuit();
        ListOfCards tempCards = new ListOfCards();
        for(int i = cards.getSize() - 2; i >= 0; i--) {
            for(int j = 0; j < 2; j++) {
                tempCards.addCard(cards.getCardAt(i + j));
            }
            if(GameRule.checkTwoCardsSameRank(tempCards)) {
                ListOfCards tempCards2 = new ListOfCards();
                for(int j = i - 2; j >= 0; j--) {
                    for(int k = 0; k < 2; k++) {
                        tempCards2.addCard(cards.getCardAt(j + k));
                    }
                    if(GameRule.checkTwoCardsSameRank(tempCards2)) {
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

    public ListOfCards cardsFromPair(ListOfCards cards) {
        cards.sortRankSuit();
        ListOfCards tempCards = new ListOfCards();
        for(int i = cards.getSize() - 2; i >= 0; i--) {
            for (int j = 0; j < 2; j++) {
                tempCards.addCard(cards.getCardAt(i + j));
            }
            if(GameRule.checkTwoCardsSameRank(tempCards)) {
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

    public ListOfCards cardsFromHighCard(ListOfCards cards) {
        cards.sortRankSuit();
        ListOfCards tempCards = new ListOfCards();
        for (int i = 1; i <= 5; i++) {
            tempCards.addCard(cards.getCardAt(cards.getSize() - i));
        }
        return tempCards;
    }

    public void checkHandRank(PokerPlayer player, ListOfCards cardsOnTable) {
        ListOfCards mergeCards = new ListOfCards(player.getCardsOnHand().getCardList());
        mergeCards.addAll(cardsOnTable);
        ListOfCards bestCards;
        if((bestCards = cardsFromStraightFlush(mergeCards)) != null) {
            player.setHandType("Straight Flush");
            player.setBestCards(bestCards);
        }
        else if((bestCards = cardsFromFourOfAKind(mergeCards)) != null) {
            player.setHandType("Four Of A Kind");
            player.setBestCards(bestCards);
        }
        else if((bestCards = cardsFromFullHouse(mergeCards)) != null) {
            player.setHandType("Full House");
            player.setBestCards(bestCards);
        }
        else if((bestCards = cardsFromFlush(mergeCards)) != null) {
            player.setHandType("Flush");
            player.setBestCards(bestCards);
        }
        else if((bestCards = cardsFromStraight(mergeCards)) != null) {
            player.setHandType("Straight");
            player.setBestCards(bestCards);
        }
        else if((bestCards = cardsFromThreeOfAKind(mergeCards)) != null) {
            player.setHandType("Three Of A Kind");
            player.setBestCards(bestCards);
        }
        else if((bestCards = cardsFromTwoPair(mergeCards)) != null) {
            player.setHandType("Two Pair");
            player.setBestCards(bestCards);
        }
        else if((bestCards = cardsFromPair(mergeCards)) != null) {
            player.setHandType("Pair");
            player.setBestCards(bestCards);
        }
        else {
            player.setHandType("High Card");
            player.setBestCards(cardsFromHighCard(mergeCards));
        }
    }
}