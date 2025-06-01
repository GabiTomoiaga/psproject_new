package com.example.perfumeservicemaven.Service;

import com.example.perfumeservicemaven.Domain.IStoreRepository;
import com.example.perfumeservicemaven.Domain.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService implements IStoreService {

    private final IStoreRepository storeRepository;

    @Autowired
    public StoreService(IStoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public boolean addStore(Store store) {
        return storeRepository.addStore(store);
    }

    @Override
    public boolean updateStore(Store store) {
        return storeRepository.updateStore(store);
    }

    @Override
    public boolean deleteStore(Store store) {
        return storeRepository.deleteStore(store);
    }

    @Override
    public Store searchStoreByName(String name) {
        return storeRepository.searchStore(name);
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.listStores();
    }
}
