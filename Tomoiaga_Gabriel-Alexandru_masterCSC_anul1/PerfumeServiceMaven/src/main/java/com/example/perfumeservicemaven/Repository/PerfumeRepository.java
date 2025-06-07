package com.example.perfumeservicemaven.Repository;

import com.example.perfumeservicemaven.Domain.IPerfumeRepository;
import com.example.perfumeservicemaven.Domain.Perfume;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class PerfumeRepository implements IPerfumeRepository {
    private Repository repository;

    public PerfumeRepository() throws SQLException {
        this.repository = new Repository();
    }

    @Override
    public Boolean addPerfume(Perfume perfume) {
        String sql = String.format(
                "INSERT INTO perfume (name, brand, price, description) VALUES ('%s', '%s', %f, '%s')",
                perfume.getName(), perfume.getBrand(), perfume.getPrice(), perfume.getDescription()
        );
        return repository.executeUpdate(sql);
    }

    @Override
    public Boolean deletePerfume(Perfume perfume) {
        String sql = String.format("DELETE FROM perfume WHERE perfume_id = %d", perfume.getId());
        return repository.executeUpdate(sql);
    }

    @Override
    public Boolean updatePerfume(Perfume perfume) {
        String sql = String.format(
                "UPDATE perfume SET name = '%s', brand = '%s', price = %f, description = '%s' WHERE perfume_id = %d",
                perfume.getName(), perfume.getBrand(), perfume.getPrice(), perfume.getDescription(), perfume.getId()
        );
        return repository.executeUpdate(sql);
    }

    @Override
    public Perfume searchPerfume(String name) {
        String sql = "SELECT * FROM perfume WHERE name = '" + name + "'";
        ResultSet rs = repository.getTable(sql);

        try {
            if (rs != null && rs.next()) {
                return new Perfume(
                        rs.getInt("perfume_id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Perfume> listPerfumes() {
        List<Perfume> perfumes = new ArrayList<>();
        ResultSet rs = repository.getTable("SELECT * FROM perfume");

        try {
            while (rs != null && rs.next()) {
                perfumes.add(new Perfume(
                        rs.getInt("perfume_id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return perfumes;
    }

    @Override
    public List<Perfume> filterPerfumes(List<Perfume> allPerfumes, String brand, Boolean available, Double maxPrice, Integer storeId) {
        List<Perfume> filtered = new ArrayList<>();

        try {
            for (Perfume p : allPerfumes) {
                boolean matches = true;

                if (brand != null && !p.getBrand().equalsIgnoreCase(brand)) {
                    matches = false;
                }

                if (maxPrice != null && p.getPrice() > maxPrice) {
                    matches = false;
                }
                if (available != null || storeId != null) {
                    String sql = "SELECT quantity FROM stock WHERE perfume_id = " + p.getId();
                    if (storeId != null) {
                        sql += " AND store_id = " + storeId;
                    }

                    ResultSet rs = repository.getTable(sql);
                    boolean hasStock = false;

                    while (rs != null && rs.next()) {
                        int qty = rs.getInt("quantity");
                        if (qty > 0) {
                            hasStock = true;
                            break;
                        }
                    }

                    if ((available != null && available && !hasStock) ||
                            (available != null && !available && hasStock)) {
                        matches = false;
                    }
                }

                if (matches) {
                    filtered.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filtered;
    }
}
