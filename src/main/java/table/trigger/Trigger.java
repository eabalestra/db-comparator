package table.trigger;

public class Trigger {
    private String name;
    private String actionTrigger;   // before / after
    private String tableName;
    private String eventManipulation;   // insert / update / delete

    public Trigger(String name, String tableName, String actionTrigger, String eventManipulation) {
        this.name = name;
        this.tableName = tableName;
        this.actionTrigger = actionTrigger;
        this.eventManipulation = eventManipulation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setactionTrigger(String actionTrigger) {
        this.actionTrigger = actionTrigger;
    }

    public String getactionTrigger() {
        return actionTrigger;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setEventManipulation(String eventManipulation) {
        this.eventManipulation = eventManipulation;
    }

    public String getEventManipulation() {
        return eventManipulation;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Trigger other = (Trigger) obj;

        return name.equals(other.name) &&
                actionTrigger.equals(other.actionTrigger) &&
                eventManipulation.equals(other.eventManipulation) &&
                tableName.equals(other.tableName);
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"name\" : \"" + name + "\",\n" +
                "  \"actionTrigger\" : \"" + actionTrigger + "\",\n" +
                "  \"tableName\" : \"" + tableName + "\",\n" +
                "  \"eventManipulation\" : \"" + eventManipulation + "\"\n" +
                "}\n";
    }
}