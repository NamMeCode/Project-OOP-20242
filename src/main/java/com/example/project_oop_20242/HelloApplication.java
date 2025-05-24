package com.example.project_oop_20242;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Card games");

        Image iconImage = new Image(getClass().getResourceAsStream("/com/example/project_oop_20242/cards/img.png"));
        stage.getIcons().add(iconImage);

        stage.setScene(scene);

        stage.setFullScreen(false);      // Tắt fullscreen nếu đang bật
        stage.setMaximized(false);       // Không cho cửa sổ tự động full màn hình
        stage.setResizable(true);        // Cho resize lại nếu muốn

        stage.show();
    }
}
