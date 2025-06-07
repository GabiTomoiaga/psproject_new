package com.example.perfumeservicemaven.Controller;

import com.example.perfumeservicemaven.Domain.Perfume;
import com.example.perfumeservicemaven.Service.IPerfumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfumes")
public class PerfumeController {

    private final IPerfumeService perfumeService;

    @Autowired
    public PerfumeController(IPerfumeService perfumeService) {
        this.perfumeService = perfumeService;
    }

    @GetMapping
    public List<Perfume> getAll() {
        return perfumeService.getAllPerfumes();
    }

    @GetMapping("/store/{storeId}")
    public List<Perfume> getByStore(@PathVariable int storeId) {
        return perfumeService.getPerfumesByStore(storeId);
    }

    @GetMapping("/filter")
    public List<Perfume> filter(@RequestParam(required = false) String brand,
                                @RequestParam(required = false) Boolean available,
                                @RequestParam(required = false) Double maxPrice,
                                @RequestParam(required = false) Integer storeId) {
        return perfumeService.filterPerfumes(brand, available, maxPrice, storeId);
    }

    @GetMapping("/{name}")
    public Perfume search(@PathVariable String name) {
        return perfumeService.searchByName(name);
    }

    @PostMapping
    public boolean add(@RequestBody Perfume perfume) {
        return perfumeService.addPerfume(perfume);
    }

    @PutMapping
    public boolean update(@RequestBody Perfume perfume) {
        return perfumeService.updatePerfume(perfume);
    }

    @DeleteMapping
    public boolean delete(@RequestBody Perfume perfume) {
        return perfumeService.deletePerfume(perfume);
    }
}
