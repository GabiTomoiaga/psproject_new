package com.example.perfumeservicemaven.Domain;

import java.util.List;

public interface IUserRepository {
    Boolean addUser(Users user);
    Boolean deleteUser(Users user);
    Boolean updateUser(Users user);
    Users searchUserByName(String name);
    List<Users> listUsers();
}
