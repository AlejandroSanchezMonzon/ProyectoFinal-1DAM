package es.dam.mcdam.controllers;

import es.dam.mcdam.AppMain;
import es.dam.mcdam.managers.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;

public class MenuAdministradorViewController {
    SceneManager sceneManager = SceneManager.getInstance(AppMain.class);
    @FXML
    private Button consultar;
    @FXML
    private Button editar;
    @FXML
    private Button backup;

    public void initialize(){
        consultar.setOnAction(event -> {
            System.out.println("Consultar");
            try {
                openConsultar();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        editar.setOnAction(event -> {
            System.out.println("Editar");
            try {
                openEditar();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        backup.setOnAction(event -> {
            System.out.println("Backup");
            try {
                openBackup();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void openConsultar() throws IOException {
        sceneManager.initConsultaAdministrador();
    }
    private void openEditar() throws IOException {
        sceneManager.initEdicionAdministrador();
    }

    private void openBackup() throws SQLException, IOException {
        sceneManager.initBackup();
    }




}
