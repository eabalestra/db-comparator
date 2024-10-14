package dataloader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.Database;
import db.DatabaseConnection;
import table.column.Column;
import table.column.ColumnType;
import table.Table;
import table.foreignkey.ForeignKey;
import table.trigger.Trigger;

public class DatabaseSchemaLoader {

    public DatabaseSchemaLoader() {
    }

    public Database loadDatabaseSchema(DatabaseConnection databaseConnection) {
        String schema = databaseConnection.getSchema();
        String[] types = { "TABLE" };
        Database resultDatabase = new Database();

        try {
            Connection connection = databaseConnection.getInstance();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSetTables = metaData.getTables(null, schema, null, types);

            while (resultSetTables.next()) {
                String tableName = resultSetTables.getString("TABLE_NAME");
                Table table = new Table(tableName);

                List<Column> columnLists = loadColumns(metaData, schema, tableName);
                table.setColumns(columnLists);

                List<String> pkList = loadPrimaryKeys(metaData, schema, tableName);
                table.setPrimaryKeys(pkList);

                List<String> ukList = loadUniqueKeys(metaData, schema, tableName);
                table.setUniqueKey(ukList);

                List<ForeignKey> fkList = loadForeignKeys(metaData, schema, tableName);
                table.setForeignKeys(fkList);

                List<Trigger> triggerList = new ArrayList<>();
                
                String selectQuery = "SELECT event_object_table, " +
                        "trigger_name, " +
                        "event_manipulation, " +
                        "action_statement, " +
                        "action_timing " +
                        "FROM information_schema.triggers " +
                        "WHERE event_object_table = ? " +
                        "ORDER BY event_object_table, event_manipulation;";

                PreparedStatement statement = connection.prepareStatement(selectQuery);
                statement.setString(1, tableName);
                ResultSet resultSetTriggers = statement.executeQuery();
                while (resultSetTriggers.next()) {
                    String triggerName = resultSetTriggers.getString("trigger_name");
                    String eventManipulation = resultSetTriggers.getString("event_manipulation");  // insert, update, delete
                    String actionTiming = resultSetTriggers.getString("action_timing");
                    
                    Trigger newTrigger = new Trigger(triggerName, tableName, actionTiming, eventManipulation);
                    triggerList.add(newTrigger);
                }
                table.setTriggers(triggerList);
        
                resultDatabase.addTable(table);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultDatabase;
    }

    private List<Trigger> loadTriggers(DatabaseMetaData metaData, String schema, String tableName) throws SQLException {
        List<Trigger> triggerList = new ArrayList<>();
        return triggerList;
    }

    private static List<ForeignKey> loadForeignKeys(DatabaseMetaData metaData, String schema, String tableName)
            throws SQLException {
        List<ForeignKey> fkList = new ArrayList<>();
        ResultSet resultSetForeignKeys = metaData.getImportedKeys(null, schema, tableName);
        while (resultSetForeignKeys.next()) {
            String fkName = resultSetForeignKeys.getString("FK_NAME");
            String fkColumn = resultSetForeignKeys.getString("FKCOLUMN_NAME");
            String fkReferencedTable = resultSetForeignKeys.getString("PKTABLE_NAME");
            String fkReferencedColumn = resultSetForeignKeys.getString("PKCOLUMN_NAME");

            ForeignKey actualForeignKey = new ForeignKey(fkName, fkColumn, fkReferencedTable, fkReferencedColumn);
            fkList.add(actualForeignKey);
        }
        return fkList;
    }

    private static List<String> loadUniqueKeys(DatabaseMetaData metaData, String schema, String tableName)
            throws SQLException {
        List<String> ukList = new ArrayList<>();
        ResultSet resultSetUniqueKeys = metaData.getIndexInfo(null, schema, tableName, true, false);
        while (resultSetUniqueKeys.next()) {
            String uk = resultSetUniqueKeys.getString("COLUMN_NAME");
            ukList.add(uk);
        }
        return ukList;
    }

    private List<String> loadPrimaryKeys(DatabaseMetaData metaData, String schema, String tableName)
            throws SQLException {
        List<String> pkList = new ArrayList<>();
        ResultSet resultSetPrimaryKeys = metaData.getPrimaryKeys(null, schema, tableName);
        while (resultSetPrimaryKeys.next()) {
            String pk = resultSetPrimaryKeys.getString("COLUMN_NAME");
            pkList.add(pk);
        }
        return pkList;
    }

    private List<Column> loadColumns(DatabaseMetaData metaData, String schema, String tableName) throws SQLException {
        List<Column> columnLists = new ArrayList<>();
        ResultSet resultSetColumns = metaData.getColumns(null, schema, tableName, null);
        while (resultSetColumns.next()) {
            String columnName = resultSetColumns.getString("COLUMN_NAME");
            String columnType = resultSetColumns.getString("TYPE_NAME");
            Column actualColumn = new Column(columnName, ColumnType.fromString(columnType));

            columnLists.add(actualColumn);
        }
        return columnLists;
    }
}