package es.dam.mcdam.controllers;

import es.dam.mcdam.utils.Properties;
import es.dam.mcdam.utils.Utils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
    @FXML
    private Button aceptar;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        titulo.setText(Properties.APP_TITLE);
        version.setText("Version: " + Properties.APP_VERSION);
        autor.setText("Autores: " + Properties.APP_AUTHOR);

        githubIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                openGitHub();
                event.consume();
            }
        });

        aceptar.setOnAction(event -> aceptarOnClick());
    }

    private void openGitHub() {
        try {
            URI uri = new URI(Properties.ACERCADE_LINK);
            java.awt.Desktop.getDesktop().browse(uri);
            //Utils.openBrowser(Properties.ACERCADE_LINK);
        } catch (URISyntaxException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al abrir el navegador");
            alert.setContentText("No se ha podido abrir el navegador");
            e.printStackTrace();
            alert.showAndWait();
        }
    }

    @FXML
    private void aceptarOnClick() {
        dialogStage.close();
    }
}
