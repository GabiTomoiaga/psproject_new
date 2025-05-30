package Repositories;

import Domain.IStockRepository;

import java.sql.SQLException;

public class StockRepository implements IStockRepository {
    private Repository repository;

    public StockRepository () throws SQLException {
        this.repository = new Repository();
    }

}
