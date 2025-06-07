package com.example.restserviceclient.presenter;

import com.example.restserviceclient.model.entity.Users;

public interface ILoginView {
    void onLoginSuccess(Users user);
    void showError(String message);
}
