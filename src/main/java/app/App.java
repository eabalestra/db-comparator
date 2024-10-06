package app;

import db.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static java.lang.System.exit;

public class App {
    private static final String postgresDriver = "org.postgresql.Driver";
    private static Scanner sc = new Scanner(System.in);
    private static final String EXIT = "5";
    private static final String START = "";

    public App() {
        try {
            Class.forName(postgresDriver);
        } catch (Exception exception) {
            System.err.println("\nError loading driver\n" + exception);
            exit(0);
        }
    }

    public void run() throws SQLException {
        String opcion = START;
        while (!opcion.equals(EXIT)) {
            imprimirMenu();
            opcion = sc.next();
            System.out.println();
            switch (opcion) {
                case "1":
                    listarClientes();
                    break;
                case "2":
                    altaCliente();
                    break;
                case EXIT:
                    System.out.println("¡Muchas gracias!\n");
                    break;
                default:
                    System.out.println("Opcion no disponible. Intente nuevamente.\n");
                    break;
            }
        }
    }

    public void imprimirMenu(){
        System.out.println("\nEJERCICIO 1 A ");
        System.out.println("-------------------------------");
        System.out.println("1) Listar clientes.");
        System.out.println("2) Alta de cliente.");
        System.out.println("5) Salir");
        System.out.println("--------------------------------");
        System.out.print("Seleccione una opcion (1 a 5): ");
    }

    private static void listarClientes() throws SQLException {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getInstance();
            String query =
                    "SELECT c.nro_cliente, c.apellido, c.nombre, c.direccion, c.telefono " +
                    "FROM exercise_a.cliente c";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("Listado de clientes: \n");
            while (resultSet.next()) {
                System.out.print("* NRO CLIENTE: " + resultSet.getString("nro_cliente"));
                System.out.print(", apellido: " + resultSet.getString("apellido"));
                System.out.print(", nombre: " + resultSet.getString("nombre"));
                System.out.print(", direccion: " + resultSet.getString("direccion"));
                System.out.print(", telefono: " + resultSet.getString("telefono"));
                System.out.print("\n");
            }
            resultSet.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void altaCliente() {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getInstance();
            System.out.println("Ingrese los datos del cliente: ");
            System.out.println("Nro Cliente: ");
            int nroCliente = sc.nextInt();
            System.out.print("Apellido: ");
            String apellido = sc.next();
            System.out.print("Nombre: ");
            String nombre = sc.next();
            System.out.print("Direccion: ");
            String direccion = sc.next();
            System.out.print("Telefono: ");
            String telefono = sc.next();

            String query =
                    "INSERT INTO exercise_a.cliente (nro_cliente, apellido, nombre, direccion, telefono) " +
                    "VALUES (" + nroCliente + ", '" + apellido + "', '" + nombre + "', '" + direccion + "', '" + telefono + "')";

            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            System.out.println("Cliente dado de alta correctamente.\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
