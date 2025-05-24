package card;

import javafx.scene.image.Image;

import java.net.URL;

public class Card {
    private String rank;
    private String suit;
    private boolean FaceUp=false;
    private boolean Selected=false;
    private String gameType;
    public String getRankString(){
        return rank;
    }
    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
    public String getSuitString(){
        return suit;
    }
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

    public Card(String rank, String suit, String gameType) {
        this.rank=rank;
        this.suit=suit;
        this.gameType = gameType;
    }

    //thirteenN: 2 -> K -> Q -> J -> ...
    //poker: A -> K -> Q -> J -> ... -> 2
    public int getRank() {
        if(gameType.equals("ThirteenS")||gameType.equals("ThirteenN")) {
            return switch (rank) {
                case "J" -> 11;
                case "Q" -> 12;
                case "K" -> 13;
                case "A" -> 14;
                case "2" -> 15;
                default -> Integer.parseInt(rank);
            };
        }
        else if(gameType.equals("Poker")) {
            return switch (rank) {
                case "J" -> 11;
                case "Q" -> 12;
                case "K" -> 13;
                case "A" -> 14;
                default -> Integer.parseInt(rank);
            };
        }
        return 0;
    }

    public int getSuit() {
        return switch (suit) {
            case "H" -> 4;
            case "D" -> 3;
            case "C" -> 2;
            default -> 1;
        };
    }

    public boolean equals(Card card) {
        return this.getRank() == card.getRank();
    }

    public boolean checkSameColour(Card card) {
        return (this.getSuit() >= 3 && card.getSuit() >= 3) || (this.getSuit() <= 2 && card.getSuit() <= 2);
    }

    public int compareCard(Card card) {
        int res = this.getRank() - card.getRank();
        return res != 0 ? res : this.getSuit() - card.getSuit();
    }
    public String toString() {
        return rank + " - " + suit;
    }
    public Image getImage() {
        String imagePath = "/com/example/project_oop_20242/cards/" + this.rank + "-" + this.suit + ".png";
        URL url = getClass().getResource(imagePath);
        if (url != null) {
            return new Image(url.toExternalForm());
        } else {
            System.err.println("Không tìm thấy ảnh cho: " + imagePath);
            return null;  // Hoặc trả về ảnh mặc định
        }
    }
}