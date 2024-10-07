package table.column;

public class Column {
    private String name;
    private ColumnType type;

    public Column(String name, ColumnType type) {
        this.name = name;
        this.type = type;
    }   

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

    public ColumnType getType() {
        return type;
    }

    public boolean equals (Object c) {

        if (this == c) {
            return true; 
        }
        if (c == null || getClass() != c.getClass()) {
            return false;
        }

        Column column = (Column) c;

        if (!name.equals(column.name)) {
            return false;
        }
        if (!type.equals(column.type)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

}