package app;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        App app = new App();
        app.execute();
        System.exit(0);
    }
}