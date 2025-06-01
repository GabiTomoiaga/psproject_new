package com.example.perfumeservicemaven.Repository;

import com.example.perfumeservicemaven.Domain.IUserRepository;
import com.example.perfumeservicemaven.Domain.Users;
import com.example.perfumeservicemaven.Domain.UsersType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class UserRepository implements IUserRepository {
    private final Repository repository;

    public UserRepository() throws SQLException {
        this.repository = new Repository();
    }

    @Override
    public Boolean addUser(Users user) {
        String sql = String.format(
                "INSERT INTO users (username, password, role, store_id) VALUES ('%s', '%s', '%s', %d)",
                user.getName(), user.getPassword(), user.getRole().name(), user.getStoreId()
        );
        return repository.executeUpdate(sql);
    }

    @Override
    public Boolean deleteUser(Users user) {
        String sql = String.format("DELETE FROM users WHERE user_id = %d", user.getId());
        return repository.executeUpdate(sql);
    }

    @Override
    public Boolean updateUser(Users user) {
        String sql = String.format(
                "UPDATE users SET username = '%s', password = '%s', role = '%s', store_id = %d WHERE id = %d",
                user.getName(), user.getPassword(), user.getRole().name(), user.getStoreId(), user.getId()
        );
        return repository.executeUpdate(sql);
    }

    @Override
    public Users searchUserByName(String name) {
        String sql = String.format("SELECT * FROM users WHERE username = '%s'", name);
        ResultSet rs = repository.getTable(sql);

        try {
            if (rs != null && rs.next()) {
                return new Users(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        UsersType.valueOf(rs.getString("role")),
                        rs.getInt("store_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Users> listUsers() {
        List<Users> users = new ArrayList<>();
        ResultSet rs = repository.getTable("SELECT * FROM users");

        try {
            while (rs != null && rs.next()) {
                users.add(new Users(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        UsersType.valueOf(rs.getString("role").toUpperCase()),
                        rs.getInt("store_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
