package org.example;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    private void handleLogIn() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticateUser(username, password)) {
            System.out.println("User authenticated");
            // get the user type
            String userType = QueryTool.userTypeQuery(username);
            // Set the user session
            UserSessionManager.LoggedIn(username, userType);

            if (userType.equals("Admin")) {
                switchScene("mainAdmin.fxml", usernameField.getScene());
            } else if (userType.equals("User")) {
            switchScene("main.fxml", usernameField.getScene());
            }
        } else {
            alertTool("Error Description", "Invalid username or password");
        }
    }

    @FXML
    private void handleSignUp() {
        switchScene("sign-up.fxml", usernameField.getScene());
    }
}
