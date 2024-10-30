package table.index;

import java.util.HashMap;
import java.util.Map;

public class Index {
    private String name;
    private String tableName;
    private Map<String, Boolean> fields = new HashMap<>(); // keys: fields the index is defined on, values: DESC / ASC
    private IndexType type;

    public Index(String name, String table) {
        this.name = name;
        this.tableName = table;
    }

    public Index(String name, String tableName, Map<String, Boolean> fields, IndexType type) {
        this.name = name;
        this.tableName = tableName;
        this.fields = fields;
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

    public Map<String, Boolean> getFields() {
        return fields;
    }

    public void setFields(Map<String, Boolean> fields) {
        this.fields = fields;
    }

    public IndexType getType() {
        return type;
    }

    public void setType(IndexType type) {
        this.type = type;
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
            type == other.type;
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"name\" : \"" + name + "\",\n" +
                "  \"tableName\" : \"" + tableName + "\",\n" +
                "  \"fields\" : " + fields + ",\n" +
                "  \"type\" : \"" + type + "\"\n" +
                "}\n";
    }
}