package org.example;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import static org.example.QueryTool.*;
import static org.example.Helpers.*;

public class SignUpController {

    private static final String INSERT_USER_SQL = "INSERT INTO users (username, password) VALUES (?, ?)";

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ChoiceBox<String> userTypeChoiceBox;

    @FXML
    public void initialize() {
        // Set the default value of the choice box
        userTypeChoiceBox.setItems(FXCollections.observableArrayList("Admin", "User"));
        userTypeChoiceBox.setValue("User");
    }

    @FXML
    private void handleSignUp() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String userType = userTypeChoiceBox.getValue();

        // Check if the username already exists
        if (checkUserExistsQuery(username) > 0) {
            alertTool("Error Description", "Username already exists");
            return;
        }

        // Check if the password and confirm password match
        if (password.equals(confirmPassword)) {
            System.out.println("Username: " + username + " Password: " + password);
            addUser(username, password, userType);
            switchScene("main.fxml", usernameField.getScene());
        } else {
            alertTool("Error Description", "Passwords do not match");
        }
    }

    @FXML
    private void handleBackToLogIn() throws IOException {
        switchScene("log-in.fxml", usernameField.getScene());
    }

    public static void addUser(String username, String password, String userType) {
        if (addUserQuery(username, password, userType) > 0) {
            System.out.println("User added successfully");
        } else {
            System.out.println("Error while adding user");
        }
    }
}
