package com.example.perfumeservicemaven.Repository;

import com.example.perfumeservicemaven.Domain.IStoreRepository;
import com.example.perfumeservicemaven.Domain.Store;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class StoreRepository implements IStoreRepository {
    private Repository repository;

    public StoreRepository() throws SQLException {
        this.repository = new Repository();
    }

    @Override
    public Boolean addStore(Store store) {
        String sql = String.format(
                "INSERT INTO stores (store_name, location) VALUES ('%s', '%s')",
                store.getName(), store.getLocation()
        );
        return repository.executeUpdate(sql);
    }

    @Override
    public Boolean deleteStore(Store store) {
        String sql = String.format("DELETE FROM stores WHERE store_id = %d", store.getId());
        return repository.executeUpdate(sql);
    }

    @Override
    public Boolean updateStore(Store store) {
        String sql = String.format(
                "UPDATE stores SET store_name = '%s', location = '%s' WHERE id = %d",
                store.getName(), store.getLocation(), store.getId()
        );
        return repository.executeUpdate(sql);
    }

    @Override
    public Store searchStore(String name) {
        String sql = "SELECT * FROM stores WHERE store_name = '" + name + "'";
        ResultSet rs = repository.getTable(sql);

        try {
            if (rs != null && rs.next()) {
                return new Store(
                        rs.getInt("store_id"),
                        rs.getString("store_name"),
                        rs.getString("location")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Store> listStores() {
        List<Store> stores = new ArrayList<>();
        ResultSet rs = repository.getTable("SELECT * FROM stores");

        try {
            while (rs != null && rs.next()) {
                stores.add(new Store(
                        rs.getInt("store_id"),
                        rs.getString("store_name"),
                        rs.getString("location")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stores;
    }
}
