package db;

import table.Table;

import java.util.List;
import java.util.ArrayList;

public class Database {
    String name;
    String schema;
    List<Table> tables;

    public Database() {
        tables = new ArrayList<>();
    }

    public List<Table> getTables() {
        return tables;
    }
    
    public void addTable(Table table) {
        tables.add(table);
    }

    @Override
    public String toString() {
        String result = "";
        result += "Database: " + name + "\n";
        result += "Schema: " + schema + "\n";
        result += "Tables: \n";
        for (Table actualTable : tables) {
            result += actualTable.toString() + "\n";
        }
        return result;
    }
}
