package com.example.project_oop_20242;

import card.Card;
import card.ListOfCards;
import control.ThirteenControl;
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
import player.Actor;
import rule.ThirteenSRule;
import rule.ThirteenNRule;
import rule.GameRule;

import java.net.URL;
import java.util.*;

public class ThirteenDeckController implements Initializable {

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

    private int playerCount ;
    private final Map<Actor, List<ImageView>> playerCardViews = new HashMap<>();
    private List<Actor> players;
    private int currentPlayerIndex = 0;
    private GameRule rule;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML

    public void setUpGame(int playerCount, String gameType) {
        clearDeckPane();
        this.playerCount = playerCount;
        if (gameType.equals("ThirteenS")) {
            rule = new ThirteenSRule();
        } else {
            rule = new ThirteenNRule();
        }


        ThirteenControl gameLogic = new ThirteenControl(playerCount, 0, gameType);
        this.players = gameLogic.getPlayersInGame();

        cardBack = new Image(getClass().getResource("/com/example/project_oop_20242/cards/BACK.png").toExternalForm());

        for (int i = 0; i < playerCount; i++) {
            Actor player = players.get(i);
            StackPane targetPane = switch (i) {
                case 0 -> southPane;
                case 1 -> westPane;
                case 2 -> northPane;
                case 3 -> eastPane;
                default -> throw new IllegalStateException("Invalid player index");
            };

            ListOfCards hand = player.getCardsOnHand();
            System.out.println(hand.toString());
            List<ImageView> views = new ArrayList<>();

            for (Card c : hand.getCardList()) {
                String imgPath = "/com/example/project_oop_20242/cards/" + c.getRankString() + "-" + c.getSuitString() + ".png";
                Image frontImage = new Image(getClass().getResource(imgPath).toExternalForm());
                ImageView cardView = new ImageView(cardBack);
                cardView.setFitWidth(100);
                cardView.setFitHeight(150);
                cardView.setUserData(frontImage); // Gắn mặt trước vào UserData
                views.add(cardView);
            }

            playerCardViews.put(player, views);
        }
        animateDealFromLogic();
    }

    private void highlightCurrentPlayer() {
        for (int i = 0; i < players.size(); i++) {
            Actor player = players.get(i);
            List<ImageView> cards = playerCardViews.get(player);

            for (ImageView card : cards) {
                if (i == currentPlayerIndex) {
                    card.setImage((Image) card.getUserData());
                } else {
                    card.setImage(cardBack);
                }
            }
        }
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % playerCount;
        highlightCurrentPlayer();
    }



    private void animateDealFromLogic() {
        Timeline timeline = new Timeline();
        int cardsPerPlayer = 13;
        int totalRounds = cardsPerPlayer * playerCount;

        for (int i = 0; i < totalRounds; i++) {
            int round = i;

            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.2 * round), e -> {
                int playerIndex = round % playerCount;
                Actor player = players.get(playerIndex);

                StackPane targetPane = switch (playerIndex) {
                    case 0 -> southPane;
                    case 1 -> westPane;
                    case 2 -> northPane;
                    case 3 -> eastPane;
                    default -> throw new IllegalStateException("Invalid index");
                };

                List<ImageView> cards = playerCardViews.get(player);
                ImageView card = cards.get(round / playerCount);

                // Tạo hiệu ứng giống dealOneCardTo
                card.setTranslateX(0);
                card.setTranslateY(0);
                if (!deckPane.getChildren().contains(card)) {
                    deckPane.getChildren().add(card);
                }
//                card.setImage(playerIndex == 0 ? (Image) card.getUserData() : cardBack); // chỉ lật mặt south

                TranslateTransition transition = new TranslateTransition(Duration.seconds(0.2), card);
                double xOffset = switch (playerIndex) {
                    case 0 -> 0;
                    case 1 -> -200;
                    case 2 -> 0;
                    case 3 -> 200;
                    default -> 0;
                };
                double yOffset = switch (playerIndex) {
                    case 0 -> 200;
                    case 1 -> 0;
                    case 2 -> -200;
                    case 3 -> 0;
                    default -> 0;
                };

                transition.setByX(xOffset);
                transition.setByY(yOffset);

                transition.setOnFinished(ev -> {
                    deckPane.getChildren().remove(card);
                    int count = targetPane.getChildren().size();
                    double fanOffsetX = 0, fanOffsetY = 0;

                    if (targetPane == southPane || targetPane == northPane) {
                        fanOffsetX = -180 + (count * 30);
                    } else if (targetPane == eastPane) {
                        card.setRotate(-90);
                        fanOffsetY = 180 - (count * 30);
                    } else if (targetPane == westPane) {
                        card.setRotate(90);
                        fanOffsetY = -180 + (count * 30);
                    }


                    card.setTranslateX(fanOffsetX);
                    card.setTranslateY(fanOffsetY);
                    targetPane.getChildren().add(card);

                    if(playerIndex == 0){
                        enableClickToggle(southPane,card, fanOffsetX, fanOffsetY);
                    }else if (playerIndex == 2){
                        enableClickToggle(northPane,card, fanOffsetX, fanOffsetY);
                    }else if (playerIndex == 1){
                        enableClickToggle(eastPane,card, fanOffsetX, fanOffsetY);
                    }else{
                        enableClickToggle(westPane,card, fanOffsetX, fanOffsetY);
                    }


//
                });

                transition.play();
            }));
        }

        timeline.setOnFinished(e -> {
            highlightCurrentPlayer();
            clearDeckPane();
        });
        timeline.play();
    }

    public void setPlayerCount(int playerCount) {
        if (playerCount < 2 || playerCount > 4) {
            throw new IllegalArgumentException("Player count must be between 2 and 4.");
        }
        this.playerCount = playerCount;
    }



    private void enableClickToggle(StackPane targetPane,ImageView card, double baseX, double baseY) {
        final boolean[] selected = {false};

        if(targetPane.equals(southPane)) {
            card.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (!selected[0]) {
                    card.setTranslateY(baseY - 30);
                } else {
                    card.setTranslateY(baseY);
                }
                selected[0] = !selected[0];
            });
        }else if(targetPane.equals(northPane)) {
            card.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (!selected[0]) {
                    card.setTranslateY(baseY + 30);
                } else {
                    card.setTranslateY(baseY);
                }
                selected[0] = !selected[0];
            });
        }else if(targetPane.equals(westPane)) {
            card.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (!selected[0]) {
                    card.setTranslateX(baseX - 30);
                } else {
                    card.setTranslateX(baseX);
                }
                selected[0] = !selected[0];
            });
        }else if(targetPane.equals(eastPane)) {
            card.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (!selected[0]) {
                    card.setTranslateX(baseX + 30);
                } else {
                    card.setTranslateX(baseX);
                }
                selected[0] = !selected[0];
            });
        }



    }
    private void clearDeckPane() {
        deckPane.getChildren().clear(); // Xóa tất cả các node khỏi deckPane
        deck.clear(); // Nếu bạn cũng duy trì danh sách deck riêng
    }
}
