package com.example.perfumeservicemaven.Domain;

import java.util.List;

public interface IStoreRepository {
    Boolean addStore (Store store);

    Boolean deleteStore (Store store);

    Boolean updateStore (Store store);

    Store searchStore(String name);

    List<Store> listStores();
}
