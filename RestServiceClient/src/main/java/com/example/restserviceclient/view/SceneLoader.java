package com.example.restserviceclient.view;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {
    public static void loadScene(String fxmlPath, Scene currentScene) {
        try {
            Parent root = FXMLLoader.load(SceneLoader.class.getResource(fxmlPath));
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace(); // ADĂUGĂ ASTA
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Eroare la login");
            alert.setContentText("Componenta UI nu este încărcată corect: " + fxmlPath);
            alert.showAndWait();
        }
    }
}
