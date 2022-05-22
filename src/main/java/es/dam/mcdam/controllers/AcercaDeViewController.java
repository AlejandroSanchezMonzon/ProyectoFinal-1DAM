package es.dam.mcdam.controllers;

import es.dam.mcdam.utils.Properties;
import es.dam.mcdam.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class AcercaDeViewController {
    private Stage dialogStage;

    @FXML
    private Label version;
    @FXML
    private Label autor;
    @FXML
    private Label titulo;
    @FXML
    private ImageView githubIcon;
    @FXML
    private Hyperlink githubLink;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        titulo.setText(Properties.APP_TITLE);
        version.setText("Version: " + Properties.APP_VERSION);
        autor.setText("Autores: " + Properties.APP_AUTHOR);

        githubLink.setText(Properties.ACERCADE_LINK);

        githubLink.setOnAction(event -> openGitHub());
    }

    private void openGitHub() {
        try {
            Utils.openBrowser(Properties.ACERCADE_LINK);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al abrir el navegador");
            alert.setContentText("No se ha podido abrir el navegador");
            e.printStackTrace();
        }
    }

    @FXML
    private void aceptarOnClick() {
        dialogStage.close();
    }
}
