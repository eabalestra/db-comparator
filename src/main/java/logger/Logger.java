package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Logger {

    private String file = "";

    public Logger() {}


    public String get() {
        return file;
    }

    public void add(String msg) {
        this.file += msg + "\n";
    }


    public void log(String msg) {
        System.out.println(msg);
    }

    //

    public void copy(Logger other) {
        this.file += other.get() + "\n";
    }

    public void writeFile(String fileName) {
        String rutaDirectorio = "src/main/resources/files"; // Directorio dentro de resources
        String nombreArchivo = fileName + ".txt";
        String contenido = file;

        try {
            // Asegurarse de que el directorio existe
            Path pathDirectorio = Paths.get(rutaDirectorio);
            if (!Files.exists(pathDirectorio)) {
                Files.createDirectories(pathDirectorio);
            }

            // Crear el archivo en el directorio especificado
            FileWriter escritor = new FileWriter(rutaDirectorio + "/" + nombreArchivo);
            escritor.write(contenido);
            escritor.close();
            System.out.println("Archivo creado exitosamente en " + rutaDirectorio);
        } catch (IOException e) {
            System.out.println("Ocurrió un error al crear o escribir en el archivo.");
            e.printStackTrace();
        }
    }

    
    
}