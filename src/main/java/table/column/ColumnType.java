package table.column;

public enum ColumnType {
    VARCHAR,
    INTEGER,
    DOUBLE,
    DATE;

    public static ColumnType fromString(String type) {
        for (ColumnType columnType : ColumnType.values()) {
            if (columnType.name().equalsIgnoreCase(type)) {
                return columnType;
            }
        }
        throw new IllegalArgumentException("No enum constant for string: " + type);
    }
}