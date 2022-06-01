/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.services;

import es.dam.mcdam.models.PersonaRegistrada;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public interface IBackupJSON extends Backup<PersonaRegistrada> {

    /**
     * Método que hace una copia de seguridad de personas.
     * @param personas
     * @throws IOException
     */
    void backupPersonas(ObservableList<PersonaRegistrada> personas) throws IOException;

    /**
     * Método que hace una copia de seguridad de pedidos.
     * @param pedidos
     * @throws IOException
     */
    void backupPedidos(List<String> pedidos) throws IOException;
}
