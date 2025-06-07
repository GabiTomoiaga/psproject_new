package com.example.restserviceclient.view;

import com.example.restserviceclient.model.repository.PerfumeRepository;
import com.example.restserviceclient.model.repository.StockRepository;
import com.example.restserviceclient.presenter.ManagerPresenter;
import javafx.fxml.FXML;

public class ManagerViewController {

    private ManagerPresenter presenter;

    @FXML
    public void initialize() {
        presenter = new ManagerPresenter(
                new PerfumeRepository(),
                new StockRepository()
        );
        presenter.init();
    }

    @FXML
    private void handleAdd() {
        presenter.handleAdd();
    }

    @FXML
    private void handleUpdate() {
        presenter.handleUpdate();
    }

    @FXML
    private void handleDelete() {
        presenter.handleDelete();
    }

    @FXML
    private void handleSearch() {
        presenter.handleSearch();
    }

    @FXML
    private void handleApply() {
        presenter.handleApplyFilters();
    }

    @FXML
    private void handleReset() {
        presenter.handleReset();
    }

    @FXML
    private void handleExport() {
        presenter.handleExport();
    }
}
