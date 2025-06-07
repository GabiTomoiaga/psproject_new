package com.example.restserviceclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/restserviceclient/LoginView.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Perfume Management");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
