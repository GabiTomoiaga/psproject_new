package com.example.perfumeservicemaven.Domain;

import java.util.List;

public interface IPerfumeRepository {
    Boolean addPerfume(Perfume perfume);

    Boolean deletePerfume(Perfume perfume);

    Boolean updatePerfume(Perfume perfume);

    Perfume searchPerfume(String name);

    List<Perfume> listPerfumes();

    List<Perfume> filterPerfumes(List<Perfume> perfumes, String brand, Boolean available, Double maxPrice, Integer storeId);
}
