package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://DESKTOP-32DNOAQ:3306/my_datdbase?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "Sizofff1984";
    private static final String PASSWORD = "V!ctoria19891906"; // Change to your MySQL password

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        } catch (SQLException e) {
            throw new RuntimeException("DB connection error. Verify server/credentials/database", e);
        }
    }
}