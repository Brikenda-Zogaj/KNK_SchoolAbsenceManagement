package controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Classes.Nxenesit;
import database.ConnexionDB;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mungesat {

    @FXML
    private TableView<Nxenesit> tableColumn;

    @FXML
    private TableColumn<Nxenesit, Integer> IDColumn;
    @FXML
    private TableColumn<Nxenesit, String> EmriColumn;
    @FXML
    private TableColumn<Nxenesit, String> emriPrinditColumn;
    @FXML
    private TableColumn<Nxenesit, String> mbiemriColumn;
    @FXML
    private TableColumn<Nxenesit, CheckBox> mungonColumn;
    @FXML
    private TableColumn<Nxenesit, CheckBox> maColumn;
    @FXML
    private TableColumn<Nxenesit, CheckBox> paColumn;

    @FXML
    private ComboBox<String> klasaComboBox;

    @FXML
    private TextField searchField;

    private ObservableList<Nxenesit> nxenesitList = FXCollections.observableArrayList();
    private FilteredList<Nxenesit> filteredList = new FilteredList<>(nxenesitList, p -> true);

    @FXML
    public void initialize() {
        // Set up the columns in the table
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        EmriColumn.setCellValueFactory(new PropertyValueFactory<>("emri"));
        emriPrinditColumn.setCellValueFactory(new PropertyValueFactory<>("emriPrindit"));
        mbiemriColumn.setCellValueFactory(new PropertyValueFactory<>("mbiemri"));
        mungonColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMungonCheckbox()));
        maColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMeArsyeCheckbox()));
        paColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPaArsyeCheckbox()));

        // Bind the filtered list to the table
        tableColumn.setItems(filteredList);

        // Populate the ComboBox with class options, including "All"
        klasaComboBox.setItems(FXCollections.observableArrayList("All", "Klasa 10", "Klasa 11", "Klasa 12"));

        // Load all students initially
        loadAllNxenesit();

        // Load data into table when class selection changes
        klasaComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if ("All".equals(newValue)) {
                    loadAllNxenesit();
                } else {
                    loadNxenesitByClass(newValue);
                }
            }
        });

        // Add a listener to the search field to filter the table
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterNxenesit();
        });
    }

    private void loadAllNxenesit() {
        String sql = "SELECT id, emri, emriPrindit, mbiemri FROM nxenesit";
        try (Connection conn = ConnexionDB.Connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            nxenesitList.clear();
            while (rs.next()) {
                int id = rs.getInt("id");
                String emri = rs.getString("emri");
                String emriPrindit = rs.getString("emriPrindit");
                String mbiemri = rs.getString("mbiemri");

                Nxenesit nxenesi = new Nxenesit(id, emri, emriPrindit, mbiemri);
                nxenesitList.add(nxenesi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception or show error message
        }
    }

    private void loadNxenesitByClass(String className) {
        String sql = "SELECT * FROM nxenesit WHERE klasa = ?";
        try (Connection conn = ConnexionDB.Connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, className);
            try (ResultSet rs = pstmt.executeQuery()) {
                nxenesitList.clear();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String emri = rs.getString("emri");
                    String emriPrindit = rs.getString("emriPrindit");
                    String mbiemri = rs.getString("mbiemri");
                    boolean mungon = rs.getBoolean("mungon");
                    boolean meArsye = rs.getBoolean("meArsye");
                    boolean paArsye = rs.getBoolean("paArsye");

                    Nxenesit nxenesi = new Nxenesit(id, emri, emriPrindit, mbiemri);
                    nxenesi.getMungonCheckbox().setSelected(mungon);
                    nxenesi.getMeArsyeCheckbox().setSelected(meArsye);
                    nxenesi.getPaArsyeCheckbox().setSelected(paArsye);

                    nxenesitList.add(nxenesi);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception or show error message
        }
    }

    private void filterNxenesit() {
        String searchText = searchField.getText().toLowerCase();
        filteredList.setPredicate(nxenesi -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            if (nxenesi.getEmri().toLowerCase().contains(searchText)) {
                return true;
            } else if (String.valueOf(nxenesi.getId()).contains(searchText)) {
                return true;
            } else if (nxenesi.getMbiemri().toLowerCase().contains(searchText)) {
                return true;
            } else if (nxenesi.getEmriPrindit().toLowerCase().contains(searchText)) {
                return true;
            }
            return false;
        });
    }

    @FXML
    private void onClick(ActionEvent event) {
        // Add your button click logic here
        System.out.println("Button clicked!");
    }
}
