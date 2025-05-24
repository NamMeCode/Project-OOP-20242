package com.example.project_oop_20242;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseNumberOfPlayersController implements Initializable {
    private int gameID;
    @FXML
    private Button _2PlayerButton;
    @FXML
    private Button _3PlayerButton;
    @FXML
    private Button _4PlayerButton;
    @FXML
    private Button backToChooseGameplayButton;

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void enterButtonEffect(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.play();

        // Thêm hiệu ứng sáng lên (DropShadow)
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.YELLOW);
        dropShadow.setRadius(10);
        button.setEffect(dropShadow);
    }

    public void exitButtonEffect(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();

        button.setEffect(null);
    }

    @FXML
    public void _2PlayersEnter() {

        enterButtonEffect(_2PlayerButton);
    }

    public void _2PlayersExit() {

        exitButtonEffect(_2PlayerButton);
    }

    public void _3PlayersEnter() {

        enterButtonEffect(_3PlayerButton);
    }

    public void _3PlayersExit() {

        exitButtonEffect(_3PlayerButton);
    }

    public void _4PlayersEnter() {

        enterButtonEffect(_4PlayerButton);
    }

    public void _4PlayersExit() {

        exitButtonEffect(_4PlayerButton);
    }

    public void backToChooseGameplayEnter() {

        enterButtonEffect(backToChooseGameplayButton);
    }

    public void backToChooseGameplayExit() {

        exitButtonEffect(backToChooseGameplayButton);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //Platform.runLater(): Đảm bảo rằng mã thiết lập sự kiện phím sẽ chỉ chạy khi FXML đã được tải xong và giao diện người dùng đã được hiển thị.
        Platform.runLater(() -> {
            Scene scene = _2PlayerButton.getScene();
            if (scene != null) {
                scene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        closeStage();
                    }
                });
            }
        });
    }

    private void addEscapeHandler(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                closeStage();
            }
        });
    }

    private void closeStage() {
        Stage stage = (Stage) _2PlayerButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void backToChooseGameplay() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChooseGameplay.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) _2PlayerButton.getScene().getWindow();

            stage.setMaximized(true);

            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void start2PlayerGame() {
        startGame(2);
    }

    @FXML
    public void start3PlayerGame() {
        startGame(3);
    }

    @FXML
    public void start4PlayerGame() {
        startGame(4);
    }


    private void startGame(int playerCount) {
        try {
            String fxmlPath;
            String gameType;

            switch (gameID) {
                case 1:
                    fxmlPath = "Deck.fxml";
                    gameType = "ThirteenN";
                    break;
                case 2:
                    fxmlPath = "Deck.fxml";
                    gameType = "ThirteenS";
                    break;
//                case 3:
//                    fxmlPath = "PokerTable.fxml";
//                    gameType = "Poker";
//                    break;
                default:
                    throw new IllegalArgumentException("Invalid gameID: " + gameID);
            }

            // ✅ Sửa ở đây
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath)); // <-- GÁN LOCATION
            Scene scene = new Scene(loader.load());

            ThirteenDeckController controller = loader.getController();
            controller.setUpGame(playerCount, gameType);

            Stage stage = (Stage) _2PlayerButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setResizable(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
