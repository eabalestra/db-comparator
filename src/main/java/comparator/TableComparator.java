package comparator;

import java.util.List;
import db.Database;
import logger.Logger;
import table.Table;

public class TableComparator {
    private Database db1;
    private Database db2;
    private Logger logger;

    public TableComparator(Database db1, Database db2) {
        this.db1 = db1;
        this.db2 = db2;
    }

    public void setDb1(Database db1) {
        this.db1 = db1;
    }

    public Database getDb1() {
        return db1;
    }

    public void setDb2(Database db2) {
        this.db2 = db2;
    }

    public Database getDb2() {
        return db2;
    }

    public void compareTables() {
        List<Table> db1Tables = db1.getTables();
        List<Table> db2Tables = db2.getTables();
        
        
    }

}