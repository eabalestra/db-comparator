package table.foreignkey;

public class ForeignKey {
    String name;
    String column;
    String referencedTable;
    String referencedColumn;

    public ForeignKey(String name, String column, String referencedTable, String referencedColumn) {
        this.name = name;
        this.column = column;
        this.referencedTable = referencedTable;
        this.referencedColumn = referencedColumn;
    }
}
