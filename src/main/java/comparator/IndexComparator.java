package comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import logger.Logger;
import table.Table;
import table.index.Index;

public class IndexComparator {

    private Logger logger;

    public Logger getLogger() {
        return logger;
    }

    /**
     * 
     */
    public void compareIndexes(Table table1, Table table2) {
        List<Index> indexes1 = table1.getIndexes();
        List<Index> indexes2 = table2.getIndexes();
        logger = new Logger();


        List<Index> table1AdditionalIndexes = findAdditionalIndexes(indexes1, indexes2);
        List<Index> table2AdditionalIndexes = findAdditionalIndexes(indexes2, indexes1);
        
        System.out.println();
        System.out.println("===============================================================================================================");
        System.out.println("Comparando las tablas: " + table1.getName());
        logger.add("Comparando las tablas: " + table1.getName());
        System.out.println();
        
        // Compare indexes with same name
        for (Index index1 : indexes1) {
            for (Index index2 : indexes2) {
                if (index1.getName().equals(index2.getName())) {
                    compareIndexesithSameName(index1, index2);
                    break; 
                }
            }
        }

        logger.add("Indices adicionales de la tabla 1" + " : " + table1AdditionalIndexes);
        logger.add("\nIndices adicionales de la tabla 2" + " : " + table2AdditionalIndexes);
        System.out.println("Indices adicionales de la tabla 1" + " : " + table1AdditionalIndexes);
        System.out.println("Indices adicionales de la tabla 2" + " : " + table2AdditionalIndexes);
    }

    /**
     * Compares two indexes with the same name.
     * 
     * @param index1 the index from `index1` to be compared.
     * @param index2 the index from `index2` to be compared.
     */
    private void compareIndexesithSameName(Index index1, Index index2) {
        if (!index1.getFields().equals(index2.getFields())) {
            logger.add("Los indices " + index1.getName() + " tienen fields diferentes: " + index1.getFields() + " y " + index2.getFields());
            System.out.println("Los indices " + index1.getName() + " tienen fields diferentes: " + index1.getFields() + " y " + index2.getFields());
        }
        if (!index1.getType().equals(index2.getType())) {
            logger.add("Los indices " + index1.getName() + " tienen tipos diferentes: " + index1.getType() + " y " + index2.getType());
            System.out.println("Los indices " + index1.getName() + " tienen tipos diferentes: " + index1.getType() + " y " + index2.getType());
        }
    }

    /**
     * 
     * @param source
     * @param target
     * @return
     */
    private List<Index> findAdditionalIndexes(List<Index> source, List<Index> target) {
        List<Index> additionalElements = new ArrayList<>();

        // Extract the names of indexes in the target list for comparison
        List<String> targetIndexNames = target.stream()
                                            .map(Index::getName)
                                            .collect(Collectors.toList());

        for (Index element : source) {
            if (!targetIndexNames.contains(element.getName())) {
                additionalElements.add(element);
            }
        }
        return additionalElements;
    }
}