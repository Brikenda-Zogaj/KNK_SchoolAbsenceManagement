package controller;

import database.ConnexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Classes.Nxenesit;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NxenesitController implements Initializable {

    @FXML
    private TableColumn<Nxenesit, Integer> paColumn;
    @FXML
    private TableColumn<Nxenesit, Integer> meaColumn;
    @FXML
    private TableColumn<Nxenesit, Integer> gjithsejColumn;
    @FXML
    private TableColumn<Nxenesit, String> emailColumn;
    @FXML
    private TextField emailField;
    @FXML
    private TableColumn<Nxenesit, String> emriColumn;
    @FXML
    private TextField emriField;
    @FXML
    private TableColumn<Nxenesit, String> emriPrinditColumn;
    @FXML
    private TextField emriPrinditField;
    @FXML
    private Button fshijButton;
    @FXML
    private TableColumn<Nxenesit, Integer> idColumn;
    @FXML
    private TextField idField;
    @FXML
    private TableColumn<Nxenesit, String> mbiemriColumn;
    @FXML
    private TextField mbiemriField;
    @FXML
    private Button ndryshoButton;
    @FXML
    private Button shtoButton;
    @FXML
    private TableView<Nxenesit> studentTable;
    @FXML
    private ComboBox<String> klasaSelect;

    private ObservableList<Nxenesit> data;
    private ConnexionDB dc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dc = new ConnexionDB();
        loadData();
        populateKlasaSelect();
        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }

    private void populateKlasaSelect() {
        klasaSelect.setItems(FXCollections.observableArrayList("All", "Klasa 10", "Klasa 11", "Klasa 12"));
        klasaSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if ("All".equals(newValue)) {
                    loadData();
                } else {
                    loadDataByClass(newValue);
                }
            }
        });
    }

    private void loadData() {
        try (Connection conn = dc.Connect()) {
            data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM nxenesit");
            while (rs.next()) {
                data.add(new Nxenesit(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
            }
            populateTable();
        } catch (SQLException ex) {
            showAlert("Error", "Error loading data: " + ex.getMessage());
        }
    }

    private void loadDataByClass(String className) {
        String sql = "SELECT * FROM nxenesit WHERE klasa = ?";
        try (Connection conn = dc.Connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, className);
            ResultSet rs = pstmt.executeQuery();
            data.clear();
            while (rs.next()) {
                data.add(new Nxenesit(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
            }
            populateTable();
        } catch (SQLException ex) {
            showAlert("Error", "Error loading data: " + ex.getMessage());
        }
    }

    private void populateTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        emriColumn.setCellValueFactory(new PropertyValueFactory<>("emri"));
        emriPrinditColumn.setCellValueFactory(new PropertyValueFactory<>("emriPrindit"));
        mbiemriColumn.setCellValueFactory(new PropertyValueFactory<>("mbiemri"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        meaColumn.setCellValueFactory(new PropertyValueFactory<>("mea"));
        paColumn.setCellValueFactory(new PropertyValueFactory<>("paa"));
        gjithsejColumn.setCellValueFactory(new PropertyValueFactory<>("gjithsej"));
        studentTable.setItems(null);
        studentTable.setItems(data);
    }

    private void populateFields(Nxenesit nxenesi) {
        idField.setText(String.valueOf(nxenesi.getId()));
        emriField.setText(nxenesi.getEmri());
        emriPrinditField.setText(nxenesi.getEmriPrindit());
        mbiemriField.setText(nxenesi.getMbiemri());
        emailField.setText(nxenesi.getEmail());
    }

    @FXML
    void fshijTedhena(ActionEvent event) {
        int id = Integer.parseInt(idField.getText());
        try (Connection conn = dc.Connect()) {
            String query = "DELETE FROM nxenesit WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Data deleted successfully!");
                loadData();
            } else {
                showAlert("Error", "No data found with the given ID.");
            }
        } catch (SQLException ex) {
            showAlert("Error", "Error deleting data: " + ex.getMessage());
        }
    }

    @FXML
    void ndryshoTeDhena(ActionEvent event) {
        int id = Integer.parseInt(idField.getText());
        String emri = emriField.getText();
        String emriPrindit = emriPrinditField.getText();
        String mbiemri = mbiemriField.getText();
        String email = emailField.getText();

        try (Connection conn = dc.Connect()) {
            String query = "UPDATE nxenesit SET emri = ?, emriPrindit = ?, mbiemri = ?, email = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, emri);
            preparedStatement.setString(2, emriPrindit);
            preparedStatement.setString(3, mbiemri);
            preparedStatement.setString(4, email);
            preparedStatement.setInt(5, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Data updated successfully!");
                loadData();
            } else {
                showAlert("Error", "No data found with the given ID.");
            }
        } catch (SQLException ex) {
            showAlert("Error", "Error updating data: " + ex.getMessage());
        }
    }

    @FXML
    void shtoTeDhena(ActionEvent event) {
        String emri = emriField.getText();
        String emriPrindit = emriPrinditField.getText();
        String mbiemri = mbiemriField.getText();
        String email = emailField.getText();

        try (Connection conn = dc.Connect()) {
            String query = "INSERT INTO nxenesit (emri, emriPrindit, mbiemri, email) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, emri);
            preparedStatement.setString(2, emriPrindit);
            preparedStatement.setString(3, mbiemri);
            preparedStatement.setString(4, email);
            preparedStatement.executeUpdate();
            showAlert("Success", "Data added successfully!");
            loadData();
        } catch (SQLException ex) {
            showAlert("Error", "Error adding data: " + ex.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
