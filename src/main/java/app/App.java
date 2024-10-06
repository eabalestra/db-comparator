package app;

import java.sql.SQLException;

import dataloader.DataLoader;
import db.Database;
import db.DatabaseConnection;

public class App {
    private static final String POSTGRES_DRIVER = "org.postgresql.Driver";
    private DatabaseConnection databaseConnectionOne;
    private DatabaseConnection databaseConnectionTwo;
    private Database databaseOne;
    private Database databaseTwo;
    private DataLoader dbLoader;

    public App() {
        loadDatabaseDriver();
        initializeConnections();
    }

    public void execute() throws SQLException {
        dbLoader = new DataLoader();
        databaseOne = dbLoader.getDatabase(databaseConnectionOne);
        databaseTwo = dbLoader.getDatabase(databaseConnectionTwo);
    }

    private void loadDatabaseDriver() {
        try {
            Class.forName(POSTGRES_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading PostgreSQL driver: " + e.getMessage());
            System.exit(1);
        }
    }

    private void initializeConnections() {
        databaseConnectionOne = new DatabaseConnection(1);
        databaseConnectionTwo = new DatabaseConnection(2);
    }

    public static void main(String[] args) {
        App app = new App();
        try {
            app.execute();
        } catch (SQLException e) {
            System.err.println("Error executing application: " + e.getMessage());
        }
    }
}
