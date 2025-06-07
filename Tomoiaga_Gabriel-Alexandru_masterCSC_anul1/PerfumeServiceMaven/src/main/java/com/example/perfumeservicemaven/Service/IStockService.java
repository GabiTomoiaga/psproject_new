package com.example.perfumeservicemaven.Service;

import com.example.perfumeservicemaven.Domain.Stock;
import java.util.List;

public interface IStockService {
    boolean addStock(Stock stock);
    boolean updateStock(Stock stock);
    boolean deleteStock(Integer stockId, Integer perfumeId);
    Stock getStockByStoreAndPerfume(int storeId, int perfumeId);
    List<Stock> getStockByStore(int storeId);
    List<Stock> getAllStock(); // manager
}
