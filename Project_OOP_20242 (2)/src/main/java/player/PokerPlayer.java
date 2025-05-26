package player;

import card.ListOfCards;

public class PokerPlayer extends ChipActor implements Comparable<PokerPlayer>{
    private int currentBet;
    private String handType;
    private ListOfCards bestCards;
    private boolean isAllIn = false;

    public PokerPlayer(String gameType, int initialStack) {
        super(gameType, initialStack);
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void setAllIn(boolean allIn) {
        isAllIn = allIn;
    }

    public boolean isAllIn() {
        return isAllIn;
    }

    public void setHandType(String handType) {
        this.handType = handType;
    }

    public String getHandType() {
        return handType;
    }

    public ListOfCards getBestCards() {
        return bestCards;
    }

    public void setBestCards(ListOfCards bestCards) {
        this.bestCards = bestCards;
    }

    public int getHandRank() {
        return switch (handType) {
            case "Straight Flush" -> 9;
            case "Four Of A Kind" -> 8;
            case "Full House" -> 7;
            case "Flush" -> 6;
            case "Straight" -> 5;
            case "Three Of A Kind" -> 4;
            case "Two Pair" -> 3;
            case "Pair" -> 2;
            default -> 1;
        };
    }

    public int getMainHandCard() {
        return switch (handType) {
            case "Straight Flush", "Flush", "Straight", "High Card" -> bestCards.getCardAt(4).getRank();
            default -> bestCards.getCardAt(0).getRank();
        };
    }

    public int compareKicker(PokerPlayer other) {
        switch (handType) {
            case "Straight Flush", "Straight" -> {
                return 0;
            }
            case "Four Of A Kind", "Full House" -> {
                return Integer.compare(bestCards.getCardAt(4).getRank(), other.getBestCards().getCardAt(4).getRank());
            }
            case "Three Of A Kind", "Two Pair" -> {
                int result = 0;
                for (int i = 3; i <= 4; i++) {
                    result = Integer.compare(bestCards.getCardAt(i).getRank(), other.getBestCards().getCardAt(i).getRank());
                    if (result != 0) return result;
                }
                return result;
            }
            case "Pair" -> {
                int result = 0;
                for (int i = 2; i <= 4; i++) {
                    result = Integer.compare(bestCards.getCardAt(i).getRank(), other.getBestCards().getCardAt(i).getRank());
                    if (result != 0) return result;
                }
                return result;
            }
            default -> {
                int result = 0;
                for (int i = 3; i >= 0; i--) {
                    result = Integer.compare(bestCards.getCardAt(i).getRank(), other.getBestCards().getCardAt(i).getRank());
                    if (result != 0) return result;
                }
                return result;
            }
        }
    }

    public int compareTo(PokerPlayer other) {
        int result = Integer.compare(this.getHandRank(), other.getHandRank());
        if(result == 0) result = Integer.compare(this.getMainHandCard(), other.getMainHandCard());
        if(result == 0) result = compareKicker(other);
        return result;
    }

    public void fold() {
        System.out.println("Player " + this.getId() + " fold");
        this.currentBet = 0;
    }

    public int call(int maxBet) {
        if (currentBet == maxBet || maxBet - currentBet > chipStack) {
            System.out.println("Sorry you can't call");
            return 0;
        }
        System.out.println("Player " +this.getId() + " call");
        int betAmount = maxBet - this.currentBet;
        this.chipStack -= betAmount;
        this.currentBet = maxBet;
        return betAmount;
    }

    public boolean check(int maxBet) {
        if (currentBet != maxBet) {
            System.out.println("Sorry you can't check");
            return false;
        }
        System.out.println("Player " + this.getId() + " check");
        return true;
    }

    public int bet(int betAmount, int maxBet) {
        if(maxBet != 0 || chipStack < betAmount) {
            System.out.println("Sorry you can't bet");
            return 0;
        }
        System.out.println("Player " + this.getId() + " bet");
        this.currentBet += betAmount;
        this.chipStack -= betAmount;
        return betAmount;
    }

    public int raise(int betAmount, int maxBet, int lowestBet, int lastRaise) {
        if(betAmount < lowestBet || betAmount < lastRaise || maxBet + betAmount - currentBet > chipStack) {
            System.out.println("Sorry you can't raise by this amount");
            return 0;
        }
        System.out.println("Player " + this.getId() + " raises by " + betAmount);
        int temp = currentBet + betAmount;
        this.chipStack -= temp - this.currentBet;
        this.currentBet = temp;
        return betAmount;
    }

    public int allIn() {
        if(chipStack == 0) {
            System.out.println("Sorry you can't all in");
            return 0;
        }
        System.out.println("Player " + this.getId() + " all in");
        isAllIn = true;
        int betAmount = this.chipStack;
        this.currentBet += betAmount;
        this.chipStack = 0;
        return betAmount;
    }
}
