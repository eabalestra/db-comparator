package comparator.storedprocedure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import db.Database;
import logger.Logger;
import storedprocedure.StoredProcedure;

public class StoredProcedureComparator {
    Database database1;
    Database database2;
    Logger logger;
    StoredProcedureColumnComparator storedProcedureColumnComparator;

    public Logger getLogger() {
        return logger;
    }

    public StoredProcedureComparator(Database database1, Database database2, Logger logger) {
        this.database1 = database1;
        this.database2 = database2;
        this.logger = logger;
        this.storedProcedureColumnComparator = new StoredProcedureColumnComparator(logger);
    }

    public void compareStoredProcedures() {
        List<StoredProcedure> storedProcedures1 = database1.getStoredProcedures();
        List<StoredProcedure> storedProcedures2 = database2.getStoredProcedures();

        List<StoredProcedure> db1additionalStoredProcedures = findAdditionalStoredProcedures(storedProcedures1,
                storedProcedures2);
        List<StoredProcedure> db2additionalStoredProcedures = findAdditionalStoredProcedures(storedProcedures2,
                storedProcedures1);

        for (StoredProcedure procedure1 : storedProcedures1) {
            for (StoredProcedure procedure2 : storedProcedures2) {
                if (procedure1.getName().equals(procedure2.getName())) {
                    logger.add("\n============== Diferencias entre los procedimientos con nombre: '"
                            + procedure1.getName() + "' ============== \n");
                    compareStoredProceduresWithSameName(procedure1, procedure2);
                    break;
                }
            }
        }

        logger.add("\n¿Tienen los mismos procedimientos/funciones? "
                + (db1additionalStoredProcedures.isEmpty() && db2additionalStoredProcedures.isEmpty() ? "Sí\n" : "No\n"));

        if (!db1additionalStoredProcedures.isEmpty()) {
            logger.add(
                    "Procedimientos Almacenados adicionales de la base de datos 1: " + db1additionalStoredProcedures);
        }

        if (!db2additionalStoredProcedures.isEmpty()) {
            logger.add(
                    "Procedimientos Almacenados adicionales de la base de datos 2: " + db2additionalStoredProcedures);
        }
    }

    private List<StoredProcedure> findAdditionalStoredProcedures(List<StoredProcedure> source,
            List<StoredProcedure> target) {

        List<StoredProcedure> additionalElements = new ArrayList<>();

        List<String> targetStoredProcedureNames = target.stream()
                .map(StoredProcedure::getName)
                .collect(Collectors.toList());

        for (StoredProcedure element : source) {
            if (!targetStoredProcedureNames.contains(element.getName())) {
                additionalElements.add(element);
            }
        }
        return additionalElements;
    }

    public void compareStoredProceduresWithSameName(StoredProcedure procedure1, StoredProcedure procedure2) {
        storedProcedureColumnComparator.storedprocedureColumnComparator(procedure1, procedure2);
    }
}