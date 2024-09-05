package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import static org.example.Helpers.*;

public class MainController {

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

    private void loadCurrencyExchangeRates() {

        // Get the exchange rates
        ArrayList<String[]> data = QueryTool.currenciesQuery();

        ObservableList<String[]> currencyData = FXCollections.observableArrayList(data);
        currenciesTable.setItems(currencyData);
    }
}
