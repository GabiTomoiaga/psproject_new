package com.example.restserviceclient.model.entity;

public class Stock {
    private Integer stockId;
    private Integer storeId;
    private Integer pefumeId;
    private Integer quantity;

    public Stock(Integer stockId, Integer storeId, Integer pefumeId, Integer quantity) {
        this.stockId = stockId;
        this.storeId = storeId;
        this.pefumeId = pefumeId;
        this.quantity = quantity;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getPefumeId() {
        return pefumeId;
    }

    public void setPefumeId(Integer pefumeId) {
        this.pefumeId = pefumeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
