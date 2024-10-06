package table;

public class Trigger {
    private String name;
    private String triggerTiming;
    private String tableName;

    public Trigger(String name, String triggerTiming, String tableName) {
        this.name = name;
        this.triggerTiming = triggerTiming;
        this.tableName = tableName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTriggerTiming(String triggerTiming) {
        this.triggerTiming = triggerTiming;
    }

    public String getTriggerTiming() {
        return triggerTiming;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
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
            triggerTiming.equals(other.triggerTiming) &&
            tableName.equals(other.tableName);
    }
}