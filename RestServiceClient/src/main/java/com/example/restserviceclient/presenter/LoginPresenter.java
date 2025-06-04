package com.example.restserviceclient.presenter;

import com.example.restserviceclient.model.entity.Users;
import com.example.restserviceclient.model.repository.UserRepository;

import java.util.List;

public class LoginPresenter {
    private final ILoginView view;
    private final UserRepository repository;

    public LoginPresenter(ILoginView view, UserRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void login(String username, String password) {
        List<Users> users = repository.getAll();
        for (Users user : users) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                view.onLoginSuccess(user);
                return;
            }
        }
        view.showError("Invalid username or password.");
    }
}
