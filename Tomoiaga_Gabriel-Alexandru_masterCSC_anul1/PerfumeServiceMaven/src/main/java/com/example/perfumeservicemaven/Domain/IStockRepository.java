package com.example.perfumeservicemaven.Domain;

import java.util.List;

public interface IStockRepository {
    Boolean addStock(Stock stock);

    Boolean updateStock(Stock stock);

    Boolean deleteStock(Integer storeId, Integer perfumeId);

    Stock getStock(Integer storeId, Integer perfumeId);

    List<Stock> listStocksByStore(Integer storeId);

    List<Stock> listAllStocks();
}
