package table.column;

public enum ColumnType {
    VARCHAR, INT4, INT8, TEXT, BOOLEAN, DOUBLE, FLOAT8,
    TIMESTAMP, DATE, NUMERIC, JSON, JSONB, UUID, BYTEA,
    TRIGGER, VOID, NAME, SERIAL, BIGSERIAL;

    public static ColumnType fromString(String type) {
        for (ColumnType columnType : ColumnType.values()) {
            if (columnType.name().equalsIgnoreCase(type)) {
                return columnType;
            }
        }
        throw new IllegalArgumentException("No enum constant for string: " + type);
    }
}