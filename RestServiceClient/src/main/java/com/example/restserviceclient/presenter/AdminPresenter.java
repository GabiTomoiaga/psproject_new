package com.example.restserviceclient.presenter;

import com.example.restserviceclient.model.repository.UserRepository;
import com.example.restserviceclient.model.entity.Users;

import java.util.List;

public class AdminPresenter {
    private final IAdminView view;
    private final UserRepository userRepository;

    public AdminPresenter(IAdminView view, UserRepository userRepository) {
        this.view = view;
        this.userRepository = userRepository;
    }

    public void loadAllUsers() {
        List<Users> users = userRepository.getAll();
        view.showUsers(users);
    }

    public void filterByRole(String role) {
        List<Users> filtered = userRepository.filterByRole(role);
        view.showUsers(filtered);
    }

    public void addUser(Users user) {
        if (userRepository.addUser(user)) loadAllUsers();
        else view.showError("Failed to add user.");
    }

    public void deleteUser(int id) {
        if (userRepository.deleteUser(id)) loadAllUsers();
        else view.showError("Failed to delete user.");
    }
}
