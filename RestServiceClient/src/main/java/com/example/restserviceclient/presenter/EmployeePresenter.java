package com.example.restserviceclient.presenter;

import com.example.restserviceclient.model.entity.Perfume;
import com.example.restserviceclient.model.entity.Stock;
import com.example.restserviceclient.model.repository.PerfumeRepository;
import com.example.restserviceclient.model.repository.StockRepository;

import java.util.List;

public class EmployeePresenter {
    private final IEmployeeView view;
    private final PerfumeRepository perfumeRepository;
    private final StockRepository stockRepository;
    private final int storeId;

    public EmployeePresenter(IEmployeeView view, PerfumeRepository perfumeRepository, StockRepository stockRepository, int storeId) {
        this.view = view;
        this.perfumeRepository = perfumeRepository;
        this.stockRepository = stockRepository;
        this.storeId = storeId;
    }

    public void loadPerfumes() {
        List<Perfume> perfumes = perfumeRepository.getAll();
        view.showPerfumes(perfumes);
    }

    public void filterPerfumes(String brand, Double maxPrice) {
        List<Perfume> filtered = perfumeRepository.filterPerfumes(brand, storeId, maxPrice);
        view.showPerfumes(filtered);
    }

    public void searchPerfumeByName(String name) {
        List<Perfume> result = perfumeRepository.searchByName(name);
        view.showPerfumes(result);
    }

    public void addPerfumeToStore(Stock stock) {
        boolean ok = stockRepository.addStock(stock);
        if (ok) view.showSuccess("Added perfume to stock.");
        else view.showError("Failed to add perfume to stock.");
    }
}
