package comparator.storedprocedure;

import java.util.ArrayList;
import java.util.List;

import logger.Logger;
import storedprocedure.StoredProcedure;
import storedprocedure.column.StoredProcedureColumn;

public class StoredProcedureColumnComparator {
    Logger logger;

    public Logger getLogger() {
        return logger;
    }

    StoredProcedureColumnComparator(Logger logger) {
        this.logger = logger;
    }

    public void storedprocedureColumnComparator(StoredProcedure storedProcedure1, StoredProcedure storedProcedure2) {
        List<StoredProcedureColumn> storedProcedureColumns1 = storedProcedure1.getColumns();
        List<StoredProcedureColumn> storedProcedureColumns2 = storedProcedure2.getColumns();

        System.out.println("--- Diferencias entre parámetros de '" + storedProcedure1.getName() + "' y '"
                + storedProcedure2.getName() + "'. --- \n");
        logger.add("--- Diferencias entre parámetros de '" + storedProcedure1.getName() + "' y '"
                + storedProcedure2.getName() + "'. --- \n");

        int stProcSize = storedProcedureColumns1.size() < storedProcedureColumns2.size()
                ? storedProcedureColumns1.size()
                : storedProcedureColumns2.size();

        List<StoredProcedureColumn> stProcMax = (storedProcedureColumns1.size() > storedProcedureColumns2.size())
                ? storedProcedureColumns1
                : storedProcedureColumns2;

        for (int i = 0; i < stProcSize; i++) {
            StoredProcedureColumn storedProcedureColumn1 = storedProcedureColumns1.get(i);
            if (!storedProcedureColumn1.equals(storedProcedureColumns2.get(i))) {
                System.out.println(
                        "--- Diferencias entre los parámetros de órden: " + storedProcedureColumn1.getColumnOrder()
                                + " ---");
                System.out.println("  Párametro del procedure 1: " + storedProcedureColumns1.get(i));
                System.out.println("  Párametro del procedure 2: " + storedProcedureColumns2.get(i));

            }
        }

        System.out.println("Parámetros adicionales de " + storedProcedure1.getName() + ": ");
        //
        logger.add("Parámetros adicionales de " + storedProcedure1.getName() + ": ");
        //
        if (stProcSize < stProcMax.size()) {
            for (int i = stProcSize; i < stProcMax.size(); i++) {
                System.out.println("column: " + stProcMax.get(i) + "\n");
                //
                logger.add("column: " + stProcMax.get(i) + "\n");
                //
            }
        }
    }
}