package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Objects;

import static org.example.DatabaseInitializer.*;

public class QueryTool {


    // Check if the user exists
    public static int checkUserExistsQuery(String username) {
        String sql = "SELECT COUNT(username) AS user_count FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return 0 if the user does not exist
        return 0;
    }


    // Add user
    public static int addUserQuery(String username, String password, String userType) {
        String sql = "INSERT INTO users (username, password, user_type) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, userType);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while adding user: " + e.getMessage());
            e.printStackTrace();
        }
        // Return 0 if the user is not added
        return 0;
    }


    // Authenticate user
    public static boolean authenticateUserQuery(String username, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("password").equals(password);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return null if the user does not exist
        return false;
    }


    // add currency details to the database
    public static void addCurrencyQuery(ArrayList<ArrayList<String>> currencies) {
        String sql = "INSERT INTO currencies(currency_name, currency_rate, currency_date) VALUES (?, ?, ?)" +
                     "ON CONFLICT (currency_name) DO UPDATE SET currency_rate = ?, currency_date = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (ArrayList<String> currency : currencies) {
                pstmt.setString(1, currency.get(0));
                pstmt.setString(2, currency.get(1));
                pstmt.setObject(3, OffsetDateTime.now());

                //update part
                pstmt.setString(4, currency.get(1));
                pstmt.setObject(5, OffsetDateTime.now());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // query the userType from the database
    public static String userTypeQuery(String username) {
        String sql = "SELECT user_type FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("user_type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return null if the user does not exist
        return null;
    }


    // query six specified currencies data from the database
    public static ArrayList<String[]> currenciesQuery() {
        String sql = "SELECT currency_name, currency_rate FROM currencies " +
                "WHERE currency_name IN ('CAD', 'JPY', 'USD', 'EUR', 'GBP', 'CNY')";
        ArrayList<String[]> data = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String currencyName = rs.getString("currency_name");
                String currencyRate = rs.getString("currency_rate");

                data.add(new String[]{currencyName, currencyRate});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }


    // query the currency rate from the database
    public static String currencyRateQuery(String currencyName) {
        String sql = "SELECT currency_rate FROM currencies WHERE currency_name = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, currencyName);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("currency_rate");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Return null if the currency does not exist
        return null;
    }



    // query the currency name from the database
    public static ArrayList<String> currencyNameQuery() {
        String sql = "SELECT currency_name FROM currencies ORDER BY id";
        ArrayList<String> data = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String currencyName = rs.getString("currency_name");

                data.add(currencyName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }


    // update the currency rate in the database
    public static void updateCurrencyRateQuery(String currencyName, String rate) {
        String sql = "UPDATE currencies SET currency_rate = ?, currency_date = ? WHERE currency_name = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, rate);
            pstmt.setObject(2, OffsetDateTime.now());
            pstmt.setString(3, currencyName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // query the currency date from the database
    public static String currencyDateQuery() {
        String sql = "SELECT currency_date FROM currencies LIMIT 1";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("currency_date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        for (String[] currency : Objects.requireNonNull(currenciesQuery())) {
            System.out.println(Arrays.toString(currency));
        }
    }
}
