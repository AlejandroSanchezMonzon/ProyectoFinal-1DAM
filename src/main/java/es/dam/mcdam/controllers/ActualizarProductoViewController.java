/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.controllers;

import es.dam.mcdam.AppMain;
import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.models.CodigoDescuento;
import es.dam.mcdam.models.Producto;
import es.dam.mcdam.repositories.ProductoRepository;
import es.dam.mcdam.services.Storage;
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
import java.util.UUID;

public class ActualizarProductoViewController {
    //ESTADO
    private final DataBaseManager db = DataBaseManager.getInstance();
    private final Storage storage = Storage.getInstance();
    ProductoRepository productoRepository = ProductoRepository.getInstance(db, storage);
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

    //COMPORTAMIENTO

    /**
     * Inicializa el controlador.
     */
    @FXML
    private void initialize() throws SQLException {
        System.out.println("Editar o nuevo producto");
    }

    /**
     * Establece el stage del dialogo.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Establece el producto a editar.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
        System.out.println("Producto asociado: " + producto);
        if (editarModo) {
            setDataInfo();
        }
        nombreTxt.requestFocus(); //Para que se ponga el foco en el primer campo.
    }

    /**
     * Rellena los campos con la información del producto, incluyendo un control de la representación de la imagen.
     */
    private void setDataInfo() {
        System.out.println("SetDataInfo");
        nombreTxt.setText(producto.getNombre());
        idTxt.setText(producto.getUuid());
        precioTxt.setText(Utils.redondeoPrecio(producto.getPrecio()));
        descripcionTxt.setText(producto.getDescripcion());
        disponibleTxt.setText(String.valueOf(producto.getDisponible()));
        codigoTxt.setText(producto.getCodigoDescuento().getCodigo());
        if (!producto.getImagen().isBlank() && Files.exists(Paths.get(producto.getImagen()))) {
            System.out.println("Cargando imagen: " + producto.getImagen());
            Image image = new Image(new File(Resources.getPath(AppMain.class, "images") + producto.getImagen()).toURI().toString());
            System.out.println("Imagen cargada: " + image.getUrl());
            imageView.setImage(image);
        } else {
            System.out.println("No existe la imagen. Usando imagen por defecto");
            imageView.setImage(new Image(Resources.get(AppMain.class, "icons/maiz.png")));
            producto.setImagen(Resources.getPath(AppMain.class, "icons/maiz.png"));
            System.out.println("Nueva información de imagen: " + producto);
        }

    }

    /**
     * Determina el modo de edición.
     */
    public void setEditarModo(boolean editarModo) {
        this.editarModo = editarModo;
        System.out.println("Modo Editar: " + editarModo);
    }

    /**
     * Retorna true si el usuario hizo click en aceptar.
     */
    public boolean isAceptarClicked() {
        return aceptarClicked;
    }

    /**
     * Maneja el evento de click en el botón aceptar. También hace un control de datos introducidos.
     */
    @FXML
    private void onAceptarAction() throws SQLException, IOException {
        System.out.println("Aceptar");
        System.out.println("Validar datos");
        if (isInputValid()) {
            producto.setNombre(nombreTxt.getText());
            producto.setPrecio(Double.parseDouble((precioTxt.getText())));
            producto.setDescripcion(descripcionTxt.getText());
            producto.setDisponible(Utils.probarDisponibilidad(disponibleTxt.getText()));
            producto.setCodigoDescuento(new CodigoDescuento(codigoTxt.getText(), 50f));
            productoRepository.storeImagen(producto);
            aceptarClicked = true;
            if(editarModo) {
                producto.setUuid(idTxt.getText());
                productoRepository.update(producto);
            } else {
                producto.setUuid(UUID.randomUUID().toString());
                productoRepository.save(producto);
            }
            dialogStage.close();
        } else {
            System.out.println("Datos no validos");
        }
    }

    /**
     * Maneja el evento de click en el botón cancelar.
     */
    @FXML
    private void onCancelarAction() {
        System.out.println("Has pulsado Cancelar");
        dialogStage.close();
    }


    /**
     * Valida los datos introducidos en el formulario.
     *
     * @return true si los datos son correctos, false en caso contrario.
     */
    private boolean isInputValid() {
        boolean errorMessage = true;

        if (nombreTxt.getText() == null || nombreTxt.getText().isBlank()) {
            errorMessage = false;
        }else if (precioTxt.getText() == null || precioTxt.getText().isBlank() || !Utils.isPrecio(precioTxt.getText())) {
            errorMessage = false;
        }else if (descripcionTxt.getText() == null || descripcionTxt.getText().isBlank()) {
            errorMessage = false;
        }else if (disponibleTxt.getText() == null || disponibleTxt.getText().isBlank() || !Utils.isYesorNo(disponibleTxt.getText())) {
            errorMessage = false;
        }else if (codigoTxt.getText() == null || codigoTxt.getText().isBlank()) {
            errorMessage = false;
        }
        if(!errorMessage){
            Alert alert = Utils.getAlertErrorDetails("Error en datos", "Datos de producto incorrrectos", "Existen problemas al validar.", "Datos incorrectos");
            alert.showAndWait();
        }
        return errorMessage;
    }

    /**
     * Al pulsar el avatar se abre una ventana para seleccionar una imagen.
     */
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
