package db;

import table.Table;

import java.util.List;

public class Database {
    List<Table> tables;

    public void addTable(Table table) {
        tables.add(table);
    }
}
