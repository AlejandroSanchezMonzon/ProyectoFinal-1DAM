/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import es.dam.mcdam.models.PersonaRegistrada;
import es.dam.mcdam.utils.LocalDateAdapter;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

public class BackupJSON implements IBackupJSON{
    //ESTADO

    private static BackupJSON instance;

    private final String APP_PATH = System.getProperty("user.dir");
    private final String DATA_DIR = APP_PATH + File.separator + "data";
    private final String BACKUP_FILE = DATA_DIR + File.separator + "backup" + File.separator;

    //CONSTRUCTOR
     private BackupJSON(){
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }

    //SINGLETON
    public static BackupJSON getInstance(){
        if (instance == null){
            instance = new BackupJSON();
        }
        return instance;
    }

    /**
     * Método que hace una copia de seguridad de personas en un fichero JSON.
     * @param personas
     * @throws IOException
     */
    @Override
    public void backupPersonas(ObservableList<PersonaRegistrada> personas) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(personas);
        Files.writeString(new File(BACKUP_FILE +  "personasRegistradas.json").toPath(), json);

    }

    /**
     * Método que hace una copia de seguridad de pedidos en un fichero JSON.
     * @param pedidos
     * @throws IOException
     */
    public void backupPedidos(List<String> pedidos) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(pedidos);
        Files.writeString(new File(BACKUP_FILE +  "pedidos.json").toPath(), json);

    }

    /**
     * Método que hace una copia de seguridad de personas registradas en un fichero JSON.
     * @return
     * @throws IOException
     */
    @Override
    public ObservableList<PersonaRegistrada> restore() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String json = "";
        json = Files.readString(new File(BACKUP_FILE + "personasRegistradas.json").toPath());
        return gson.fromJson(json, new TypeToken<List<PersonaRegistrada>>(){
        }.getType());
    }
}
