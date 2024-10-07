package table.foreignkey;

public class ForeignKey {
    String name;    // name of the foreign key constraint.
    String tableColumn; // column in the current table that is part of the foreign key.
    String referencedTable; // name of the table the foreign key references.
    String referencedColumn;    // column in the referenced table that is referenced by the foreign key.

    public ForeignKey(String name, String column, String referencedTable, String referencedColumn) {
        this.name = name;
        this.tableColumn = column;
        this.referencedTable = referencedTable;
        this.referencedColumn = referencedColumn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableColumn() {
        return tableColumn;
    }    

    public void setTableColumn(String tableColumn) {
        this.tableColumn = tableColumn;
    }

    public String getReferencedTable() {
        return referencedTable;
    }

    public void setReferencedTable(String referencedTable) {
        this.referencedTable = referencedTable;
    }

    public String getReferencedColumn() {
        return referencedColumn;
    } 

    public void setReferencedColumn(String referencedColumn) {
        this.referencedColumn = referencedColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) 
            return true;

        if (o == null || getClass() != o.getClass()) 
            return false;

        ForeignKey other = (ForeignKey) o;

        return name == other.name &&
               tableColumn.equals(other.tableColumn) &&
               referencedTable.equals(other.referencedTable) &&
               referencedColumn.equals(other.referencedColumn);
    }

    @Override
    public String toString() {
        return "ForeignKey{" +
                "name='" + name + '\'' +
                ", tableColumn='" + tableColumn + '\'' +
                ", referencedTable='" + referencedTable + '\'' +
                ", referencedColumn='" + referencedColumn + '\'' +
                '}';
    }
}