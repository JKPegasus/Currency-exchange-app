package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.*;

public class EditWindow {

    @FXML
    private ComboBox<String> currencyToEdit;

    @FXML
    private TextField newRate;

    private MainControllerAdmin mainControllerAdmin;
    private Stage editStage;

    public void setMainControllerAdmin(MainControllerAdmin mainControllerAdmin) {
        this.mainControllerAdmin = mainControllerAdmin;
    }

    public void setEditStage(Stage stage) {
        this.editStage = stage;
    }

    @FXML
    private void initialize() {
        // load the currencies names into the combo boxes
        ArrayList<String> currencies = QueryTool.currencyNameQuery();
        ObservableList<String> currencyList = FXCollections.observableArrayList(currencies);
        currencyToEdit.setItems(currencyList);
    }

    @FXML
    private void updateRate() {
        String currencyName = currencyToEdit.getValue();
        String rate = String.format("%.3f", Double.parseDouble(newRate.getText()));
        QueryTool.updateCurrencyRateQuery(currencyName, rate);

        // close the edit window
        if (editStage != null) {
            editStage.close();
        }

        // refresh the main window
        mainControllerAdmin.refreshWindow();
    }
}
