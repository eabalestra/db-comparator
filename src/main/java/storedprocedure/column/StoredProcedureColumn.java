package storedprocedure.column;

import table.column.ColumnType;

/**
 * Represents a column in a stored procedure, defined by a parameter type, name, data type, and order.
 */
public class StoredProcedureColumn {
    StoredProcedureColumnType parameterType;
    String columnName;
    ColumnType columnType;
    String columnOrder;

    public StoredProcedureColumn(StoredProcedureColumnType parameterType, String columnName, ColumnType columnType, String columnOrder) {
        this.parameterType = parameterType;
        this.columnName = columnName;
        this.columnType = columnType;
        this.columnOrder = columnOrder;
    }

    public String getColumnOrder() {
        return columnOrder;
    }

    @Override
    public String toString() {
        return "StoredProcedureColumn [columnName=" + columnName + ", columnOrder=" + columnOrder + ", parameterType="
                + parameterType + ", type=" + columnType + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        StoredProcedureColumn that = (StoredProcedureColumn) o;

        if (parameterType != that.parameterType)
            return false;
        if (columnType != that.columnType)
            return false;

        return columnOrder.equals(that.columnOrder);
    }
}

