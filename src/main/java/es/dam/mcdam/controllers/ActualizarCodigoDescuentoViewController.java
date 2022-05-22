package es.dam.mcdam.controllers;

import es.dam.mcdam.AppMain;
import es.dam.mcdam.models.CodigoDescuento;
import es.dam.mcdam.models.Producto;
import es.dam.mcdam.utils.Resources;
import es.dam.mcdam.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static es.dam.mcdam.utils.Properties.CODIGODESCUENTO_DEFAULT;

public class ActualizarCodigoDescuentoViewController {
    @FXML
    TextField codigoTxt;
    @FXML
    TextField porcentajeDescuentoTxt;
    @FXML
    ImageView imageView;
    private Stage dialogStage;
    private CodigoDescuento codigoDescuento;
    private boolean aceptarClicked = false;
    private boolean editarModo = false;

    // GETTERS AND SETTERS
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCodigoDescuento(CodigoDescuento codigoDescuento) {
        this.codigoDescuento = codigoDescuento;
        System.out.println("Codigo Descuento asociado: " + codigoDescuento);
        if (editarModo) {
            setDataInfo();
        }
        codigoTxt.requestFocus(); // Obtiene el foco
    }

    private void setDataInfo() {
        System.out.println("SetDataInfo");
        codigoTxt.setText(codigoDescuento.getCodigo());
        porcentajeDescuentoTxt.setText(String.valueOf(codigoDescuento.getPorcentajeDescuento()));

        System.out.println("Usando imagen por defecto");
        imageView.setImage(new Image(Resources.get(AppMain.class, "icons/pagarcontarjeta.png")));
        System.out.println("Nueva información de imagen: " + codigoDescuento);

    }

    public void setEditarModo(boolean editarModo) {
        this.editarModo = editarModo;
        System.out.println("Modo Editar: " + editarModo);
    }

    public boolean isAceptarClicked() {
        return aceptarClicked;
    }

    @FXML
    private void initialize() {
        System.out.println("Editar o nuevo código de descuento");
    }

    @FXML
    private void onAceptarAction() {
        System.out.println("Aceptar");
        System.out.println("Validar datos");
        if (isInputValid()) {
            codigoTxt.setText(codigoDescuento.getCodigo());
            porcentajeDescuentoTxt.setText(String.valueOf(codigoDescuento.getPorcentajeDescuento()));
            aceptarClicked = true;
            dialogStage.close();
        } else {
            System.out.println("Datos no validos");
        }
    }

    @FXML
    private void onCancelarAction() {
        System.out.println("Has pulsado Cancelar");
        dialogStage.close();
    }

    //TODO validar datos mejor
    private boolean isInputValid() {
        String errorMessage = "";

        if (codigoTxt.getText() == null || codigoTxt.getText().isBlank()) {
            errorMessage += "El nombre no puede estar en blanco\n";
        }
        if (porcentajeDescuentoTxt.getText() == null || porcentajeDescuentoTxt.getText().isBlank()) {
            errorMessage += "El id no pueden estar en blanco\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = Utils.getAlertErrorDetails("Error en datos", "Datos de códido de descuento incorrrectos", "Existen problemas al validar.", errorMessage);
            alert.showAndWait();
            return false;
        }
    }

}
