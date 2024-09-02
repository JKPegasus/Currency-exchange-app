package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import static org.example.DatabaseInitializer.*;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(("log-in.fxml"))));
        primaryStage.setTitle("Currency Exchange Application");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {

        // check if the database connection is successful
        try (Connection conn = getConnection()) {
            System.out.println("Successfully connected to the PostgreSQL database!");
        } catch (SQLException e) {
            System.out.println("Error occurred while connecting to the database" + e.getMessage());
        }

        // initialize the database
        initializeDatabase();

        launch(args);
    }
}