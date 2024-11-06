package app;

import java.sql.SQLException;

import comparator.TableComparator;
import comparator.storedprocedure.StoredProcedureComparator;
import dataloader.DatabaseSchemaLoader;
import db.Database;
import db.DatabaseConnection;
import logger.Logger;

public class App {
    private static final String POSTGRES_DRIVER = "org.postgresql.Driver";
    private DatabaseConnection databaseConnectionOne;
    private DatabaseConnection databaseConnectionTwo;

    public App() {
        loadDatabaseDriver();
        initializeConnections();
    }

    public void execute() throws SQLException {
        DatabaseSchemaLoader dbLoader = new DatabaseSchemaLoader();

        System.out.println("Connecting to Database 1: " + databaseConnectionOne);
        System.out.println("Connecting to Database 2: " + databaseConnectionTwo);

        Database databaseOne = dbLoader.loadDatabaseSchema(databaseConnectionOne);
        databaseOne.setSchema(databaseConnectionOne.getSchema());
        Database databaseTwo = dbLoader.loadDatabaseSchema(databaseConnectionTwo);
        databaseTwo.setSchema(databaseConnectionTwo.getSchema());
        
        TableComparator tableComparator = new TableComparator(databaseOne, databaseTwo);
        tableComparator.compareTables();
        StoredProcedureComparator storedProcedureComparator = new StoredProcedureComparator(databaseOne, databaseTwo, new Logger());
        storedProcedureComparator.compareStoredProcedures();
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