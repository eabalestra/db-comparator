package db;

import properties.PropertiesLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private String url = "";
    private String username = "";
    private String password = "";
    private String schema = "";
    private Connection connection = null;

    public DatabaseConnection(int databaseNumber) {
        PropertiesLoader loader = new PropertiesLoader("database.properties");
        String dbPrefix = "db" + databaseNumber;
        url = loader.getProperty(dbPrefix + ".url");
        username = loader.getProperty(dbPrefix + ".username");
        password = loader.getProperty(dbPrefix + ".password");
        schema = loader.getProperty(dbPrefix + ".schema");
        System.out.println("Database Connection : " + dbPrefix);
        System.out.println("URL : " + url);
    }

    public Connection getInstance() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    public String getSchema() {
        return schema;
    }
}