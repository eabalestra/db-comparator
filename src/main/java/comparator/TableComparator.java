package comparator;

import java.util.ArrayList;
import java.util.List;
import db.Database;
import logger.Logger;
import table.Table;
import table.column.Column;

public class TableComparator {
    private Database db1;
    private Database db2;
    private Logger logger;

    public TableComparator(Database db1, Database db2) {
        this.db1 = db1;
        this.db2 = db2;
    }

    public void setDb1(Database db1) {
        this.db1 = db1;
    }

    public Database getDb1() {
        return db1;
    }

    public void setDb2(Database db2) {
        this.db2 = db2;
    }

    public Database getDb2() {
        return db2;
    }

    public void compareTables() {
        List<Table> db1Tables = db1.getTables();
        List<Table> db2Tables = db2.getTables();
        
        List<String> db1TableNames = getListOfTableNames(db1Tables);
        List<String> db2TableNames = getListOfTableNames(db2Tables);

        List<Table> db1AdditionalTables = new ArrayList<>();
        List<Table> db2AdditionalTables = new ArrayList<>();

        for (String tableName : db1TableNames) {
            if (db2TableNames.contains(tableName)) {
                compareTablesWithSameName(findTableByName(tableName, db1AdditionalTables), findTableByName(tableName, db2AdditionalTables));
            }
        }

        /* las que tengan mismo nombre, compararlas
        las que no coinciden en nombre, agregarlas a las diferencias. */
    }

    private void compareTablesWithSameName(Table table1, Table table2) {
        compareColumns(table1, table2);
        // compareTriggers();
        // compareKeys();
    }

    private void compareColumns(Table table1, Table table2) {
        List<Column> table1Columns = table1.getColumns();
        List<Column> table2Columns = table2.getColumns();

        
    }

    private List<String> getListOfTableNames(List<Table> tables) {
        List<String> tablesNames = new ArrayList<>();
        for (Table table : tables) {
            tablesNames.add(table.getName());
        }
        return tablesNames;
    }

    private Table findTableByName(String name, List<Table> tables) {
        for (Table table : tables) {
            if (table.getName() == name) {
                return table;
            }
        }
        return null;
    }

}