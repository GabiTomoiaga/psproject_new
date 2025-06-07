package com.example.restserviceclient.presenter;

import com.example.restserviceclient.model.entity.Perfume;
import com.example.restserviceclient.model.repository.PerfumeRepository;
import com.example.restserviceclient.model.repository.StockRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class ManagerPresenter {

    private final PerfumeRepository perfumeRepo;
    private final StockRepository stockRepo;

    private TextField id, name, brand, price, description, search;
    private TableView<Perfume> table;
    private ChoiceBox<String> filterBrand, filterPrice, exportChoice;
    private CheckBox checkDisponibility;

    public ManagerPresenter(PerfumeRepository perfumeRepo, StockRepository stockRepo) {
        this.perfumeRepo = perfumeRepo;
        this.stockRepo = stockRepo;
    }

    public void init() {
        id = (TextField) lookup("#id");
        name = (TextField) lookup("#name");
        brand = (TextField) lookup("#brand");
        price = (TextField) lookup("#price");
        description = (TextField) lookup("#description");
        search = (TextField) lookup("#search");
        table = (TableView<Perfume>) lookup("#list");

        filterBrand = (ChoiceBox<String>) lookup("#brand");
        filterPrice = (ChoiceBox<String>) lookup("#price");
        exportChoice = (ChoiceBox<String>) lookup("#exportChoice");
        checkDisponibility = (CheckBox) lookup("#disponibility");

        if (table == null || id == null) {
            showError("Eroare la inițializarea componentelor.");
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
                showInfo("Adăugat cu succes", "Parfumul a fost adăugat.");
                loadPerfumes();
            } else {
                showError("Eroare la adăugare.");
            }
        } catch (NumberFormatException e) {
            showError("ID și Preț trebuie să fie numere valide.");
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
                showInfo("Actualizat", "Parfumul a fost actualizat.");
                loadPerfumes();
            } else {
                showError("Eroare la actualizare.");
            }
        } catch (NumberFormatException e) {
            showError("ID și Preț trebuie să fie numere valide.");
        }
    }

    public void handleDelete() {
        Perfume selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selectează un parfum.");
            return;
        }
        if (perfumeRepo.deletePerfume(selected.getId())) {
            showInfo("Șters", "Parfumul a fost șters.");
            loadPerfumes();
        } else {
            showError("Eroare la ștergere.");
        }
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
                    return stockRepo.getAllByStore(1).stream()
                            .anyMatch(s -> s.getPefumeId().equals(p.getId()) && s.getQuantity() > 0);
                })
                .toList();

        table.setItems(FXCollections.observableArrayList(filtered));
    }

    public void handleSearch() {
        String keyword = search.getText();
        if (keyword == null || keyword.isEmpty()) {
            loadPerfumes();
            return;
        }

        List<Perfume> filtered = perfumeRepo.searchByName(keyword);
        table.setItems(FXCollections.observableArrayList(filtered));
    }

    public void handleExport() {
        List<Perfume> perfumes = table.getItems();
        String format = exportChoice.getValue();

        if (perfumes == null || perfumes.isEmpty()) {
            showError("Nu există date pentru export.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("exported_perfumes." + format.toLowerCase());
        File file = fileChooser.showSaveDialog(Window.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null));

        if (file == null) return;

        try (PrintWriter writer = new PrintWriter(file)) {
            switch (format) {
                case "CSV" -> exportCSV(writer, perfumes);
                case "TXT" -> exportTXT(writer, perfumes);
                case "JSON" -> exportJSON(writer, perfumes);
            }
            showInfo("Export reușit", "Fișierul a fost salvat în format " + format);
        } catch (Exception e) {
            showError("Eroare la export: " + e.getMessage());
        }
    }

    public void handleReset() {
        loadPerfumes();
    }

    private void loadPerfumes() {
        List<Perfume> perfumes = perfumeRepo.getAll();
        table.setItems(FXCollections.observableArrayList(perfumes));
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

    private void populateFilters() {
        List<String> brands = perfumeRepo.getAll().stream()
                .map(Perfume::getBrand)
                .distinct()
                .toList();

        filterBrand.getItems().addAll(brands);
        filterPrice.getItems().addAll("<100", "100-300", ">300");
        exportChoice.getItems().addAll("CSV", "TXT", "JSON");
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

    private void exportCSV(PrintWriter writer, List<Perfume> list) {
        writer.println("ID,Name,Brand,Price,Description");
        for (Perfume p : list) {
            writer.printf("%d,%s,%s,%.2f,%s%n", p.getId(), p.getName(), p.getBrand(), p.getPrice(), p.getDescription());
        }
    }

    private void exportTXT(PrintWriter writer, List<Perfume> list) {
        for (Perfume p : list) {
            writer.printf("ID: %d | Name: %s | Brand: %s | Price: %.2f | Description: %s%n",
                    p.getId(), p.getName(), p.getBrand(), p.getPrice(), p.getDescription());
        }
    }

    private void exportJSON(PrintWriter writer, List<Perfume> list) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));
    }
}
