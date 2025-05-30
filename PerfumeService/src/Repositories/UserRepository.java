package Repositories;

import Domain.IUserRepository;

import java.sql.SQLException;

public class UserRepository implements IUserRepository {
    private Repository repository;

    public UserRepository () throws SQLException {
        this.repository = new Repository();
    }
}
