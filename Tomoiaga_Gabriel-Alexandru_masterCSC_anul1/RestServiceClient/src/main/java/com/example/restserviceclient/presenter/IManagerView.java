package com.example.restserviceclient.presenter;

import com.example.restserviceclient.model.entity.Perfume;
import java.util.List;

public interface IManagerView {
    void showPerfumes(List<Perfume> perfumes);
    void showChartData(int[] data);  // Poți adapta tipul dacă vrei date mai complexe
    void showExportSuccess(String message);
    void showError(String message);
}
