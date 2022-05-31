package es.dam.mcdam.controllers;

import es.dam.mcdam.AppMain;
import es.dam.mcdam.models.CodigoDescuento;
import es.dam.mcdam.models.Producto;
import es.dam.mcdam.repositories.CodigoDescuentoRepository;
import es.dam.mcdam.repositories.ProductoRepository;
import es.dam.mcdam.utils.Resources;
import es.dam.mcdam.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import static es.dam.mcdam.utils.Properties.CODIGODESCUENTO_DEFAULT;

public class ActualizarCodigoDescuentoViewController {

    CodigoDescuentoRepository codigoRepository = CodigoDescuentoRepository.getInstance();
    @FXML
    TextField codigoTxt;
    @FXML
    TextField porcentajeDescuentoTxt;
    @FXML
    ImageView imageView;
    @FXML
    Button aceptarButon;
    @FXML
    Button cancelarButon;
    private Stage dialogStage;
    private CodigoDescuento codigoDescuento;
    private boolean aceptarClicked = false;
    private boolean editarModo = false;

    @FXML
    private void initialize() {
        System.out.println("Editar o nuevo código de descuento");
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCodigoDescuento(CodigoDescuento codigoDescuento) {
        this.codigoDescuento = codigoDescuento;
        System.out.println("Codigo Descuento asociado: " + codigoDescuento);
        if (editarModo) {
            setDataInfo();
        }
        codigoTxt.requestFocus();
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
    private void onAceptarAction() throws SQLException {
        System.out.println("Aceptar");
        System.out.println("Validar datos");
        if (isInputValid()) {
            codigoDescuento.setCodigo(codigoTxt.getText());
            codigoDescuento.setPorcentajeDescuento(Float.parseFloat(porcentajeDescuentoTxt.getText()));
            aceptarClicked = true;
            dialogStage.close();
            codigoRepository.save(codigoDescuento);
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
        boolean errorMessage = true;

        if (codigoTxt.getText() == null || codigoTxt.getText().isBlank()) {
            errorMessage = false;
        }else if (porcentajeDescuentoTxt.getText() == null || porcentajeDescuentoTxt.getText().isBlank()) {
            errorMessage = false;
        }
        if (!errorMessage) {
            Alert alert = Utils.getAlertErrorDetails("Error en datos", "Datos de codigo descuento incorrrectos", "Existen problemas al validar.", "Datos incorrectos");
            alert.showAndWait();
        }
        return errorMessage;
    }

}
