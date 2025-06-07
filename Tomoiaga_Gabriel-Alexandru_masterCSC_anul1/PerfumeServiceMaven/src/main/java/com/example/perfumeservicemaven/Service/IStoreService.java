package com.example.perfumeservicemaven.Service;

import com.example.perfumeservicemaven.Domain.Store;

import java.util.List;

public interface IStoreService {
    boolean addStore(Store store);
    boolean updateStore(Store store);
    boolean deleteStore(Store store);
    Store searchStoreByName(String name);
    List<Store> getAllStores();
}
