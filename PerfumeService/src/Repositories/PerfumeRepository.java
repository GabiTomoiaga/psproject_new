package Repositories;

import Domain.IPerfumeRepository;

import java.sql.SQLException;

public class PerfumeRepository implements IPerfumeRepository {
    private Repository repository;

    public PerfumeRepository() throws SQLException {
        this.repository = new Repository();
    }

}
