package card;

public class Card {
    private String rank;
    private String suit;
    private boolean FaceUp=false;
    private boolean Selected=false;

    public boolean isSelected() {
        return Selected;
    }
    public void setSelected(boolean selected) {
        Selected = selected;
    }
    public boolean isFaceUp() {
        return FaceUp;
    }
    public void setFaceUp(boolean faceUp) {
        FaceUp = faceUp;
    }

    public Card(String rank, String suit) {
        this.rank=rank;
        this.suit=suit;
    }

    //thirteenN: 2 -> K -> Q -> J -> ...
    //poker: A -> K -> Q -> J -> ... -> 2
    public int getRank() {
        return switch (rank) {
            case "J" -> 11;
            case "Q" -> 12;
            case "K" -> 13;
            case "A" -> 14;
            case "2" -> 15;
            default -> Integer.parseInt(rank);
        };
    }

    public int getSuit() {
        return switch (suit) {
            case "H" -> 4;
            case "D" -> 3;
            case "C" -> 2;
            default -> 1;
        };
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public boolean equals(Card card) {
        return this.getRank() == card.getRank();
    }

    public int compareCard(Card card) {
        int res = this.getRank() - card.getRank();
        return res != 0 ? res : this.getSuit() - card.getSuit();
    }
    public String toString() {
        return rank + " - " + suit;
    }
}
