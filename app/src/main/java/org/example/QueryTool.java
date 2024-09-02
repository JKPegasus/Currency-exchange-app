package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.DatabaseInitializer.*;

public class QueryTool {


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

    public static int addUserQuery(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while adding user: " + e.getMessage());
            e.printStackTrace();
        }
        // Return 0 if the user is not added
        return 0;
    }

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
}
