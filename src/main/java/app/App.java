package app;

import java.sql.SQLException;

import comparator.TableComparator;
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
        // Debug: Print connection details
        System.out.println("Connecting to Database 1: " + databaseConnectionOne);
        System.out.println("Connecting to Database 2: " + databaseConnectionTwo);
        
        databaseOne = dbLoader.loadDatabaseSchema(databaseConnectionOne);
        databaseTwo = dbLoader.loadDatabaseSchema(databaseConnectionTwo);
        
        // Debug: Print loaded schemas
        System.out.println("Database 1 Schema: " + databaseOne);
        System.out.println("===================================");
        System.out.println("Database 2 Schema: " + databaseTwo);
        
        TableComparator tableComparator = new TableComparator(databaseOne, databaseTwo);
        System.out.println("============================================================");
        tableComparator.compareTables();
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