package storedprocedure;

import java.util.ArrayList;
import java.util.List;

import storedprocedure.column.StoredProcedureColumn;

public class StoredProcedure {
    private String name;
    private List<StoredProcedureColumn> columns;

    public StoredProcedure(String name) {
        this.name = name;
        columns = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addColumn(StoredProcedureColumn column) {
        columns.add(column);
    }

    // @Override
    // public boolean equals(Object f) {

    // if (this == f) {
    // return true;
    // }
    // if (f == null || getClass() != f.getClass()) {
    // return false;
    // }

    // StoredProcedure function = (StoredProcedure) f;

    // if (!name.equals(function.name)) {
    // return false;
    // }
    // if (!returnType.equals(function.returnType)) {
    // return false;
    // }
    // if (!listParams.equals(function.listParams)) {
    // return false;
    // }
    // return true;
    // }

    @Override
    public String toString() {
        return "StoredProcedure{" + "name='" + name + '\'' + ", columns=" + columns + '}';
    }

}