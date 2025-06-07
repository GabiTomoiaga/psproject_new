package com.example.restserviceclient.presenter;

import com.example.restserviceclient.model.entity.Users;
import com.example.restserviceclient.model.entity.UsersType;
import com.example.restserviceclient.model.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.util.List;

public class AdminPresenter {
    private final UserRepository userRepository;

    private TextField username, pass, roleField, storeId, id;
    private TableView<Users> table;
    private ChoiceBox<String> roleFilter;

    public AdminPresenter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void init() {
        username = (TextField) lookup("#username");
        pass = (TextField) lookup("#pass");
        roleField = (TextField) lookup("#role");
        storeId = (TextField) lookup("#storeId");
        id = (TextField) lookup("#id");
        roleFilter = (ChoiceBox<String>) lookup("#role");
        table = (TableView<Users>) lookup("#list");

        configureTable();
        populateFilter();
        loadAllUsers();
    }

    private void configureTable() {
        TableColumn<Users, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Users, String> colUsername = new TableColumn<>("Username");
        colUsername.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Users, String> colPass = new TableColumn<>("Password");
        colPass.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<Users, String> colRole = new TableColumn<>("Role");
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<Users, Integer> colStore = new TableColumn<>("Store ID");
        colStore.setCellValueFactory(new PropertyValueFactory<>("storeId"));

        table.getColumns().setAll(colId, colUsername, colPass, colRole, colStore);
    }

    private void populateFilter() {
        roleFilter.getItems().addAll("EMPLOYEE", "MANAGER", "ADMIN");
    }

    public void handleAdd() {
        try {
            Users user = new Users(
                    Integer.parseInt(id.getText()),
                    username.getText(),
                    pass.getText(),
                    UsersType.valueOf(roleField.getText().toUpperCase()),
                    Integer.parseInt(storeId.getText())
            );
            if (userRepository.addUser(user)) {
                loadAllUsers();
                showInfo("User added");
            } else {
                showError("Failed to add user.");
            }
        } catch (Exception e) {
            showError("Date invalide: " + e.getMessage());
        }
    }

    public void handleUpdate() {
        try {
            Users user = new Users(
                    Integer.parseInt(id.getText()),
                    username.getText(),
                    pass.getText(),
                    UsersType.valueOf(roleField.getText().toUpperCase()),
                    Integer.parseInt(storeId.getText())
            );
            if (userRepository.updateUser(user)) {
                loadAllUsers();
                showInfo("User updated");
            } else {
                showError("Failed to update user.");
            }
        } catch (Exception e) {
            showError("Date invalide: " + e.getMessage());
        }
    }

    public void handleDelete() {
        Users selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selectează un utilizator.");
            return;
        }

        if (userRepository.deleteUser(selected.getId())) {
            loadAllUsers();
            showInfo("User deleted");
        } else {
            showError("Failed to delete user.");
        }
    }

    public void handleApplyFilter() {
        String role = roleFilter.getValue();
        if (role != null && !role.isEmpty()) {
            List<Users> filtered = userRepository.filterByRole(role);
            table.setItems(FXCollections.observableArrayList(filtered));
        }
    }

    public void handleResetFilter() {
        loadAllUsers();
    }

    public void loadAllUsers() {
        List<Users> users = userRepository.getAll();
        table.setItems(FXCollections.observableArrayList(users));
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Eroare");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Succes");
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
