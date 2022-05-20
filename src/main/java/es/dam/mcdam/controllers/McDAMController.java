package es.dam.mcdam.controllers;

import es.dam.mcdam.managers.DataBaseManager;

import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static javafx.application.Application.launch;

public class McDAMController {

    public static void main(String[] args) {
        checkServer();
        launch();
    }

    private static void checkServer() {
        System.out.println("Comprobamos la conexión al Servidor BD");
        DataBaseManager controller = DataBaseManager.getInstance();
        try {
            controller.open();
            Optional<ResultSet> rs = controller.select("SELECT 'Hello world'");
            if (rs.isPresent()) {
                rs.get().next();
                controller.close();
                System.out.println("Conexión correcta a la Base de Datos");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar al servidor de Base de Datos: " + e.getMessage());
            System.exit(1);
        }
    }

    //TODO: Iniciar la escena y extender la clase de la aplicacion
}
