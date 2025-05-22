package player;
import card.Card;
import card.ListOfCards;
import rule.*;

public abstract class Actor implements Comparable<Actor>{
    public void setRule(String gameType) {
        switch (gameType) {
            case "ThirteenS":
            {
                this.rule=new ThirteenSRule();
                break;
            }

            case "ThirteenN":
            {
                this.rule=new ThirteenNRule();
                break;
            }
            case "Reddog":
            {
                this.rule=new ReddogRule();
                break;
            }
            case "Poker":
            {
                this.rule=new PokerRule();
            }




        }
    }
    protected ListOfCards cardsOnHand= new ListOfCards();
    protected String actorType; //player hoặc bot
    private String gameType;
    GameRule rule;
    private static int idGenerator=0;
    private final int id;

    private int chipStack;
    private int currentBet;
    private String handType;
    private ListOfCards bestCards;
    private boolean isAllIn = false;
    public String getActorType()
    {
        return actorType;
    }
    public Actor() {
        idGenerator++;
        id = idGenerator;
    }

    //poker
    public Actor(String gameType, int initialStack) {
        this.gameType = gameType;
        this.chipStack = initialStack;
        this.id = ++idGenerator;
    }

    public int getId() {
        return id;
    }

    public void setCardsOnHand(ListOfCards cardsOnHand) {
        this.cardsOnHand = cardsOnHand;
        cardsOnHand.sortRankSuit();
    }

    public ListOfCards getCardsOnHand() {
        return cardsOnHand;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public void decreaseChipStack(int amount) {
        chipStack -= amount;

    }
    public void increaseChipStack(int amount) {
        chipStack += amount;

    }

    public void selectCard(int index) {
        Card cardChosen= cardsOnHand.getCardAt(index);
        if (cardChosen!=null) {
            cardChosen.setSelected(true);
        }
    }

    public void unselectCard(int index) {
        Card cardChosen= cardsOnHand.getCardAt(index);
        if (cardChosen!=null) {
            cardChosen.setSelected(false);
        }
    }

    public void addCard(Card card) {
        cardsOnHand.addCard(card);
    }
    public void removeCard(Card card) {
        cardsOnHand.removeCard(card);
    }
    public boolean playCards(ListOfCards cardsOnTable) {
        ListOfCards cardsPlayed = cardsOnHand.cardsSelected();

        if (rule.checkValidPlay(cardsPlayed,cardsOnTable))
        {
            cardsOnTable.replacedBy(cardsPlayed);
            cardsOnHand.replacedBy(cardsOnHand.cardsNotSelected());
            return true;
        }
        else
        {
            return false;
        }
    }

    public String toStringCardsOnHand() {
        return cardsOnHand.toString();
    }

    public String toStringCardsSelected() {
        return cardsOnHand.cardsSelected().toString();
    }

    public boolean isWin()
    {
        return rule.checkWinCondition(cardsOnHand);
    }

    public void sortCardsOnHand()
    {
        cardsOnHand.sortRankSuit();
    }


    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void setChipStack(int amount) {
        this.chipStack = amount;
    }

    public int getChipStack() {
        return chipStack;
    }
    // poker
    public void setAllIn(boolean allIn) {
        isAllIn = allIn;
    }

    public boolean isAllIn() {
        return isAllIn;
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

    public int compareKicker(Actor other) {
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

    public int compareTo(Actor other) {
        int result = Integer.compare(this.getHandRank(), other.getHandRank());
        if(result == 0) result = Integer.compare(this.getMainHandCard(), other.getMainHandCard());
        if(result == 0) result = compareKicker(other);
        return result;
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

    public void fold() {
        this.currentBet = 0;
    }

    public int call(int maxBet) {
        int betAmount = maxBet - this.currentBet;
        this.chipStack -= betAmount;
        this.currentBet = maxBet;
        return betAmount;
    }

    public void check() {
        return;
    }

    public int bet(int betAmount) {
        this.currentBet += betAmount;
        this.chipStack -= betAmount;
        return betAmount;
    }

    public int raise(int betAmount, int currentBet) {
        int temp = currentBet + betAmount;
        this.chipStack -= temp - this.currentBet;
        this.currentBet = temp;
        return betAmount;
    }

    public int allIn() {
        int betAmount = this.chipStack;
        this.currentBet += betAmount;
        this.chipStack = 0;
        return betAmount;
    }




}
