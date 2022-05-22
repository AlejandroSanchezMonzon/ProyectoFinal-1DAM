package es.dam.mcdam.controllers;

import es.dam.mcdam.managers.SceneManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {
    @FXML
    private ImageView fondo;

    @FXML
    private ImageView gifCarga;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition transition = new FadeTransition(Duration.millis(3000), fondo);
        transition.setFromValue(1.0);
        transition.setToValue(1.0);
        transition.play();

        transition.setOnFinished(event -> {
            Stage scene = (Stage) fondo.getScene().getWindow();
            scene.setResizable(false);
            scene.hide();
            SceneManager sceneManager = SceneManager.get();
            try {
                sceneManager.initMain();
            } catch (IOException e) {
                System.err.println("Error al cargar la escena principal");
                e.printStackTrace();
            }
        });
    }
}
