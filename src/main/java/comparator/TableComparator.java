package comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import db.Database;
import logger.Logger;
import table.Table;

public class TableComparator {
    private Database db1;
    private Database db2;
    private Logger logger;
    private Logger loggerDb1;
    private Logger loggerDb2;
    private ColumnComparator columnComparator;
    private TriggerComparator triggerComparator;
    private KeysComparator keysComparator;
    private IndexComparator indexComparator;

    public TableComparator(Database db1, Database db2) {
        this.db1 = db1;
        this.db2 = db2;
        columnComparator = new ColumnComparator();
        triggerComparator = new TriggerComparator();
        keysComparator = new KeysComparator();
        indexComparator = new IndexComparator();
        logger = new Logger();
        loggerDb1 = new Logger();
        loggerDb2 = new Logger();
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

    /**
     * Compares tables between two databases, `db1` and `db2`.
     * Identifies additional tables in each database and calls a method to compare
     * tables with the same name.
     */
    public void compareTables() {
        List<Table> db1Tables = db1.getTables();
        List<Table> db2Tables = db2.getTables();
        loggerDb1.add(db1.toString());
        loggerDb2.add(db2.toString());
        List<Table> db1AdditionalTables = findAdditionalTables(db1Tables, db2Tables);
        List<Table> db2AdditionalTables = findAdditionalTables(db2Tables, db1Tables);

        // Compare tables with same name
        for (Table table1 : db1Tables) {
            for (Table table2 : db2Tables) {
                if (table1.getName().equals(table2.getName())) {
                    compareTablesWithSameName(table1, table2);
                    break;
                }
            }
        }

        logger.add("Tablas adicionales de la base de datos 1: " + db1AdditionalTables);
        logger.add("\nTablas adicionales de la base de datos 2: " + db2AdditionalTables);
        //
        loggerDb1.writeFile("db1");
        loggerDb2.writeFile("db2");
        logger.writeFile("file");

    }

    private List<Table> findAdditionalTables(List<Table> source, List<Table> target) {
        List<Table> additionalElements = new ArrayList<>();

        // Extract the names of tables in the target list for comparison
        List<String> targetTableNames = target.stream()
                .map(Table::getName)
                .collect(Collectors.toList());

        for (Table element : source) {
            // Compare based on table names
            if (!targetTableNames.contains(element.getName())) {
                additionalElements.add(element);
            }
        }
        return additionalElements;
    }

    /**
     * Compares columns, triggers, and keys between two tables with the same name.
     * 
     * @param table1 the table from `db1` to be compared.
     * @param table2 the table from `db2` to be compared.
     */
    private void compareTablesWithSameName(Table table1, Table table2) {
        logger.add("\n============== Diferencias entre las tablas con nombre: '" + table1.getName() + "' ============== \n");

        columnComparator.compareColumns(table1, table2);
        logger.copy(columnComparator.getLogger());

        triggerComparator.compareTriggers(table1, table2);
        logger.copy(triggerComparator.getLogger());

        keysComparator.compareKeys(table1, table2);
        logger.copy(keysComparator.getLogger());

        indexComparator.compareIndexes(table1, table2);
        logger.copy(indexComparator.getLogger());
    }
}