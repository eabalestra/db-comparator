package db;

import table.Table;

import java.util.List;

import storedprocedure.StoredProcedure;

import java.util.ArrayList;

public class Database {
    String name;
    String schema;
    List<Table> tables;
    List<StoredProcedure> storedProcedures;

    public Database() {
        tables = new ArrayList<>();
        storedProcedures = new ArrayList<>();
    }

    public List<Table> getTables() {
        return tables;
    }
    
    public void addTable(Table table) {
        tables.add(table);
    }

    public void setStoredProcedures(List<StoredProcedure> storedProcedures) {
        this.storedProcedures = storedProcedures;
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
        result += "Stored Procedures: \n";
        for (StoredProcedure actualStoredProcedure : storedProcedures) {
            result += actualStoredProcedure.toString() + "\n";
        }
        return result;
    }
}