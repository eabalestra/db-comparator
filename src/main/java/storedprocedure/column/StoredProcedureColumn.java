package storedprocedure.column;

import table.column.ColumnType;

/**
 * StoredProcedureColumn
 */
public class StoredProcedureColumn {
    StoredProcedureColumnType parameterType;
    String columnName;
    ColumnType type;
    String columnOrder;

    public StoredProcedureColumn(StoredProcedureColumnType parameterType, String columnName, ColumnType type, String columnOrder) {
        this.parameterType = parameterType;
        this.columnName = columnName;
        this.type = type;
        this.columnOrder = columnOrder;
    }

    @Override
    public String toString() {
        return "StoredProcedureColumn [columnName=" + columnName + ", columnOrder=" + columnOrder + ", parameterType="
                + parameterType + ", type=" + type + "]";
    }
}