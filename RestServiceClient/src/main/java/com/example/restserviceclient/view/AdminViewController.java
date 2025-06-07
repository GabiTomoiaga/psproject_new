package com.example.restserviceclient.view;

import com.example.restserviceclient.model.repository.UserRepository;
import com.example.restserviceclient.presenter.AdminPresenter;
import javafx.fxml.FXML;

public class AdminViewController {

    private AdminPresenter presenter;

    @FXML
    public void initialize() {
        presenter = new AdminPresenter(new UserRepository());
        presenter.init(); // Inițializare și populare tabel + filtre
    }

    @FXML
    private void handleAdd() {
        presenter.handleAdd();
    }

    @FXML
    private void handleUpdate() {
        presenter.handleUpdate();
    }

    @FXML
    private void handleDelete() {
        presenter.handleDelete();
    }

    @FXML
    private void handleApply() {
        presenter.handleApplyFilter();
    }

    @FXML
    private void handleReset() {
        presenter.handleResetFilter();
    }
}
