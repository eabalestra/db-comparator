package dataloader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.Database;
import db.DatabaseConnection;
import storedprocedure.StoredProcedure;
import storedprocedure.column.StoredProcedureColumn;
import storedprocedure.column.StoredProcedureColumnType;
import table.column.Column;
import table.column.ColumnType;
import table.Table;
import table.foreignkey.ForeignKey;
import table.index.Index;
import table.index.IndexType;
import table.trigger.Trigger;

public class DatabaseSchemaLoader {

    public DatabaseSchemaLoader() {
    }

    public Database loadDatabaseSchema(DatabaseConnection databaseConnection) {
        Database database = new Database();
        String schema = databaseConnection.getSchema();
        String[] types = { "TABLE" };

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

                List<Trigger> triggerList = loadTriggers(connection, tableName);
                table.setTriggers(triggerList);

                List<Index> indexList = loadIndexes(metaData, schema, tableName);
                table.setIndexes(indexList);

                database.addTable(table);
            }

            List<StoredProcedure> proceduresList = getStoredProcedures(metaData, schema);
            database.setStoredProcedures(proceduresList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return database;
    }

    private static List<StoredProcedure> getStoredProcedures(DatabaseMetaData metaData, String schema) throws SQLException {
        List<StoredProcedure> proceduresList = new ArrayList<>();
        ResultSet resultSet = metaData.getProcedureColumns(null, schema, null, null);

        while (resultSet.next()) {
            String procedureName = resultSet.getString("PROCEDURE_NAME");
            String columnName = resultSet.getString("COLUMN_NAME");
            String columnType = resultSet.getString("TYPE_NAME");
            String columnOrder = resultSet.getString("ORDINAL_POSITION");
            int columnTypeCode = resultSet.getInt("COLUMN_TYPE");
            ColumnType type = ColumnType.fromString(columnType);

            StoredProcedureColumnType parameterType = getStoredProcedureColumnType(columnTypeCode);

            StoredProcedure existingProcedure = proceduresList.stream()
                    .filter(proc -> proc.getName().equals(procedureName))
                    .findFirst()
                    .orElse(null);

            if (existingProcedure == null) {
                StoredProcedure newProcedure = new StoredProcedure(procedureName);
                proceduresList.add(newProcedure);
            } else {
                StoredProcedureColumn storedProcedureColumn = new StoredProcedureColumn(parameterType, columnName,
                        type, columnOrder);
                existingProcedure.addColumn(storedProcedureColumn);
            }
        }
        return proceduresList;
    }


    private List<Trigger> loadTriggers(Connection connection, String tableName) throws SQLException {
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
            String eventManipulation = resultSetTriggers.getString("event_manipulation"); // insert, update, delete
            String actionTiming = resultSetTriggers.getString("action_timing");

            Trigger newTrigger = new Trigger(triggerName, tableName, actionTiming, eventManipulation);
            triggerList.add(newTrigger);
        }
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

    private List<Index> loadIndexes(DatabaseMetaData metaData, String schema, String tableName) throws SQLException {
        List<Index> indexList = new ArrayList<>();
        ResultSet resultSetIndexes = metaData.getIndexInfo(null, schema, tableName, false, false);
    
        // Map to keep track of index objects by name for grouping columns
        Map<String, Index> indexMap = new HashMap<>();
    
        while (resultSetIndexes.next()) {
            String indexName = resultSetIndexes.getString("INDEX_NAME");
            String columnName = resultSetIndexes.getString("COLUMN_NAME");
            boolean isAsc = !resultSetIndexes.getString("ASC_OR_DESC").equals("D");
            boolean isUnique = !resultSetIndexes.getBoolean("NON_UNIQUE");
    
            Index index = indexMap.get(indexName);
            if (index == null) {
                IndexType indexType = isUnique ? IndexType.UNIQUE : IndexType.NORMAL;
                index = new Index(indexName, tableName);
                index.setType(indexType);
                index.getFields().put(columnName, isAsc);
                indexMap.put(indexName, index); 
            } else {
                index.getFields().put(columnName, isAsc);
            }
    
        }
        indexList.addAll(indexMap.values());
    
        return indexList;
    }

    
    private static StoredProcedureColumnType getStoredProcedureColumnType(int columnTypeCode) {
        StoredProcedureColumnType parameterType;
        switch (columnTypeCode) {
            case DatabaseMetaData.procedureColumnIn:
                parameterType = StoredProcedureColumnType.IN;
                break;
            case DatabaseMetaData.procedureColumnOut:
                parameterType = StoredProcedureColumnType.OUT;
                break;
            case DatabaseMetaData.procedureColumnInOut:
                parameterType = StoredProcedureColumnType.INOUT;
                break;
            case DatabaseMetaData.procedureColumnReturn:
                parameterType = StoredProcedureColumnType.RETURN;
                break;
            default:
                parameterType = StoredProcedureColumnType.UNKNOWN;
        }
        return parameterType;
    }
}
