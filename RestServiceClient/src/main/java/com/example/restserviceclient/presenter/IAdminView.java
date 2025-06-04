package com.example.restserviceclient.presenter;

import com.example.restserviceclient.model.entity.Users;

import java.util.List;

public interface IAdminView {
    void showUsers(List<Users> users);
    void showError(String message);
}
