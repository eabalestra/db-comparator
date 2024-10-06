package table;

import java.util.List;

public class Index {
    private String name;
    private String tableName;
    private List<String> fields; // fields of the table the index is defined on.
    private boolean isAsc; // true if ASC, false if DESC.
    private IndexType type;

    public Index(String name, String table) {
        this.name = name;
    }

    public Index(String name, String tableName, List<String> fields, boolean isAsc, IndexType type) {
        this.name = name;
        this.tableName = tableName;
        this.fields = fields;
        this.isAsc = isAsc;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return tableName;
    }

    public void setTable(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public boolean isAsc() {
        return isAsc;
    }

    public void setAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }

    public IndexType getType() {
        return type;
    }

    public void setType(IndexType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Index[name=" + name + ", table=" + tableName + ", fields=" + fields + ", isAsc=" + isAsc + ", type=" + type + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; 
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Index other = (Index) obj; 

        return name.equals(other.name) &&
            tableName.equals(other.tableName) &&
            fields.equals(other.fields) &&
            isAsc == other.isAsc &&
            type == other.type;
    }
}