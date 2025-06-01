package Service;

import Domain.Users;
import Domain.UsersType;

import java.util.List;

public interface IUserService {
    boolean addUser(Users user);
    boolean updateUser(Users user);
    boolean deleteUser(Users user);
    Users getUserByName(String name);
    List<Users> getAllUsers();
    List<Users> getUsersByRole(UsersType role);
}
