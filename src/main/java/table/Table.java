package table;

import table.column.Column;
import table.foreignkey.ForeignKey;
import table.index.Index;
import table.trigger.Trigger;

import java.util.List;

public class Table {
    private List<Column> columns;
    private String name;
    private List<Trigger> triggers;
    private List<Index> indexes;
    private List<ForeignKey> foreignKeys;
    private List<String> primaryKeys;
    private List<String> uniqueKeys;

    public Table(String name) {
        this.name = name;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTriggers(List<Trigger> triggers) {
        this.triggers = triggers;
    }

    public List<Trigger> getTriggers() {
        return triggers;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }

    public List<Index> getIndexs() {
        return indexes;
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    public void addTrigger(Trigger trigger) {
        triggers.add(trigger);
    }

    public void addIndex(Index index) {
        indexes.add(index);
    }

    public void setForeignKeys(List<ForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public List<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void addForeignKey(ForeignKey foreignKey) {
        foreignKeys.add(foreignKey);
    }

    public void setPrimaryKeys(List<String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public List<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setUniqueKey(List<String> uniqueKeys) {
        this.uniqueKeys = uniqueKeys;
    }

    public List<String> getUniqueKeys() {
        return uniqueKeys;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false; 
        }

        Table other = (Table) obj;

        return name == other.name &&
            columns.equals(other.columns) &&
            triggers.equals(other.triggers) &&
            indexes.equals(other.indexes) &&
            foreignKeys.equals(other.foreignKeys) &&
            primaryKeys.equals(other.primaryKeys) &&
            uniqueKeys.equals(other.uniqueKeys);
    }

    @Override
    public String toString() {
        return "Table{\n" +
                "name='" + name + '\'' +
                ",\n columns=" + columns +
                ",\n triggers=" + triggers +
                ",\n indexes=" + indexes +
                ",\n foreignKeys=" + foreignKeys +
                ",\n primaryKeys=" + primaryKeys +
                ",\n uniqueKeys=" + uniqueKeys + "\n" +
                '}';
    }
}