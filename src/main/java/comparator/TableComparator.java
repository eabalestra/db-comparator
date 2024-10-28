package comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import db.Database;
import logger.Logger;
import table.Table;
import table.column.Column;
import table.foreignkey.ForeignKey;
import table.trigger.Trigger;

public class TableComparator {
    private Database db1;
    private Database db2;
    private Logger logger;
    private ColumnComparator columnComparator;
    private TriggerComparator triggerComparator;

    public TableComparator(Database db1, Database db2) {
        this.db1 = db1;
        this.db2 = db2;
        columnComparator = new ColumnComparator();
        triggerComparator = new TriggerComparator();
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
     * Identifies additional tables in each database and calls a method to compare tables with the same name.
     */
    public void compareTables() {
        List<Table> db1Tables = db1.getTables();
        List<Table> db2Tables = db2.getTables();
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

        System.out.println("Tablas adicionales de la base de datos 1: " + db1AdditionalTables);
        System.out.println("Tablas adicionales de la base de datos 2: " + db2AdditionalTables);
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
        //System.out.println("Diferencias entre las tablas " + table1 + " y " + table2);
        columnComparator.compareColumns(table1, table2); 
        triggerComparator.compareTriggers(table1, table2);
        // compareKeys(table1, table2);
        // compareIndexes(table1, table2); TODO
    }	

     /**
     * Compares primary, unique, and foreign keys between two tables.
     */
    private void compareKeys(Table table1, Table table2) {
        compareForeignKeys(table1, table2);
        comparePrimaryKeys(table1, table2); 
        compareUniqueKeys(table1, table2);
    }

    /**
     * 
     * @param table1
     * @param table2
     */
    private void compareForeignKeys(Table table1, Table table2) {
        List<ForeignKey> foreignKeys1 = table1.getForeignKeys();
        List<ForeignKey> foreignKeys2 = table2.getForeignKeys();

        List<ForeignKey> table1AdditionalForeignKeys = findAdditionalElements(foreignKeys1, foreignKeys2);
        List<ForeignKey> table2AdditionalForeignKeys = findAdditionalElements(foreignKeys2, foreignKeys1);

        // Compare foreign keys with the same name
        for (ForeignKey foreignKey1 : foreignKeys1) {
            for (ForeignKey foreignKey2 : foreignKeys2) {
                if (foreignKey1.getName() == foreignKey2.getName()) {
                    compareForeignKeysWithSameName(foreignKey1, foreignKey2);
                    break; 
                }
            }
        }

        System.out.println("Foreign Keys adicionales de la tabla " + table1 + " : " + table1AdditionalForeignKeys);
        System.out.println("Foreign Keys adicionales de la tabla " + table2 + " : " + table2AdditionalForeignKeys);
    }

    /**
     * 
     * @param foreignKey1
     * @param foreignKey2
     */
    private void compareForeignKeysWithSameName(ForeignKey foreignKey1, ForeignKey foreignKey2) {
        // TODO: habria que en el logger mostrar los atributos de cada una para mostrar las diferencias
        if (!foreignKey1.equals(foreignKey2)) {
            System.out.println("Las foreign keys con nombre : " + foreignKey1.getName() + " son diferentes." + 
            " Foreign key de la tabla 1: " + foreignKey1 + " Foreign key de la tabla 2: " + foreignKey2);
        }
    }

    /**
     * 
     * @param table1
     * @param table2
     */
    private void comparePrimaryKeys(Table table1, Table table2) {
        List<String> primaryKeys1 = table1.getPrimaryKeys();
        List<String> primaryKeys2 = table2.getPrimaryKeys();
        List<String> table1AdditionalPrimaryKeys = findAdditionalElements(primaryKeys1, primaryKeys2);
        List<String> table2AdditionalPrimaryKeys = findAdditionalElements(primaryKeys2, primaryKeys1);

        if (table1AdditionalPrimaryKeys.isEmpty() && table2AdditionalPrimaryKeys.isEmpty()) {
            System.out.println("Ambas tablas tienen las mismas primary keys");
        } else {
            System.out.println("Primary Keys adicionales de la tabla " + table1 + " : " + table1AdditionalPrimaryKeys);
            System.out.println("Primary Keys adicionales de la tabla " + table2 + " : " + table2AdditionalPrimaryKeys);
        }
    }


    private void compareUniqueKeys(Table table1, Table table2) {
        List<String> uniqueKeys1 = table1.getUniqueKeys();
        List<String> uniqueKeys2 = table2.getUniqueKeys();
        List<String> table1AdditionalUniqueKeys = findAdditionalElements(null, null);
        List<String> table2AdditionalUniqueKeys = new ArrayList<>();

        for (String uniqueKey1 : uniqueKeys1) {
            boolean isAdditional = true;
            for (String uniqueKey2 : uniqueKeys2) {
                if (uniqueKey2 == uniqueKey1) {
                    isAdditional = false;
                    break; 
                }
            }
            if (isAdditional) {
                table1AdditionalUniqueKeys.add(uniqueKey1);
            }
        }

        System.out.println("Unique Keys adicionales de la tabla " + table1 + " : " + table1AdditionalUniqueKeys);
        
        for (String uniqueKey2 : uniqueKeys2) {
            boolean isAdditional = true;
            for (String uniqueKey1 : uniqueKeys1) {
                if (uniqueKey1 == uniqueKey2) {
                    isAdditional = false;
                    break;
                }
            }
            if (isAdditional) {
                table2AdditionalUniqueKeys.add(uniqueKey2);
            }
        }

        System.out.println("Unique Keys adicionales de la tabla " + table2 + " : " + table2AdditionalUniqueKeys);
    }

    private <T> List<T> findAdditionalElements(List<T> source, List<T> target) {
        List<T> additionalElements = new ArrayList<>();
        for (T element : source) {
            if (!target.contains(element)) {
                additionalElements.add(element);
            }
        }
        return additionalElements;
    }
} 