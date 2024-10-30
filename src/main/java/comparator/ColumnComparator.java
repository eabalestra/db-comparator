package comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import logger.Logger;
import table.Table;
import table.column.Column;

public class ColumnComparator {

    private Logger logger;

    public Logger getLogger() {
        return logger;
    }

    /**
     * Compares columns between two tables, identifying additional columns in each.
     * For columns with the same name, it checks if they have the same type.
     */
    public void compareColumns(Table table1, Table table2) {
        List<Column> columns1 = table1.getColumns();
        List<Column> columns2 = table2.getColumns();
        logger = new Logger();
        
        List<Column> table1AdditionalColumns = findAdditionalColumns(columns1, columns2);
        List<Column> table2AdditionalColumns = findAdditionalColumns(columns2, columns1);
        
        System.out.println();
        System.out.println("===============================================================================================================");
        System.out.println("Comparando las tablas: " + table1.getName());
        logger.add("Comparando las tablas: " + table1.getName());
        System.out.println();
        
        // Compare columns with same name
        for (Column column1 : columns1) {
            for (Column column2 : columns2) {
                if (column1.getName().equals(column2.getName())) {
                    compareColumnsWithSameName(column1, column2);
                    break; 
                }
            }
        }
        logger.add("Columnas adicionales de la tabla 1" + " : " + table1AdditionalColumns);
        logger.add("Columnas adicionales de la tabla 2" + " : " + table2AdditionalColumns);
        System.out.println("Columnas adicionales de la tabla 1" + " : " + table1AdditionalColumns);
        System.out.println("Columnas adicionales de la tabla 2" + " : " + table2AdditionalColumns);
    }

    /**
     * Compares two columns with the same name.
     * 
     * @param column1 the column from `table1` to be compared.
     * @param column2 the column from `table2` to be compared.
     */
    private void compareColumnsWithSameName(Column column1, Column column2) {
        if (!column1.equals(column2)) {
            logger.add("Las columnas " + column1.getName() + " tienen tipos diferentes: " + column1.getType() + " y " + column2.getType());
            System.out.println("Las columnas " + column1.getName() + " tienen tipos diferentes: " + column1.getType() + " y " + column2.getType());
        }
    }

    /**
     * 
     * @param source
     * @param target
     * @return
     */
    private List<Column> findAdditionalColumns(List<Column> source, List<Column> target) {
        List<Column> additionalElements = new ArrayList<>();

        // Extract the names of columns in the target list for comparison
        List<String> targetColumnNames = target.stream()
                                            .map(Column::getName)
                                            .collect(Collectors.toList());

        for (Column element : source) {
            if (!targetColumnNames.contains(element.getName())) {
                additionalElements.add(element);
            }
        }
        return additionalElements;
    }
}