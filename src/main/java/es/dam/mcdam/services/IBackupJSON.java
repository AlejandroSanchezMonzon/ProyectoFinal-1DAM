package es.dam.mcdam.services;

import es.dam.mcdam.models.Pedido;
import es.dam.mcdam.models.PersonaRegistrada;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public interface IBackupJSON extends Backup<PersonaRegistrada> {

    void backupPersonas(ObservableList<PersonaRegistrada> personas) throws IOException;
    void backupPedidos(List<String> pedidos) throws IOException;
}
