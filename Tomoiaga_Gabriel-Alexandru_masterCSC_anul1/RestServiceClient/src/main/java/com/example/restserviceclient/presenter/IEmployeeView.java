package com.example.restserviceclient.presenter;

import com.example.restserviceclient.model.entity.Perfume;

import java.util.List;

public interface IEmployeeView {
    void showPerfumes(List<Perfume> perfumes);
    void showError(String message);
    void showSuccess(String message);
}
