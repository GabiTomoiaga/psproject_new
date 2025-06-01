package com.example.perfumeservicemaven.Service;

import com.example.perfumeservicemaven.Domain.IPerfumeRepository;
import com.example.perfumeservicemaven.Domain.IStockRepository;
import com.example.perfumeservicemaven.Domain.Perfume;
import com.example.perfumeservicemaven.Domain.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerfumeService implements IPerfumeService {

    private final IPerfumeRepository perfumeRepository;
    private final IStockRepository stockRepository;

    @Autowired
    public PerfumeService(IPerfumeRepository perfumeRepository, IStockRepository stockRepository) {
        this.perfumeRepository = perfumeRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public boolean addPerfume(Perfume perfume) {
        return perfumeRepository.addPerfume(perfume);
    }

    @Override
    public boolean updatePerfume(Perfume perfume) {
        return perfumeRepository.updatePerfume(perfume);
    }

    @Override
    public boolean deletePerfume(Perfume perfume) {
        return perfumeRepository.deletePerfume(perfume);
    }

    @Override
    public Perfume searchByName(String name) {
        return perfumeRepository.searchPerfume(name);
    }

    @Override
    public List<Perfume> getAllPerfumes() {
        return perfumeRepository.listPerfumes();
    }

    @Override
    public List<Perfume> getPerfumesByStore(int storeId) {
        List<Stock> stockList = stockRepository.listStocksByStore(storeId);
        List<Perfume> result = new ArrayList<>();
        for (Stock s : stockList) {
            Perfume p = perfumeRepository.listPerfumes().stream()
                    .filter(perfume -> perfume.getId().equals(s.getPefumeId()))
                    .findFirst().orElse(null);
            if (p != null) result.add(p);
        }
        return result;
    }

    @Override
    public List<Perfume> filterPerfumes(String brand, Boolean available, Double maxPrice, Integer storeId) {
        List<Perfume> all = perfumeRepository.listPerfumes();
        List<Perfume> filtered = new ArrayList<>();

        for (Perfume p : all) {
            boolean matches = true;

            if (brand != null && !p.getBrand().equalsIgnoreCase(brand))
                matches = false;

            if (maxPrice != null && p.getPrice() > maxPrice)
                matches = false;

            if (storeId != null && available != null) {
                Stock stock = stockRepository.getStock(storeId, p.getId());
                boolean inStock = stock != null && stock.getQuantity() > 0;
                if (available != inStock)
                    matches = false;
            }

            if (matches)
                filtered.add(p);
        }

        return filtered;
    }
}
