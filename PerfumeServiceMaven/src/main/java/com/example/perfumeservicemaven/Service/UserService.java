package com.example.perfumeservicemaven.Service;

import com.example.perfumeservicemaven.Domain.IUserRepository;
import com.example.perfumeservicemaven.Domain.Users;
import com.example.perfumeservicemaven.Domain.UsersType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean addUser(Users user) {
        return userRepository.addUser(user);
    }

    @Override
    public boolean updateUser(Users user) {
        return userRepository.updateUser(user);
    }

    @Override
    public boolean deleteUser(Users user) {
        return userRepository.deleteUser(user);
    }

    @Override
    public Users getUserByName(String name) {
        return userRepository.searchUserByName(name);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.listUsers();
    }

    @Override
    public List<Users> getUsersByRole(UsersType role) {
        return userRepository.listUsers().stream()
                .filter(user -> user.getRole() == role)
                .collect(Collectors.toList());
    }
}
