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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

public class ActualizarProductoViewController {

    ProductoRepository productoRepository = ProductoRepository.getInstance();
    @FXML
    TextField nombreTxt;
    @FXML
    TextField idTxt;
    @FXML
    TextField precioTxt;
    @FXML
    TextField descripcionTxt;
    @FXML
    TextField disponibleTxt;
    @FXML
    TextField codigoTxt;
    @FXML
    ImageView imageView;

    @FXML
    Button aceptar;

    @FXML
    Button cancelar;
    private Stage dialogStage;
    private Producto producto;
    private boolean aceptarClicked = false;
    private boolean editarModo = false;

    @FXML
    private void initialize() throws SQLException {
        System.out.println("Editar o nuevo producto");
        aceptar.setOnAction(event ->{
                    try {
                        onAceptarAction();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                );
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        System.out.println("Producto asociado: " + producto);
        if (editarModo) {
            setDataInfo();
        }
        nombreTxt.requestFocus();
    }

    private void setDataInfo() {
        System.out.println("SetDataInfo");
        nombreTxt.setText(producto.getNombre());
        idTxt.setText(producto.getUuid());
        precioTxt.setText(Float.toString(producto.getPrecio()));
        descripcionTxt.setText(producto.getDescripcion());
        disponibleTxt.setText(String.valueOf(producto.getDisponible()));
        codigoTxt.setText(producto.getCodigoDescuento().getCodigo());
        // La imagen
        if (!producto.getImagen().isBlank() && Files.exists(Paths.get(producto.getImagen()))) {
            System.out.println("Cargando imagen: " + producto.getImagen());
            Image image = new Image(new File(producto.getImagen()).toURI().toString());
            System.out.println("Imagen cargada: " + image.getUrl());
            imageView.setImage(image);
        } else {
            System.out.println("No existe la imagen. Usando imagen por defecto");
            imageView.setImage(new Image(Resources.get(AppMain.class, "icons/adduser.png")));
            producto.setImagen(Resources.getPath(AppMain.class, "icons/adduser.png"));
            System.out.println("Nueva información de imagen: " + producto);
        }

    }

    public void setEditarModo(boolean editarModo) {
        this.editarModo = editarModo;
        System.out.println("Modo Editar: " + editarModo);
    }

    public boolean isAceptarClicked() {
        return aceptarClicked;
    }



    private Producto onAceptarAction() throws SQLException {
        System.out.println("Aceptar");
        System.out.println("Validar datos");
        if (isInputValid()) {
            producto.setNombre(nombreTxt.getText());
            producto.setUuid(idTxt.getText());
            producto.setPrecio(Float.parseFloat(precioTxt.getText()));
            producto.setDescripcion(descripcionTxt.getText());
            producto.setDisponible(Boolean.parseBoolean(disponibleTxt.getText()));
            producto.setCodigoDescuento(new CodigoDescuento(codigoTxt.getText(), 50f));
            producto.setImagen(imageView.getImage().getUrl());
            aceptarClicked = true;
            productoRepository.save(producto);
            dialogStage.close();
        } else {
            System.out.println("Datos no validos");
        }
        return producto;
    }

    @FXML
    private void onCancelarAction() {
        System.out.println("Has pulsado Cancelar");
        dialogStage.close();
    }

    //TODO validar datos mejor
    private boolean isInputValid() {
        boolean errorMessage = true;

        if (nombreTxt.getText() == null || nombreTxt.getText().isBlank()) {
            errorMessage = false;
        }else if (idTxt.getText() == null || idTxt.getText().isBlank()) {
            //errorMessage += "El id no pueden estar en blanco\n";
            errorMessage = false;
        }else if (precioTxt.getText() == null || precioTxt.getText().isBlank() || !Utils.isPrecio(precioTxt.getText())) {
           // errorMessage += "La calle no puede estar en blanco\n";
            errorMessage = false;
        }else if (descripcionTxt.getText() == null || descripcionTxt.getText().isBlank()) {
            //errorMessage += "La ciudad no puede estar en blanco\n";
            errorMessage = false;
        }else if (disponibleTxt.getText() == null || disponibleTxt.getText().isBlank() || !Utils.isYesorNo(disponibleTxt.getText())) {
            //errorMessage += "El email no puede estar en blanco o no es válido\n";
            errorMessage = false;
        }else if (codigoTxt.getText() == null || codigoTxt.getText().isBlank()) {
            //errorMessage += "La fecha de cumpleaños no puede ser superior a la actual\n";
            errorMessage = false;
        }
        if(!errorMessage){
            Alert alert = Utils.getAlertErrorDetails("Error en datos", "Datos de producto incorrrectos", "Existen problemas al validar.", "Datos incorrectos");
            alert.showAndWait();
        }
        return errorMessage;
    }

    @FXML
    private void onAvatarAction() {
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Selecciona una imagen");
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagenes", "*.jpg", "*.png"));
        File file = filechooser.showOpenDialog(imageView.getScene().getWindow());

        if (file != null) {
            System.out.println("Se ha seleccionado el archivo: " + file.getAbsolutePath());
            imageView.setImage(new Image(file.toURI().toString()));
            // Se lo asignamos a la persona...
            producto.setImagen(file.getAbsolutePath());
            System.out.println("Se ha asignado el avatar a la persona desde: " + producto.getImagen());
        }
    }
}
