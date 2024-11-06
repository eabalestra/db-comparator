package storedprocedure;

import java.util.ArrayList;
import java.util.List;

import storedprocedure.column.StoredProcedureColumn;

/**
 * Represents a stored procedure in a database, defined by a unique name and a list of associated columns.
 * <p>
 * Each stored procedure has a name to identify it and a collection of columns (parameters or return types) that specify its structure.
 * This class provides methods to retrieve the stored procedure's name, add columns, and compare stored procedures based on their attributes.
 * </p>
 */
public class StoredProcedure {
    private String name;
    private List<StoredProcedureColumn> columns;

    /**
     * Constructs a new StoredProcedure with the specified name.
     *
     * @param name the unique name of the stored procedure
     */
    public StoredProcedure(String name) {
        this.name = name;
        columns = new ArrayList<>();
    }

    /**
     * Returns the name of the stored procedure.
     *
     * @return the name of the stored procedure
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the columns of the stored procedure.
     *
     * @return the columns of the stored procedure
     */
    public List<StoredProcedureColumn> getColumns() {
        return columns;
    }


    /**
     * Adds a column to the stored procedure.
     *
     * @param column the column to be added, representing a parameter or return type
     */
    public void addColumn(StoredProcedureColumn column) {
        columns.add(column);
    }

    /**
     * Compares this stored procedure to another object for equality.
     * <p>
     * Stored procedures are considered equal if they have the same name and identical columns.
     * </p>
     *
     * @param o the object to compare with this stored procedure
     * @return true if the specified object is equal to this stored procedure, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        StoredProcedure that = (StoredProcedure) o;

        if (!name.equals(that.name))
            return false;

        return columns.equals(that.columns);
    }

    /**
     * Returns a string representation of the stored procedure.
     * <p>
     * The string includes the stored procedure's name and a list of columns for easier inspection and debugging.
     * </p>
     *
     * @return a string representation of the stored procedure
     */
    @Override
    public String toString() {
        return "StoredProcedure{" + "name='" + name + '\'' + ", columns=" + columns + '}';
    }
}
