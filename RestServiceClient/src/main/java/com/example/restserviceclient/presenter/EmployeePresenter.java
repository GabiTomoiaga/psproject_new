package com.example.restserviceclient.presenter;

import com.example.restserviceclient.model.entity.Perfume;
import com.example.restserviceclient.model.entity.Stock;
import com.example.restserviceclient.model.repository.PerfumeRepository;
import com.example.restserviceclient.model.repository.StockRepository;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.util.List;

public class EmployeePresenter {

    private final PerfumeRepository perfumeRepo;
    private final StockRepository stockRepo;
    private final int storeId;

    private TextField id, name, brand, price, description, search;
    private TableView<Perfume> table;
    private ChoiceBox<String> filterBrand, filterPrice;
    private CheckBox checkDisponibility;

    public EmployeePresenter(PerfumeRepository perfumeRepo, StockRepository stockRepo, int storeId) {
        this.perfumeRepo = perfumeRepo;
        this.stockRepo = stockRepo;
        this.storeId = storeId;
    }

    public void init() {
        id = (TextField) lookup("#idField");
        name = (TextField) lookup("#name");
        brand = (TextField) lookup("#brandField");
        price = (TextField) lookup("#price");
        description = (TextField) lookup("#description");
        search = (TextField) lookup("#search");
        table = (TableView<Perfume>) lookup("#perfumeTable");

        filterBrand = (ChoiceBox<String>) lookup("#brandFilter");
        filterPrice = (ChoiceBox<String>) lookup("#price");
        checkDisponibility = (CheckBox) lookup("#disponibility");

        if (id == null || table == null) {
            showError("Eroare la încărcarea componentelor UI.");
            return;
        }

        configureTable();
        populateFilters();
        loadPerfumes();
    }


    public void handleAdd() {
        try {
            Perfume perfume = new Perfume(
                    Integer.parseInt(id.getText()),
                    name.getText(),
                    brand.getText(),
                    Double.parseDouble(price.getText()),
                    description.getText()
            );
            if (perfumeRepo.addPerfume(perfume)) {
                stockRepo.add(new Stock(null, storeId, perfume.getId(), 1));
                showInfo("Succes", "Parfumul a fost adăugat.");
                loadPerfumes();
            } else {
                showError("Eroare la adăugare.");
            }
        } catch (NumberFormatException e) {
            showError("ID și prețul trebuie să fie numere valide.");
        }
    }

    public void handleUpdate() {
        try {
            Perfume perfume = new Perfume(
                    Integer.parseInt(id.getText()),
                    name.getText(),
                    brand.getText(),
                    Double.parseDouble(price.getText()),
                    description.getText()
            );
            if (perfumeRepo.updatePerfume(perfume)) {
                showInfo("Succes", "Parfumul a fost actualizat.");
                loadPerfumes();
            } else {
                showError("Eroare la actualizare.");
            }
        } catch (NumberFormatException e) {
            showError("ID și prețul trebuie să fie numere valide.");
        }
    }

    public void handleDelete() {
        Perfume selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selectează un parfum din tabel.");
            return;
        }
        if (perfumeRepo.deletePerfume(selected.getId())) {
            showInfo("Succes", "Parfumul a fost șters.");
            loadPerfumes();
        } else {
            showError("Eroare la ștergere.");
        }
    }

    public void handleSearch() {
        String text = search.getText();
        List<Perfume> filtered = perfumeRepo.searchByName(text);
        table.setItems(FXCollections.observableArrayList(filtered));
    }

    public void handleApplyFilters() {
        String selectedBrand = filterBrand.getValue();
        String selectedPrice = filterPrice.getValue();
        boolean onlyAvailable = checkDisponibility.isSelected();

        List<Perfume> perfumes = perfumeRepo.getAll();

        List<Perfume> filtered = perfumes.stream()
                .filter(p -> selectedBrand == null || selectedBrand.isEmpty() || p.getBrand().equals(selectedBrand))
                .filter(p -> {
                    if (selectedPrice == null) return true;
                    double pr = p.getPrice();
                    return switch (selectedPrice) {
                        case "<100" -> pr < 100;
                        case "100-300" -> pr >= 100 && pr <= 300;
                        case ">300" -> pr > 300;
                        default -> true;
                    };
                })
                .filter(p -> {
                    if (!onlyAvailable) return true;
                    Stock stock = stockRepo.getStockByStoreAndPerfume(storeId, p.getId());
                    return stock != null && stock.getQuantity() > 0;
                })
                .toList();

        table.setItems(FXCollections.observableArrayList(filtered));
    }

    public void handleReset() {
        loadPerfumes();
    }

    private void loadPerfumes() {
        List<Perfume> all = perfumeRepo.getAll();
        table.setItems(FXCollections.observableArrayList(all));
    }

    private void configureTable() {
        TableColumn<Perfume, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Perfume, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Perfume, String> colBrand = new TableColumn<>("Brand");
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Perfume, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Perfume, String> colDesc = new TableColumn<>("Description");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.getColumns().setAll(colId, colName, colBrand, colPrice, colDesc);
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Eroare");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private Object lookup(String fxId) {
        return Window.getWindows().stream()
                .filter(Window::isShowing)
                .findFirst()
                .map(Window::getScene)
                .map(scene -> scene.lookup(fxId))
                .orElse(null);
    }

    private void populateFilters() {
        List<String> brands = perfumeRepo.getAll().stream()
                .map(Perfume::getBrand)
                .distinct()
                .toList();
        filterBrand.getItems().addAll(brands);

        filterPrice.getItems().addAll("<100", "100-300", ">300");
    }
}
