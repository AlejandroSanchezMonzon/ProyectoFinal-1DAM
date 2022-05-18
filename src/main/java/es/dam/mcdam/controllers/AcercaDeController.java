package es.dam.mcdam.controllers;

import es.dam.mcdam.utils.Properties;
import es.dam.mcdam.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class AcercaDeController {

    private Stage acercaDeStage;
    @FXML
    private Label version;
    @FXML
    private Label autor;
    @FXML
    private ImageView logo;
    @FXML
    private Hyperlink githubLink;

    @FXML
    public void setAcercaDeStage(Stage acercaDeStage) {
        this.acercaDeStage = acercaDeStage;
    }

    @FXML
    public void initialize() {
        version.setText("Versión: " + Properties.APP_VERSION);
        autor.setText("Autores: " + Properties.APP_AUTHOR);
        logo.setImage(new Image(Properties.APP_LOGO));
        githubLink.setText(Properties.ACERCADE_LINK);


        githubLink.setOnAction(event -> openGithub());


    }

    private void openGithub() {
        try{
            Utils.openBrowser(Properties.ACERCADE_LINK);
        }catch (IOException e){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error al abrir la página");
            alerta.setContentText(e.getMessage());
        }

    }

    @FXML
    private void handleCerrar() {
        acercaDeStage.close();
    }


}
