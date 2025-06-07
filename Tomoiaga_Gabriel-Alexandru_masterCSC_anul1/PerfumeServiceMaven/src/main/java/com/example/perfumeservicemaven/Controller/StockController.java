package com.example.perfumeservicemaven.Controller;

import com.example.perfumeservicemaven.Domain.Stock;
import com.example.perfumeservicemaven.Service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final IStockService stockService;

    @Autowired
    public StockController(IStockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public List<Stock> getAll() {
        return stockService.getAllStock();
    }

    @GetMapping("/store/{storeId}")
    public List<Stock> getByStore(@PathVariable int storeId) {
        return stockService.getStockByStore(storeId);
    }

    @GetMapping("/check")
    public Stock getOne(@RequestParam int storeId, @RequestParam int perfumeId) {
        return stockService.getStockByStoreAndPerfume(storeId, perfumeId);
    }

    @PostMapping
    public boolean add(@RequestBody Stock stock) {
        return stockService.addStock(stock);
    }

    @PutMapping
    public boolean update(@RequestBody Stock stock) {
        return stockService.updateStock(stock);
    }

    @DeleteMapping("/{storeId}")
    public boolean delete(@PathVariable Integer storeId, Integer perfumeId) {
        return stockService.deleteStock(storeId, perfumeId);
    }
}
