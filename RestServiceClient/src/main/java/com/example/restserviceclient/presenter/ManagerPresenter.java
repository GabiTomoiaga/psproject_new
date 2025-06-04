package com.example.restserviceclient.presenter;

import com.example.restserviceclient.model.entity.Perfume;
import com.example.restserviceclient.model.entity.Stock;
import com.example.restserviceclient.model.repository.PerfumeRepository;
import com.example.restserviceclient.model.repository.StockRepository;

import java.util.List;

public class ManagerPresenter {
    private final IManagerView view;
    private final PerfumeRepository perfumeRepository;
    private final StockRepository stockRepository;

    public ManagerPresenter(IManagerView view, PerfumeRepository perfumeRepository, StockRepository stockRepository) {
        this.view = view;
        this.perfumeRepository = perfumeRepository;
        this.stockRepository = stockRepository;
    }

    public void loadPerfumesAcrossStores() {
        List<Perfume> perfumes = perfumeRepository.getAll();
        view.showPerfumes(perfumes);
    }

    public void export(String format) {
        // dummy method for now
        view.showExportSuccess("Exported in format: " + format);
    }

    public void showStatistics() {
        // dummy method for now
        view.showChartData(new int[]{10, 15, 7});
    }
}
