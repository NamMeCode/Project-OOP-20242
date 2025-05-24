package com.example.project_oop_20242;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class DeckController implements Initializable {

    private final List<ImageView> deck = new ArrayList<>();
    private final List<Image> cardFaces = new ArrayList<>();
    @FXML
    private StackPane deckPane;
    @FXML
    private StackPane northPane;
    @FXML
    private StackPane southPane;
    @FXML
    private StackPane eastPane;
    @FXML
    private StackPane westPane;
    private Image cardBack;

    private int playerCount = 4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCardImages();
        prepareDeck();
    }

    public void setPlayerCount(int playerCount) {
        if (playerCount < 2 || playerCount > 4) {
            throw new IllegalArgumentException("Player count must be between 2 and 4.");
        }
        this.playerCount = playerCount;
    }

    public void startGame() {
        dealCardsInRounds(13);
    }

    private void loadCardImages() {
        cardBack = new Image(getClass().getResource("/com/example/project_oop_20242/cards/BACK.png").toExternalForm());

        String[] suits = {"S", "H", "D", "C"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String rank : ranks) {
            for (String suit : suits) {
                String filename = "/com/example/project_oop_20242/cards/" + rank + "-" + suit + ".png";
                URL imageUrl = getClass().getResource(filename);
                if (imageUrl != null) {
                    cardFaces.add(new Image(imageUrl.toExternalForm()));
                } else {
                    System.err.println("Missing image: " + filename);
                }
            }
        }

        Collections.shuffle(cardFaces);
    }

    private void prepareDeck() {
        for (Image face : cardFaces) {
            ImageView card = new ImageView(cardBack);
            card.setFitWidth(100);
            card.setFitHeight(150);
            card.setUserData(face); // store the front image
            deckPane.getChildren().add(card);
            deck.add(card);
        }
    }

    private void dealCardsInRounds(int rounds) {
        Timeline timeline = new Timeline();

        for (int i = 0; i < rounds; i++) {
            double delay = i * 0.8;

            if (playerCount >= 1)
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(delay), e -> dealOneCardTo(southPane, 0, 200)));
            if (playerCount >= 2)
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(delay + 0.15), e -> dealOneCardTo(westPane, -200, 0)));
            if (playerCount >= 3)
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(delay + 0.30), e -> dealOneCardTo(northPane, 0, -200)));
            if (playerCount == 4)
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(delay + 0.45), e -> dealOneCardTo(eastPane, 200, 0)));
        }
        timeline.setOnFinished(e -> {
            deckPane.getChildren().clear(); // Clear the remaining cards from the center
            deck.clear();                   // Clear from memory
        });
        timeline.play();
    }

    private void dealOneCardTo(StackPane targetPane, double xOffset, double yOffset) {
        if (deck.isEmpty()) return;

        ImageView card = deck.remove(deck.size() - 1);

        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.2), card);
        transition.setByX(xOffset);
        transition.setByY(yOffset);

        transition.setOnFinished(e -> {
            deckPane.getChildren().remove(card);
            card.setTranslateX(0);
            card.setTranslateY(0);

          boolean isPlayer = targetPane == southPane;
           card.setImage(isPlayer ? (Image) card.getUserData() : cardBack);
          //  card.setImage((Image) card.getUserData());

            int count = targetPane.getChildren().size();
            double fanOffsetX = 0, fanOffsetY = 0;

            if (targetPane == southPane || targetPane == northPane) {
                fanOffsetX = -180 + (count * 30);
            } else if (targetPane == eastPane) {
                fanOffsetY = 180 - (count * 30);
            } else if (targetPane == westPane) {
                fanOffsetY = -180 + (count * 30);
            }

            card.setTranslateX(fanOffsetX);
            card.setTranslateY(fanOffsetY);
            targetPane.getChildren().add(card);

            if (isPlayer) {
                enableClickToggle(card, fanOffsetX, fanOffsetY);
            }
        });

        transition.play();
    }

    private void enableClickToggle(ImageView card, double baseX, double baseY) {
        final boolean[] selected = {false};

        card.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (!selected[0]) {
                card.setTranslateY(baseY - 30);
            } else {
                card.setTranslateY(baseY);
            }
            selected[0] = !selected[0];
        });
    }
}
