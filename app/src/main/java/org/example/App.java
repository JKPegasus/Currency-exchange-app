package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
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

    private static void updateCurrencyDatabaseIfNeeded() {

        String dbLastUpdate = QueryTool.currencyDateQuery();
        OffsetDateTime odt = OffsetDateTime.parse(dbLastUpdate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSX"));
        LocalDate lastUpdate = odt.toLocalDate();
        LocalDate today = LocalDate.now();

        if (today.isAfter(lastUpdate)) {
            QueryTool.addCurrencyQuery(CurrencyDatabase.initializeCurrencies());
        }
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

        // update the currency database once a day
        updateCurrencyDatabaseIfNeeded();

        launch(args);
    }
}