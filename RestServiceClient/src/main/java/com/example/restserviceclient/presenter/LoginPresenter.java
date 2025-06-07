package com.example.restserviceclient.presenter;

import com.example.restserviceclient.model.entity.Users;
import com.example.restserviceclient.model.repository.UserRepository;
import com.example.restserviceclient.view.SceneLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.util.List;

public class LoginPresenter {
    private final UserRepository repository;

    private TextField username;
    private TextField password;

    public LoginPresenter(UserRepository repository) {
        this.repository = repository;
    }

    public void init() {
        username = (TextField) lookup("#username");
        password = (TextField) lookup("#password");
    }

    public void handleLogin() {
        if (username == null || password == null) {
            showError("Componenta UI nu este încărcată corect.");
            return;
        }

        String user = username.getText();
        String pass = password.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            showError("Completează toate câmpurile!");
            return;
        }

        List<Users> users = repository.getAll();
        for (Users u : users) {
            if (u.getName().equals(user) && u.getPassword().equals(pass)) {
                Scene scene = username.getScene();
                switch (u.getRole().toString()) {
                    case "EMPLOYEE" -> SceneLoader.loadScene("/com/example/restserviceclient/EmployeeView.fxml", scene);
                    case "MANAGER"  -> SceneLoader.loadScene("/com/example/restserviceclient/ManagerView.fxml", scene);
                    case "ADMIN"    -> SceneLoader.loadScene("/com/example/restserviceclient/AdminView.fxml", scene);
                }
                return;
            }
        }

        showError("Nume sau parolă greșită.");
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Eroare la login");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private Object lookup(String fxId) {
        return Window.getWindows().stream()
                .filter(Window::isShowing)
                .findFirst()
                .map(Window::getScene)
                .map(scene -> scene.lookup(fxId))
                .orElse(null);
    }
}
