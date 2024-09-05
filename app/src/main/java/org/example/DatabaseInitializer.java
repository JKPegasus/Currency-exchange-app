package org.example;

import java.sql.*;


public class DatabaseInitializer {
    private static final String URL = "jdbc:postgresql://localhost:5432/user_info";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";

    private static final String CREATE_USER_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                username VARCHAR(50) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL,
                user_type VARCHAR(50) DEFAULT 'User',
                created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
            )
            """;

    private static final String CREATE_CURRENCIES_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS currencies (
                id SERIAL PRIMARY KEY,
                currency_name VARCHAR(50) UNIQUE NOT NULL,
                currency_rate VARCHAR(50) NOT NULL,
                currency_date TIMESTAMP WITH TIME ZONE
            )
            """;

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(CREATE_USER_TABLE_SQL);
            stmt.execute(CREATE_CURRENCIES_TABLE_SQL);

        } catch (SQLException e) {
            System.out.println("Error while initializing database" + e.getMessage());
            e.printStackTrace();
        }
    }


    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found.", e);
        }
    }

    public static void main(String[] args) {

        // Check if the database is connected successfully
        try (Connection connection = getConnection()) {
            System.out.println("Success in connecting to the PostgreSQL database！");
        } catch (SQLException e) {
            System.out.println("Error occur when connect to the database" + e.getMessage());
        }

        // Initialize the database
        initializeDatabase();
    }

}

