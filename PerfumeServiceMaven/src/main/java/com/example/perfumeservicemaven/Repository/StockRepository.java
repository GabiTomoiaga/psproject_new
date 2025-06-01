package com.example.perfumeservicemaven.Repository;

import com.example.perfumeservicemaven.Domain.IStockRepository;
import com.example.perfumeservicemaven.Domain.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class StockRepository implements IStockRepository {
    private Repository repository;

    public StockRepository() throws SQLException {
        this.repository = new Repository();
    }

    @Override
    public Boolean addStock(Stock stock) {
        String sql = String.format(
                "INSERT INTO stock (store_id, perfume_id, quantity) VALUES (%d, %d, %d)",
                stock.getStoreId(), stock.getPefumeId(), stock.getQuantity()
        );
        return repository.executeUpdate(sql);
    }

    @Override
    public Boolean updateStock(Stock stock) {
        String sql = String.format(
                "UPDATE stock SET store_id = %d, perfume_id = %d, quantity = %d WHERE stock_id = %d",
                stock.getStoreId(), stock.getPefumeId(), stock.getQuantity(), stock.getStockId()
        );
        return repository.executeUpdate(sql);
    }

    @Override
    public Boolean deleteStock(Integer stockId, Integer perfumeId) {
        String sql = String.format("DELETE FROM stock WHERE stock_id = %d", stockId);
        return repository.executeUpdate(sql);
    }

    @Override
    public Stock getStock(Integer storeId, Integer perfumeId) {
        String sql = String.format(
                "SELECT * FROM stock WHERE store_id = %d AND perfume_id = %d",
                storeId, perfumeId
        );
        ResultSet rs = repository.getTable(sql);

        try {
            if (rs != null && rs.next()) {
                return new Stock(
                        rs.getInt("stock_id"),
                        rs.getInt("store_id"),
                        rs.getInt("perfume_id"),
                        rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Stock> listStocksByStore(Integer storeId) {
        List<Stock> stocks = new ArrayList<>();
        String sql = String.format("SELECT * FROM stock WHERE store_id = %d", storeId);
        ResultSet rs = repository.getTable(sql);

        try {
            while (rs != null && rs.next()) {
                stocks.add(new Stock(
                        rs.getInt("stock_id"),
                        rs.getInt("store_id"),
                        rs.getInt("perfume_id"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stocks;
    }

    @Override
    public List<Stock> listAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM stock";
        ResultSet rs = repository.getTable(sql);

        try {
            while (rs != null && rs.next()) {
                stocks.add(new Stock(
                        rs.getInt("stock_id"),
                        rs.getInt("store_id"),
                        rs.getInt("perfume_id"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stocks;
    }
}