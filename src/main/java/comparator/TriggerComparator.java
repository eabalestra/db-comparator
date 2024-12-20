package comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import logger.Logger;
import table.Table;
import table.trigger.Trigger;

public class TriggerComparator {

    private Logger logger;

    public Logger getLogger() {
        return logger;
    }

    /**
     * Compares triggers between two tables, identifying additional triggers in each.
     * For triggers with the same name, it checks if they differ in timing or event.
     */
    public void compareTriggers(Table table1, Table table2) {
        List<Trigger> triggers1 = table1.getTriggers();
        List<Trigger> triggers2 = table2.getTriggers();
        logger = new Logger();

        List<Trigger> table1AdditionalTriggers = findAdditionalTriggers(triggers1, triggers2);
        List<Trigger> table2AdditionalTriggers = findAdditionalTriggers(triggers2, triggers1);

        logger.add("--- Diferencias entre los triggers: ---");

        for (Trigger trigger1 : triggers1) {
            for (Trigger trigger2 : triggers2) {
                if (trigger1.getName().equals(trigger2.getName())) {
                    compareTriggersWithSameName(trigger1, trigger2);
                    break; 
                }
            }
        }
        logger.add("  Triggers adicionales de la tabla 1" + " : " + table1AdditionalTriggers);
        logger.add("  Triggers adicionales de la tabla 2" + " : " + table2AdditionalTriggers);
    }

    /**
     * Compares two triggers with the same name.
     * 
     * @param trigger1 the trigger from `table1` to be compared.
     * @param trigger2 the trigger from `table2` to be compared.
     */
    private void compareTriggersWithSameName(Trigger trigger1, Trigger trigger2) {
        if (!trigger1.equals(trigger2)) {
            logger.add("  Los triggers '" + trigger1.getName() + "' tienen diferentes momentos de disparos: " + trigger1.getactionTrigger() + " " +
              trigger1.getEventManipulation() + " y " + trigger2.getactionTrigger() + " " + trigger2.getEventManipulation());
        }
    }

    /**
     * 
     * @param source
     * @param target
     * @return
     */
    private List<Trigger> findAdditionalTriggers(List<Trigger> source, List<Trigger> target) {
        List<Trigger> additionalElements = new ArrayList<>();

        // Extract the names of columns in the target list for comparison
        List<String> targetColumnNames = target.stream()
                                            .map(Trigger::getName)
                                            .collect(Collectors.toList());

        for (Trigger element : source) {
            if (!targetColumnNames.contains(element.getName())) {
                additionalElements.add(element);
            }
        }
        return additionalElements;
    }
}