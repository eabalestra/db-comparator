package comparator;

import java.util.ArrayList;
import java.util.List;
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

     /**
     * Compares tables between two databases, `db1` and `db2`.
     * Identifies additional tables in each database and calls a method to compare tables with the same name.
     */
    public void compareTables() {
        List<Table> db1Tables = db1.getTables();
        List<Table> db2Tables = db2.getTables();
        List<Table> db1AdditionalTables = new ArrayList<>();
        List<Table> db2AdditionalTables = new ArrayList<>();

        // Add additional tables of db1Tables and compare tables with same name
        for (Table table1 : db1Tables) {
            boolean isAdditional = true;
            for (Table table2 : db2Tables) {
                if (table1.getName() == table2.getName()) {
                    compareTablesWithSameName(table1, table2);
                    isAdditional = false;
                    break; 
                }
            }
            if (isAdditional) {
                db1AdditionalTables.add(table1);
            }
        }

        System.out.println("Tablas adicionales de la base de datos 1: " + db1AdditionalTables);
        
        // Add additional tables of db2Tables
        for (Table table2 : db2Tables) {
            boolean isAdditional = true;
            for (Table table1 : db1Tables) {
                if (table2.getName().equals(table1.getName())) {
                    isAdditional = false;
                    break;
                }
            }
            if (isAdditional) {
                db2AdditionalTables.add(table2);
            }
        }

        System.out.println("Tablas adicionales de la base de datos 2: " + db2AdditionalTables);
    }

    /**
     * Compares columns, triggers, and keys between two tables with the same name.
     * 
     * @param table1 the table from `db1` to be compared.
     * @param table2 the table from `db2` to be compared.
     */
    private void compareTablesWithSameName(Table table1, Table table2) {
        System.out.println("Diferencias entre las tablas " + table1 + " y " + table2);
        compareColumns(table1, table2); 
        compareTriggers(table1, table2);
        compareKeys(table1, table2);
        // compareIndexes(table1, table2); TODO
    }

    /**
     * Compares columns between two tables, identifying additional columns in each.
     * For columns with the same name, it checks if they have the same type.
     */
    private void compareColumns(Table table1, Table table2) {
        List<Column> columns1 = table1.getColumns();
        List<Column> columns2 = table2.getColumns();
        
        List<Column> table1AdditionalColumns = findAdditionalElements(columns1, columns2);
        List<Column> table2AdditionalColumns = findAdditionalElements(columns2, columns1);

        // Compare columns with same name
        for (Column column1 : columns1) {
            for (Column column2 : columns2) {
                if (column1.getName() == column2.getName()) {
                    compareColumnsWithSameName(column1, column2);
                    break; 
                }
            }
        }

        System.out.println("Columnas adicionales de la tabla " + table1 + " : " + table1AdditionalColumns);
        System.out.println("Columnas adicionales de la tabla " + table2 + " : " + table2AdditionalColumns);
    }

    /**
     * Compares two columns with the same name.
     * 
     * @param column1 the column from `table1` to be compared.
     * @param column2 the column from `table2` to be compared.
     */
    private void compareColumnsWithSameName(Column column1, Column column2) {
        if (!column1.equals(column2)) {
            System.out.println("Las columnas tienen tipos diferentes: " + column1.getType() + " y " + column2.getType());
        }
    }

    /**
     * Compares triggers between two tables, identifying additional triggers in each.
     * For triggers with the same name, it checks if they differ in timing or event.
     */
    private void compareTriggers(Table table1, Table table2) {
        List<Trigger> triggers1 = table1.getTriggers();
        List<Trigger> triggers2 = table2.getTriggers();
        List<Trigger> table1AdditionalTriggers = findAdditionalElements(triggers1, triggers2);
        List<Trigger> table2AdditionalTriggers = findAdditionalElements(triggers2, triggers1);

        // Compare triggers with the same name
        for (Trigger trigger1 : triggers1) {
            for (Trigger trigger2 : triggers2) {
                if (trigger1.getName() == trigger2.getName()) {
                    compareTriggersWithSameName(trigger1, trigger2);
                    break; 
                }
            }
        }

        System.out.println("Triggers adicionales de la tabla " + table1 + " : " + table1AdditionalTriggers);
        System.out.println("Triggers adicionales de la tabla " + table2 + " : " + table2AdditionalTriggers);
    }

    /**
     * Compares two triggers with the same name.
     * 
     * @param trigger1 the trigger from `table1` to be compared.
     * @param trigger2 the trigger from `table2` to be compared.
     */
    private void compareTriggersWithSameName(Trigger trigger1, Trigger trigger2) {
        if (!trigger1.equals(trigger2)) {
            System.out.println("Los triggers tienen diferentes momentos de disparos: " + trigger1.getactionTrigger() + " " +
            trigger1.getEventManipulation() + ", " + trigger2.getactionTrigger() + " " + trigger2.getEventManipulation());
        }
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