package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static org.example.Helpers.*;

public class MainControllerAdmin {

    @FXML
    public Label welcomeText;

    @FXML
    private TableView<String[]> currenciesTable;

    @FXML
    private TableColumn<String[], String> nameColumn;

    @FXML
    private TableColumn<String[], String> rateColumn;

    @FXML
    private TextField amountField;

    @FXML
    private ComboBox<String> fromCurrency;

    @FXML
    private ComboBox<String> toCurrency;

    @FXML
    private TextField resultField;

    @FXML
    private void initialize() {
       loadData();
    }

    @FXML
    public void refreshWindow() {
        loadData();
    }

    private void loadData() {
        // Set the welcome text
        String currentUser = UserSessionManager.getCurrentLoggerInUsername();
        String userType = UserSessionManager.getCurrentLoggerInUserType();
        welcomeText.setText("Hi %s %s, welcome to the Currency Exchange App!!".formatted(userType, currentUser));

        // load and display the exchange rates
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));
        rateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));
        loadCurrencyExchangeRates();

        // load the currencies names into the combo boxes
        ArrayList<String> currencies = QueryTool.currencyNameQuery();
        ObservableList<String> currencyList = FXCollections.observableArrayList(currencies);
        fromCurrency.setItems(currencyList);
        toCurrency.setItems(currencyList);
    }

    @FXML
    private void LoggedOut() {
        UserSessionManager.LoggedOut();
        switchScene("log-in.fxml", welcomeText.getScene());
    }

    @FXML
    private void handleConvert() {
        String from = fromCurrency.getValue();
        String to = toCurrency.getValue();
        String amount = amountField.getText();

        if (from == null || to == null || amount.isEmpty()) {
            alertTool("Error Description", "Please fill in all fields");
            return;
        }

        String result = CurrencyConverter.converter(from, to, amount);
        resultField.setText(result);
    }

    @FXML
    private void editWindow() {

        // open to the edit window
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("edit-window.fxml")));
            Parent root = loader.load();
            EditWindow editController = loader.getController();
            editController.setMainControllerAdmin(this);

            Stage editStage = new Stage();
            editController.setEditStage(editStage);

            editStage.setTitle("Edit Currency Exchange Rates");
            editStage.setScene(new javafx.scene.Scene(root, 350, 200));
            // block the main window
            editStage.initModality(Modality.WINDOW_MODAL);
            // set the owner of the edit window
            editStage.initOwner(welcomeText.getScene().getWindow());
            editStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCurrencyExchangeRates() {

        // Get the exchange rates
        ArrayList<String[]> data = QueryTool.currenciesQuery();

        ObservableList<String[]> currencyData = FXCollections.observableArrayList(data);
        currenciesTable.setItems(currencyData);
    }
}
