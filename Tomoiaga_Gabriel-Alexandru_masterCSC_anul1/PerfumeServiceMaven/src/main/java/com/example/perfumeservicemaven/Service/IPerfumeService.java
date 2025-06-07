package com.example.perfumeservicemaven.Service;

import com.example.perfumeservicemaven.Domain.Perfume;
import java.util.List;

public interface IPerfumeService {
    boolean addPerfume(Perfume perfume);
    boolean updatePerfume(Perfume perfume);
    boolean deletePerfume(Perfume perfume);
    Perfume searchByName(String name);
    List<Perfume> getAllPerfumes(); // manager
    List<Perfume> getPerfumesByStore(int storeId); // angajat
    List<Perfume> filterPerfumes(String brand, Boolean available, Double maxPrice, Integer storeId);
}
