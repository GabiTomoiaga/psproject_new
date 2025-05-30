package Repositories;

import Domain.IStoreRepository;

import java.sql.SQLException;

public class StoreRepository implements IStoreRepository {
    private Repository repository;

    public StoreRepository () throws SQLException {
        this.repository = new Repository();
    }

}
