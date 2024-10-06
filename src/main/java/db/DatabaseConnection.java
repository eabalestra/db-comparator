package db;

import properties.PropertiesLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    static String url = "";
    static String username = "";
    static String password = "";
    static Connection connection = null;

    public DatabaseConnection() {
        PropertiesLoader loader = new PropertiesLoader("database.properties");
        url = loader.getProperty("db.url");
        username = loader.getProperty("db.username");
        password = loader.getProperty("db.password");
    }

    public Connection getInstance() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }
}