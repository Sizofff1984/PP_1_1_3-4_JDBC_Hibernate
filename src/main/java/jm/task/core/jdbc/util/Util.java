package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/my_datdbase?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "Sizofff1984";
    private static final String PASSWORD = "V!ctoria19891906";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database. Please check:\n" +
                    "1. Is MySQL server running?\n" +
                    "2. Are the connection details correct in Util.java?\n" +
                    "3. Does the database 'testdb' exist?", e);
        }
    }
}