/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.services;

import javafx.collections.ObservableList;

import java.io.IOException;

public interface Backup<T>{

    /**
     * Método que realiza una copia de seguridad de la base de datos.
     * @return
     * @throws IOException
     */
    ObservableList<T> restore() throws IOException;
}
