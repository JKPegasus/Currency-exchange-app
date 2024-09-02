package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class Helpers {

    // Method to display an alert box
    public static void alertTool(String title, String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }

    // Method to switch scenes
    public static void switchScene(String fxmlFile, Scene scene){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(MainController.class.getResource(fxmlFile)));
            Stage stage = (Stage) scene.getWindow();
            stage.setScene(new Scene(root, 300, 275));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
