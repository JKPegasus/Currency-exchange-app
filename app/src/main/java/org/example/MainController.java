package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {

    @FXML
    public Label welcomeText;

    @FXML
    private void initialize() {
        welcomeText.setText("Welcome to the application!");
    }
}
