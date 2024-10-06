package dataloader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.Database;
import db.DatabaseConnection;
import table.Column;
import table.ColumnType;
import table.Table;

// Si tienen las mismas columnas, y si no es así, especificar las columnas
// adicionales que poseen.
// Si teniendo el mismo nombre de columna, las columnas son de
// distinto tipo, esto también se debe reportar.
// Diferencia de Triggers(nombre, momento de disparo, no código del
// cuerpo).
// Diferencias entre claves primarias, únicas y foráneas e Índices (no
// diferencia de nombres, sino de estructura).

public class DataLoader {
    public DataLoader() {
    }

    public Database getDatabase(DatabaseConnection databaseConnection) {
        Database resultDatabase = new Database();
        String schema = databaseConnection.getSchema();
        String[] types = { "TABLE" };

        try {
            Connection connection = databaseConnection.getInstance();
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet resultSetTables = metaData.getTables(null, schema, null, types);
            // TABLA
            while (resultSetTables.next()) {
                String tableName = resultSetTables.getString("TABLE_NAME");
                Table table = new Table(tableName);

                List<Column> columnLists = new ArrayList<>();
                ResultSet resultSetColumns = metaData.getColumns(null, schema, tableName, null);
                while (resultSetColumns.next()) {
                    String columnName = resultSetColumns.getString("COLUMN_NAME");
                    String columnType = resultSetColumns.getString("TYPE_NAME");
                    Column actualColumn = new Column(columnName, ColumnType.fromString(columnType));

                    columnLists.add(actualColumn);
                }
                table.setColumns(columnLists);

                // PRIMARY KEYS
                List<String> pkList = new ArrayList<>();
                ResultSet resultSetPrimaryKeys = metaData.getPrimaryKeys(null, schema, tableName);
                while (resultSetPrimaryKeys.next()) {
                    String pk = resultSetPrimaryKeys.getString("COLUMN_NAME");
                    pkList.add(pk);
                }

                // UNIQUE KEYS
                List<String> ukList = new ArrayList<>();
                ResultSet resultSetUniqueKeys = metaData.getIndexInfo(null, schema, tableName, true, false);
                while (resultSetUniqueKeys.next()) {
                    String uk = resultSetUniqueKeys.getString("COLUMN_NAME");
                    ukList.add(uk);
                }

                // FOREIGN KEYS
                // List<
                try (ResultSet resultSetForeignKeys = metaData.getImportedKeys(null, schema, tableName)) {
                    while (resultSetForeignKeys.next()) {
                        System.out.println("    Clave foránea: " + resultSetForeignKeys.getString("FKCOLUMN_NAME"));
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return new Database();
    }
}