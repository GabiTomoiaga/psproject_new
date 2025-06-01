package com.example.perfumeservicemaven.Controller;

import com.example.perfumeservicemaven.Domain.Store;
import com.example.perfumeservicemaven.Service.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final IStoreService storeService;

    @Autowired
    public StoreController(IStoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public List<Store> getAll() {
        return storeService.getAllStores();
    }

    @GetMapping("/{name}")
    public Store getByName(@PathVariable String name) {
        return storeService.searchStoreByName(name);
    }

    @PostMapping
    public boolean add(@RequestBody Store store) {
        return storeService.addStore(store);
    }

    @PutMapping
    public boolean update(@RequestBody Store store) {
        return storeService.updateStore(store);
    }

    @DeleteMapping
    public boolean delete(@RequestBody Store store) {
        return storeService.deleteStore(store);
    }
}
