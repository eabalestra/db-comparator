package app;

import java.sql.SQLException;

import dataloader.DatabaseSchemaLoader;
import db.Database;
import db.DatabaseConnection;

public class App {
    private static final String POSTGRES_DRIVER = "org.postgresql.Driver";
    private DatabaseConnection databaseConnectionOne;
    private DatabaseConnection databaseConnectionTwo;
    private Database databaseOne;
    private Database databaseTwo;
    private DatabaseSchemaLoader dbLoader;

    public App() {
        loadDatabaseDriver();
        initializeConnections();
    }

    public void execute() throws SQLException {
        dbLoader = new DatabaseSchemaLoader();
        databaseOne = dbLoader.loadDatabaseSchema(databaseConnectionOne);
        databaseTwo = dbLoader.loadDatabaseSchema(databaseConnectionTwo);
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
}
