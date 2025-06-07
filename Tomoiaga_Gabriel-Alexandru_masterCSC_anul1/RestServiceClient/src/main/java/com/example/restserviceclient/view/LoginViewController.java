package com.example.restserviceclient.view;

import com.example.restserviceclient.model.repository.UserRepository;
import com.example.restserviceclient.presenter.LoginPresenter;
import javafx.fxml.FXML;

public class LoginViewController {

    private LoginPresenter presenter;

    @FXML
    public void initialize() {
        presenter = new LoginPresenter(new UserRepository());
        presenter.init();
    }

    @FXML
    private void handleLogin() {
        presenter.handleLogin();
    }
}
