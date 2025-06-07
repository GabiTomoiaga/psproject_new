package com.example.perfumeservicemaven.Service;

import com.example.perfumeservicemaven.Domain.IStockRepository;
import com.example.perfumeservicemaven.Domain.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService implements IStockService {

    private final IStockRepository stockRepository;

    @Autowired
    public StockService(IStockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public boolean addStock(Stock stock) {
        return stockRepository.addStock(stock);
    }

    @Override
    public boolean updateStock(Stock stock) {
        return stockRepository.updateStock(stock);
    }

    @Override
    public boolean deleteStock(Integer stockId, Integer perfumeId) {
        return stockRepository.deleteStock(stockId, perfumeId);
    }

    @Override
    public Stock getStockByStoreAndPerfume(int storeId, int perfumeId) {
        return stockRepository.getStock(storeId, perfumeId);
    }

    @Override
    public List<Stock> getStockByStore(int storeId) {
        return stockRepository.listStocksByStore(storeId);
    }

    @Override
    public List<Stock> getAllStock() {
        return stockRepository.listAllStocks();
    }
}
