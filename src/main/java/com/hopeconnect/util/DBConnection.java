package com.hopeconnect.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection.java
 * Utility class for managing MySQL database connections.
 * Provides a centralized connection management using JDBC DriverManager.
 *
 * Database: hopeconnect_db
 * Database credentials are hardcoded for development (update for production).
 * Note: Connection pooling is not implemented - simple DriverManager is used.
 */
public class DBConnection {

    // Database configuration constants
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/hopeconnect_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    /**
     * Static initialization block to load MySQL JDBC driver.
     * Throws RuntimeException if driver cannot be loaded.
     */
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found: " + e.getMessage());
        }
    }

    /**
     * Establishes and returns a new MySQL database connection.
     *
     * @return Connection object connected to hopeconnect_db
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Closes a database connection safely.
     * Suppresses any exceptions during closure.
     *
     * @param connection the Connection object to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }

    /**
     * Tests the database connection.
     * Useful for verifying database setup and credentials.
     *
     * @return true if connection is successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Database connection successful!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
        }
        return false;
    }
}
