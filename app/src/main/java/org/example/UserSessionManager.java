package org.example;

public class UserSessionManager {

    private static String currentLoggerInUsername;
    private static String currentLoggerInUserType;

    private UserSessionManager() {
        // Prevent instantiation
    }

    public static void LoggedIn(String username, String userType) {
        currentLoggerInUsername = username;
        currentLoggerInUserType = userType;
    }

    public static String getCurrentLoggerInUserType() {
        return currentLoggerInUserType;
    }

    public static String getCurrentLoggerInUsername() {
        return currentLoggerInUsername;
    }

    public static void LoggedOut() {
        currentLoggerInUsername = null;
        currentLoggerInUserType = null;
    }
}
