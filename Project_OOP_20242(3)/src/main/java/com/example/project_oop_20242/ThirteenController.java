
package com.example.project_oop_20242;

import card.Card;
import card.ListOfCards;
import control.ThirteenRound;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import player.Actor;
import player.ThirteenPlayer;
import player.ThirteenBot;
import rule.ThirteenSRule;
import rule.ThirteenNRule;
import rule.GameRule;
import java.net.URL;
import java.util.*;

public class ThirteenController implements Initializable {
    private final List<ImageView> deck = new ArrayList<>();
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
    @FXML
    private StackPane centerPane; // For played cards animation
    @FXML
    private Button playButton;
    @FXML
    private Button passButton;

    private Image cardBack;
    private int playerCount;
    private final Map<ImageView, double[]> cardBasePosition = new HashMap<>();
    private final Map<Actor, List<ImageView>> playerCardViews = new HashMap<>();
    private final List<ImageView> selectedCards = new ArrayList<>();
    private final Map<ImageView, Boolean> cardSelectionState = new HashMap<>();
    private ArrayList<Actor> players = new ArrayList<>();
    private ArrayList<Actor> playersWinGame = new ArrayList<>();
    private GameRule rule;
    private final Map<Actor, Integer> playerOriginalIndex = new HashMap<>();
    private ThirteenRound currentRound;
    private Actor playerWinLastRound;
    private boolean playWithBots ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupButtonActions();
    }

    private void setupButtonActions() {
        // Buttons will be automatically linked via FXML onAction attributes
    }

    public void setUpGame(int playerCount, String gameType, boolean playWithBots) {
        clearDeckPane();
        this.playerCount = playerCount;
        this.playWithBots = playWithBots; // ← Cập nhật biến trạng thái

        if (gameType.equals("ThirteenS")) {
            rule = new ThirteenSRule();
        } else {
            rule = new ThirteenNRule();
        }

        initializePlayers(playerCount, gameType);
        playerWinLastRound = players.get(0);
        cardBack = new Image(getClass().getResource("/com/example/project_oop_20242/cards/BACK.png").toExternalForm());
        createPlayerCardViews();
        animateDealFromLogic();
    }

    private void initializePlayers(int playerCount, String gameType) {
        players.clear();
        playersWinGame.clear();
        selectedCards.clear();
        cardSelectionState.clear();
        playerOriginalIndex.clear();

        ListOfCards deck = new ListOfCards();
        deck.initializeDeck(gameType);

        for (int i = 0; i < playerCount; i++) {
            Actor player;
            if (playWithBots && i != 0) {
                player = new ThirteenBot(gameType); // Sử dụng ThirteenBot
            } else {
                player = new ThirteenPlayer(gameType); // Người chơi thật
            }
            player.position = i;
            players.add(player);
            playerOriginalIndex.put(player, i);
        }

        for (Actor player : players) {
            player.setCardsOnHand(deck.drawCard(13));
        }
    }


    private void createPlayerCardViews() {
        playerCardViews.clear();
        for (int i = 0; i < playerCount; i++) {
            Actor player = players.get(i);
            ListOfCards hand = player.getCardsOnHand();
            List<ImageView> views = new ArrayList<>();
            for (Card c : hand.getCardList()) {
                ImageView cardView = new ImageView(cardBack);
                cardView.setFitWidth(100);
                cardView.setFitHeight(150);
                cardView.setUserData(c);
                cardSelectionState.put(cardView, false);
                views.add(cardView);
            }
            playerCardViews.put(player, views);
        }
    }

    private void startNewRound() {
        currentRound = new ThirteenRound(players, playersWinGame, playerWinLastRound);
        clearSelection();
        processCurrentTurn();
    }

    private void processCurrentTurn() {
        if (!currentRound.isRoundActive()) {
            endRound();
            return;
        }

        Actor currentPlayer = currentRound.getCurrentPlayer();
        if (currentPlayer == null) {
            endRound();
            return;
        }

        System.out.println("Processing turn for player " + currentPlayer.getId() +
                " (Bot: " + currentRound.isCurrentPlayerBot() + ")");

        highlightCurrentPlayer();

        if (currentRound.isCurrentPlayerBot()) {
            enablePlayerControls(false);

            System.out.println("Bot has " + currentPlayer.getCardsOnHand().getSize() + " cards");

            // Tăng thời gian delay để người chơi có thể theo dõi
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                System.out.println("Bot is making move...");

                if (currentRound.processBotTurn()) {
                    System.out.println("Bot played successfully");
                    animateBotPlayedCards();
                    // Delay thêm để xem animation
                    Timeline nextTurnDelay = new Timeline(new KeyFrame(Duration.seconds(1.5), ev -> {
                        nextTurn();
                    }));
                    nextTurnDelay.play();
                } else {
                    System.out.println("Bot passed");
                    Timeline delay = new Timeline(new KeyFrame(Duration.seconds(1), ev -> processCurrentTurn()));
                    delay.play();
                }


            }));
            timeline.play();
        } else {
            enablePlayerControls(true);
            System.out.println("Human player's turn");
        }
    }

    // Thêm method debug để kiểm tra trạng thái bot
    private void debugBotState(Actor bot) {
        System.out.println("=== BOT DEBUG INFO ===");
        System.out.println("Bot ID: " + bot.getId());
        System.out.println("Cards on hand: " + bot.getCardsOnHand().getSize());
        System.out.println("Hand content: " + bot.toStringCardsOnHand());
        System.out.println("Selected cards: " + bot.getCardsSelected().getSize());
        System.out.println("Selected content: " + bot.toStringCardsSelected());

        List<ImageView> botCardViews = playerCardViews.get(bot);
        if (botCardViews != null) {
            System.out.println("UI Card views: " + botCardViews.size());
        }
        System.out.println("=====================");
    }



    private void nextTurn() {
        currentRound.nextTurn();
        clearSelection();
        if (currentRound.isRoundActive() && currentRound.getCurrentPlayer() != null) {
            processCurrentTurn();
        } else {
            endRound();
        }
    }

    private void endRound() {
        playerWinLastRound = currentRound.getPlayerLastInRound();
        if (players.size() > 1) {
            if (centerPane != null) {
                Platform.runLater(() -> centerPane.getChildren().clear());
            }
            startNewRound();
        } else {
            endGame();
        }
    }

    private void endGame() {
        playersWinGame.add(players.get(0));
        enablePlayerControls(false);
    }

    @FXML
    private void handlePlay() {
        if (currentRound != null && currentRound.isCurrentPlayerHuman()) {
            updatePlayerSelection();
            Actor playingPlayer = currentRound.getCurrentPlayer();
            if (currentRound.play()) {
                animatePlayedCards();
                Timeline delay = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                    if (playersWinGame.contains(playingPlayer)) {
                        removeAllCardsFromWinner(playingPlayer);
                    }
                    if (!currentRound.isRoundActive()) {
                        endRound();
                    } else {
                        nextTurn();
                    }
                }));
                delay.play();
            }
        }
    }

    private void removeAllCardsFromWinner(Actor winner) {
        List<ImageView> winnerCards = playerCardViews.get(winner);
        StackPane winnerPane = getPlayerPane(winner);
        if (winnerCards != null && winnerPane != null) {
            Platform.runLater(() -> {
                for (ImageView card : winnerCards) {
                    winnerPane.getChildren().remove(card);
                    cardSelectionState.remove(card);
                }
                winnerCards.clear();
            });
        }
    }

    @FXML
    private void handlePass() {
        if (currentRound != null && currentRound.isCurrentPlayerHuman()) {
            Actor passingPlayer = currentRound.getCurrentPlayer();
            resetSelectedCardsToOriginalPosition();
            currentRound.pass();
            clearSelection();
            Timeline delay = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
                if (!currentRound.isRoundActive()) {
                    endRound();
                } else {
                    processCurrentTurn();
                }
            }));
            delay.play();
        }
    }

    private void resetSelectedCardsToOriginalPosition() {
        Actor currentPlayer = currentRound.getCurrentPlayer();
        StackPane currentPlayerPane = getPlayerPane(currentPlayer);
        List<ImageView> playerCards = playerCardViews.get(currentPlayer);
        for (ImageView selectedCard : selectedCards) {
            int cardIndex = playerCards.indexOf(selectedCard);
            if (cardIndex != -1) {
                double[] originalPosition = calculateCardPosition(currentPlayerPane, cardIndex);
                moveCardForSelection(selectedCard, currentPlayerPane, originalPosition[0], originalPosition[1], false);
                cardSelectionState.put(selectedCard, false);
            }
        }
    }

    private void updatePlayerSelection() {
        Actor currentPlayer = currentRound.getCurrentPlayer();
        List<ImageView> playerCards = playerCardViews.get(currentPlayer);
        currentPlayer.clearSelected();
        for (int i = 0; i < playerCards.size(); i++) {
            ImageView cardView = playerCards.get(i);
            if (selectedCards.contains(cardView)) {
                currentRound.select(i);
            }
        }
    }

    private void clearSelection() {
        selectedCards.clear();
        for (Actor player : players) {
            List<ImageView> cards = playerCardViews.get(player);
            if (cards != null) {
                for (ImageView card : cards) {
                    cardSelectionState.put(card, false);
                }
            }
        }
    }

    private void animatePlayedCards() {
        Actor currentPlayer = currentRound.getCurrentPlayer();
        List<ImageView> playerCards = playerCardViews.get(currentPlayer);
        List<ImageView> cardsToAnimate = new ArrayList<>();
        for (ImageView card : selectedCards) {
            cardsToAnimate.add(card);
        }
        for (int i = 0; i < cardsToAnimate.size(); i++) {
            ImageView card = cardsToAnimate.get(i);
            animateCardToCenter(card, i);
        }
        Timeline removeCards = new Timeline(new KeyFrame(Duration.seconds(0.8), e -> {
            removePlayedCardsFromHand(cardsToAnimate, currentPlayer);
        }));
        removeCards.play();
    }

    private void animateCardToCenter(ImageView card, int cardIndex) {
        if (centerPane == null) return;
        Card cardData = (Card) card.getUserData();
        Image cardImage = new Image(getClass().getResource(cardData.getImagePath()).toExternalForm());
        ImageView animatedCard = new ImageView(cardImage);
        animatedCard.setFitWidth(80);
        animatedCard.setFitHeight(120);
        double offsetX = cardIndex * 20;
        double offsetY = cardIndex * 5;
        animatedCard.setTranslateX(offsetX);
        animatedCard.setTranslateY(offsetY);
        Platform.runLater(() -> {
            centerPane.getChildren().add(animatedCard);
            ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.3), animatedCard);
            scaleIn.setFromX(0.1);
            scaleIn.setFromY(0.1);
            scaleIn.setToX(1.0);
            scaleIn.setToY(1.0);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), animatedCard);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            ParallelTransition playAnimation = new ParallelTransition(scaleIn, fadeIn);
            playAnimation.play();
        });
    }

    private void removePlayedCardsFromHand(List<ImageView> cardsToRemove, Actor player) {
        List<ImageView> playerCards = playerCardViews.get(player);
        StackPane playerPane = getPlayerPane(player);
        Platform.runLater(() -> {
            for (ImageView card : cardsToRemove) {
                playerPane.getChildren().remove(card);
                playerCards.remove(card);
                cardSelectionState.remove(card);
            }
            rearrangePlayerCards(player);
        });
    }

    private void rearrangePlayerCards(Actor player) {
        List<ImageView> playerCards = playerCardViews.get(player);
        StackPane playerPane = getPlayerPane(player);
        for (int i = 0; i < playerCards.size(); i++) {
            ImageView card = playerCards.get(i);
            double[] newPosition = calculateCardPosition(playerPane, i);
            TranslateTransition moveCard = new TranslateTransition(Duration.seconds(0.5), card);
            moveCard.setToX(newPosition[0]);
            moveCard.setToY(newPosition[1]);
            moveCard.play();
            card.setOnMouseClicked(null);
            enableClickToggle(playerPane, card, newPosition[0], newPosition[1]);
        }
    }

    private void animateBotPlayedCards() {
        if (centerPane != null && currentRound != null) {
            Actor currentPlayer = currentRound.getCurrentPlayer();
            if (currentPlayer instanceof ThirteenBot) {
                // Lấy các bài đã selected của bot
                ListOfCards selectedCards = currentPlayer.getCardsSelected();

                System.out.println("Bot played " + selectedCards.getSize() + " cards: " + selectedCards.toString());

                Platform.runLater(() -> {
                    for (int i = 0; i < selectedCards.getSize(); i++) {
                        Card card = selectedCards.getCardAt(i);
                        // Tạo ImageView mới từ card data
                        Image cardImage = new Image(getClass().getResource(card.getImagePath()).toExternalForm());
                        ImageView animatedCard = new ImageView(cardImage);
                        animatedCard.setFitWidth(80);
                        animatedCard.setFitHeight(120);

                        // Tính toán vị trí cho animation
                        double offsetX = i * 20;
                        double offsetY = i * 5;
                        animatedCard.setTranslateX(offsetX);
                        animatedCard.setTranslateY(offsetY);

                        // Thêm vào center pane
                        centerPane.getChildren().add(animatedCard);

                        // Animation hiệu ứng
                        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.3), animatedCard);
                        scaleIn.setFromX(0.1);
                        scaleIn.setFromY(0.1);
                        scaleIn.setToX(1.0);
                        scaleIn.setToY(1.0);

                        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), animatedCard);
                        fadeIn.setFromValue(0.0);
                        fadeIn.setToValue(1.0);

                        ParallelTransition playAnimation = new ParallelTransition(scaleIn, fadeIn);
                        playAnimation.play();
                    }
                });

                // Remove cards từ bot's hand sau animation
                Timeline removeCards = new Timeline(new KeyFrame(Duration.seconds(0.8), e -> {
                    removeBotPlayedCards(currentPlayer);
                }));
                removeCards.play();
            }
        }
    }
    private void removeBotPlayedCards(Actor bot) {
        List<ImageView> botCards = playerCardViews.get(bot);
        StackPane botPane = getPlayerPane(bot);
        ListOfCards selectedCards = bot.getCardsSelected();

        if (botCards != null && botPane != null && selectedCards.getSize() > 0) {
            System.out.println("Removing " + selectedCards.getSize() + " cards from bot's hand");

            Platform.runLater(() -> {
                // Tìm và remove các card view tương ứng
                List<ImageView> cardsToRemove = new ArrayList<>();

                for (int i = 0; i < selectedCards.getSize(); i++) {
                    Card selectedCard = selectedCards.getCardAt(i);

                    // Tìm card view tương ứng trong tay bot
                    for (ImageView cardView : botCards) {
                        Card cardData = (Card) cardView.getUserData();
                        if (cardData != null &&
                                cardData.getRank() == selectedCard.getRank() &&
                                cardData.getSuit() == selectedCard.getSuit() &&
                                !cardsToRemove.contains(cardView)) { // Tránh remove trùng
                            cardsToRemove.add(cardView);
                            break;
                        }
                    }
                }

                System.out.println("Found " + cardsToRemove.size() + " card views to remove");

                // Remove cards từ UI và data structures
                for (ImageView cardView : cardsToRemove) {
                    botPane.getChildren().remove(cardView);
                    botCards.remove(cardView);
                    cardSelectionState.remove(cardView);
                }

                // Rearrange remaining cards
                if (!botCards.isEmpty()) {
                    rearrangePlayerCards(bot);
                }

                System.out.println("Bot now has " + botCards.size() + " cards remaining");
            });
        }
    }
    private ImageView createCardImageView(Card card) {
        Image cardImage = new Image(getClass().getResource(card.getImagePath()).toExternalForm());
        ImageView cardView = new ImageView(cardImage);
        cardView.setFitWidth(80);
        cardView.setFitHeight(120);
        cardView.setUserData(card);
        return cardView;
    }

    private void highlightCurrentPlayer() {
        Actor currentPlayer = currentRound != null ? currentRound.getCurrentPlayer() : null;
        for (Map.Entry<Actor, Integer> entry : playerOriginalIndex.entrySet()) {
            Actor player = entry.getKey();
            Integer originalIndex = entry.getValue();
            List<ImageView> cards = playerCardViews.get(player);
            if (cards == null) continue;
            for (ImageView cardView : cards) {
                Card logicCard = (Card) cardView.getUserData();
                if (player.equals(currentPlayer)) {
                    cardView.setImage(new Image(getClass().getResource(logicCard.getImagePath()).toExternalForm()));
                } else {
                    cardView.setImage(cardBack);
                }
            }
        }
    }

    private void enablePlayerControls(boolean enable) {
        Platform.runLater(() -> {
            if (playButton != null) playButton.setDisable(!enable);
            if (passButton != null) passButton.setDisable(!enable);
        });
    }

    private void animateDealFromLogic() {
        Timeline timeline = new Timeline();
        int cardsPerPlayer = 13;
        int totalRounds = cardsPerPlayer * playerCount;
        for (int i = 0; i < totalRounds; i++) {
            int round = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1 * round), e -> {
                int playerIndex = round % playerCount;
                Actor player = players.get(playerIndex);
                StackPane targetPane = getPlayerPaneByIndex(player.getPosition());
                List<ImageView> cards = playerCardViews.get(player);
                ImageView card = cards.get(round / playerCount);
                dealCardToPlayer(card, targetPane, playerIndex, round / playerCount);
            }));
        }
        timeline.setOnFinished(e -> {
            clearDeckPane();
            startNewRound();
        });
        timeline.play();
    }

    private StackPane getPlayerPane(Actor player) {
        Integer originalIndex = playerOriginalIndex.get(player);
        if (originalIndex == null) {
            throw new IllegalStateException("Player not found in original index map: " + player);
        }
        return switch (originalIndex) {
            case 0 -> southPane;
            case 1 -> westPane;
            case 2 -> northPane;
            case 3 -> eastPane;
            default -> throw new IllegalStateException("Invalid player index: " + originalIndex);
        };
    }

    private void dealCardToPlayer(ImageView card, StackPane targetPane, int playerIndex, int cardCount) {
        card.setTranslateX(0);
        card.setTranslateY(0);
        if (!deckPane.getChildren().contains(card)) {
            deckPane.getChildren().add(card);
        }
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.2), card);
        double[] offsets = getPlayerOffset(playerIndex);
        transition.setByX(offsets[0]);
        transition.setByY(offsets[1]);
        transition.setOnFinished(ev -> {
            deckPane.getChildren().remove(card);
            double[] position = calculateCardPosition(targetPane, cardCount);
            if (playerIndex == 1) card.setRotate(90);
            else if (playerIndex == 3) card.setRotate(-90);
            card.setTranslateX(position[0]);
            card.setTranslateY(position[1]);
            targetPane.getChildren().add(card);
            enableClickToggle(targetPane, card, position[0], position[1]);
        });
        transition.play();
    }

    private double[] getPlayerOffset(int playerIndex) {
        return switch (playerIndex) {
            case 0 -> new double[]{0, 200};
            case 1 -> new double[]{-200, 0};
            case 2 -> new double[]{0, -200};
            case 3 -> new double[]{200, 0};
            default -> new double[]{0, 0};
        };
    }

    private double[] calculateCardPosition(StackPane targetPane, int cardCount) {
        double fanOffsetX = 0, fanOffsetY = 0;
        if (targetPane == southPane || targetPane == northPane) {
            fanOffsetX = -180 + (cardCount * 30);
        } else if (targetPane == eastPane) {
            fanOffsetY = 180 - (cardCount * 30);
        } else if (targetPane == westPane) {
            fanOffsetY = -180 + (cardCount * 30);
        }
        return new double[]{fanOffsetX, fanOffsetY};
    }

    private void enableClickToggle(StackPane targetPane, ImageView cardView, double baseX, double baseY) {
        cardView.setOnMouseClicked(e -> {
            Actor currentPlayer = currentRound.getCurrentPlayer();
            StackPane currentPlayerPane = getPlayerPane(currentPlayer);
            if (!targetPane.equals(currentPlayerPane)) {
                return;
            }
            toggleCardSelection(cardView, targetPane, baseX, baseY);
        });
    }

    private void toggleCardSelection(ImageView card, StackPane targetPane, double baseX, double baseY) {
        Boolean isSelected = cardSelectionState.get(card);
        if (isSelected == null) isSelected = false;
        if (!isSelected) {
            if (!selectedCards.contains(card)) {
                selectedCards.add(card);
            }
            cardSelectionState.put(card, true);
            moveCardForSelection(card, targetPane, baseX, baseY, true);
        } else {
            selectedCards.remove(card);
            cardSelectionState.put(card, false);
            moveCardForSelection(card, targetPane, baseX, baseY, false);
        }
    }

    private StackPane getPlayerPaneByIndex(int playerIndex) {
        return switch (playerIndex) {
            case 0 -> southPane;
            case 1 -> westPane;
            case 2 -> northPane;
            case 3 -> eastPane;
            default -> throw new IllegalStateException("Invalid player index: " + playerIndex);
        };
    }

    private void moveCardForSelection(ImageView card, StackPane targetPane, double baseX, double baseY, boolean select) {
        double offset = select ? 30 : 0;
        TranslateTransition moveTransition = new TranslateTransition(Duration.seconds(0.2), card);
        int playerIndex = -1;
        for (Map.Entry<Actor, Integer> entry : playerOriginalIndex.entrySet()) {
            StackPane playerPane = getPlayerPaneByIndex(entry.getValue());
            if (playerPane.equals(targetPane)) {
                playerIndex = entry.getValue();
                break;
            }
        }
        if (playerIndex == 0) {
            moveTransition.setToX(baseX);
            moveTransition.setToY(baseY - offset);
        } else if (playerIndex == 2) {
            moveTransition.setToX(baseX);
            moveTransition.setToY(baseY + offset);
        } else if (playerIndex == 1) {
            moveTransition.setToX(baseX + offset);
            moveTransition.setToY(baseY);
        } else if (playerIndex == 3) {
            moveTransition.setToX(baseX - offset);
            moveTransition.setToY(baseY);
        } else {
            moveTransition.setToX(baseX);
            moveTransition.setToY(baseY);
        }
        moveTransition.play();
    }

//    public void setPlayerCount(int playerCount) {
//        if (playerCount < 2 || playerCount > 4) {
//            throw new IllegalArgumentException("Player count must be between 2 and 4.");
//        }
//        this.playerCount = playerCount;
//    }

    private void clearDeckPane() {
        deckPane.getChildren().clear();
        deck.clear();
    }
}
