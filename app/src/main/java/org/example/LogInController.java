package org.example;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLException;
import static org.example.QueryTool.*;
import static org.example.Helpers.*;


public class LogInController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    // Authenticate user
    public static boolean authenticateUser(String username, String password){
        return authenticateUserQuery(username, password);
    }

    @FXML
    private void handleLogIn() throws SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticateUser(username, password)) {
            System.out.println("User authenticated");
            switchScene("main.fxml", usernameField.getScene());
        } else {
            alertTool("Error Description", "Invalid username or password");
        }
    }

    @FXML
    private void handleSignUp() throws IOException {
        switchScene("sign-up.fxml", usernameField.getScene());
    }
}
