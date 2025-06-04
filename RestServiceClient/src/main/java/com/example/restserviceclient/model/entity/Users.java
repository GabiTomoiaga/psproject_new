package com.example.restserviceclient.model.entity;

public class Users {
    private Integer id;
    private String name;
    private String password;
    private UsersType role;
    private Integer storeId;

    public Users(Integer id, String name, String password, UsersType role, Integer storeId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.storeId = storeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsersType getRole() {
        return role;
    }

    public void setRole(UsersType role) {
        this.role = role;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}
