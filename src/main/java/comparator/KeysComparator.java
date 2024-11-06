package comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import logger.Logger;
import table.Table;
import table.foreignkey.ForeignKey;

public class KeysComparator {

    private Logger logger;

    public Logger getLogger() {
        return logger;
    }

     /**
     * Compares primary, unique, and foreign keys between two tables.
     */
    public void compareKeys(Table table1, Table table2) {
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
        logger = new Logger();


        List<ForeignKey> table1AdditionalForeignKeys = findAdditionalForeignKeys(foreignKeys1, foreignKeys2);
        List<ForeignKey> table2AdditionalForeignKeys = findAdditionalForeignKeys(foreignKeys2, foreignKeys1);

        logger.add("--- Diferencias entre las claves foráneas: ---");

        for (ForeignKey foreignKey1 : foreignKeys1) {
            for (ForeignKey foreignKey2 : foreignKeys2) {
                if (foreignKey1.getName().equals(foreignKey2.getName())) {
                    compareForeignKeysWithSameName(foreignKey1, foreignKey2);
                    break; 
                }
            }
        }
        logger.add("  Foreign Keys adicionales de la tabla 1" + " : " + table1AdditionalForeignKeys);
        logger.add("  Foreign Keys adicionales de la tabla 2" + " : " + table2AdditionalForeignKeys + "\n");
    }

    private List<ForeignKey> findAdditionalForeignKeys(List<ForeignKey> source, List<ForeignKey> target) {
        List<ForeignKey> additionalElements = new ArrayList<>();

        List<String> targetTableNames = target.stream()
                                            .map(ForeignKey::getName)
                                            .collect(Collectors.toList());

        for (ForeignKey element : source) {
            if (!targetTableNames.contains(element.getName())) {
                additionalElements.add(element);
            }
        }
        return additionalElements;
    }

    /**
     * 
     * @param foreignKey1
     * @param foreignKey2
     */
    private void compareForeignKeysWithSameName(ForeignKey foreignKey1, ForeignKey foreignKey2) {
        if (!foreignKey1.equals(foreignKey2)) {
            logger.add("\n  Las foreign keys con nombre : '" + foreignKey1.getName() + "' son diferentes." +
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

        logger.add("--- Diferencias entre las claves primarias: ---");

        logger.add("  Primary Keys adicionales de la tabla 1" + " : " + table1AdditionalPrimaryKeys);
        logger.add("  Primary Keys adicionales de la tabla 2" + " : " + table2AdditionalPrimaryKeys+"\n");
    }


    private void compareUniqueKeys(Table table1, Table table2) {
        List<String> uniqueKeys1 = table1.getUniqueKeys();
        List<String> uniqueKeys2 = table2.getUniqueKeys();
        List<String> table1AdditionalUniqueKeys = findAdditionalElements(uniqueKeys1, uniqueKeys2);
        List<String> table2AdditionalUniqueKeys = findAdditionalElements(uniqueKeys2, uniqueKeys1);

        logger.add("--- Diferencias entre las claves únicas: ---");

        logger.add("  Unique Keys adicionales de la tabla 1" + " : " + table1AdditionalUniqueKeys);
        logger.add("  Unique Keys adicionales de la tabla 2" + " : " + table2AdditionalUniqueKeys);
    }

    private List<String> findAdditionalElements(List<String> source, List<String> target) {
        List<String> additionalElements = new ArrayList<>();
        for (String element : source) {
            if (!target.contains(element)) {
                additionalElements.add(element);
            }
        }
        return additionalElements;
    }
}