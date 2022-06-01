/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.services;

import es.dam.mcdam.utils.Properties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Storage {
    //ESTADO
    private static Storage instance;

    //CONSTRUCTOR
    private Storage() {
        makeDirectory();
    }

    //SINGLETON
    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    /**
     * Método que crea el directorio de almacenamiento de las imágenes.
     */
    private void makeDirectory() {
        if (!Files.exists(Paths.get(Properties.DATA_DIR))) {
            try {
                Files.createDirectory(Paths.get(Properties.DATA_DIR));
                Files.createDirectory(Paths.get(Properties.BACKUP_DIR));
                Files.createDirectory(Paths.get(Properties.IMAGES_DIR));
                // Imagenes
            } catch (IOException e) {
                System.err.println("Error al crear directorio de trabajo de la aplicación");
            }
        }
    }

    /**
     * Método que elimina un fichero específico.
     * @param file
     * @throws IOException
     */
    public void deleteFile(String file) throws IOException {
        System.out.println("Borrando " + file);
        if (Files.exists(Paths.get(file))) {
            Files.delete(Paths.get(file));
        } else {
            System.out.println("No existe el archivo " + file);
        }
    }

    /**
     * Método que copia un fichero específico.
     * @param source
     * @param destination
     * @throws IOException
     */
    public void copyFile(String source, String destination) throws IOException {
        System.out.println("Copiando " + source + " a " + destination);
        if (Files.exists(Paths.get(source))) {
            Files.copy(Paths.get(source), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } else {
            System.out.println("No existe el archivo " + source);
        }
    }
}
