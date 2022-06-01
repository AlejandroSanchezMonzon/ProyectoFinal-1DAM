/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.controllers;

import es.dam.mcdam.AppMain;
import es.dam.mcdam.managers.SceneManager;
import es.dam.mcdam.models.CodigoDescuento;
import es.dam.mcdam.models.Producto;
import es.dam.mcdam.repositories.CodigoDescuentoRepository;
import es.dam.mcdam.repositories.ProductoRepository;
import es.dam.mcdam.utils.Resources;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Optional;

public class EdicionAdministradorViewController {
    //ESTADO
    private final ProductoRepository productosRepository = ProductoRepository.getInstance();
    private final CodigoDescuentoRepository codigoDescuentoRepository = CodigoDescuentoRepository.getInstance();
    boolean isProductoClicked = true;
    @FXML
    public Button eliminarButton;
    @FXML
    public Button editarButton;
    @FXML
    public MenuItem menuItemProducto;
    @FXML
    public MenuItem menuItemPromo;
    @FXML
    public TableView<Producto> productosTable;
    @FXML
    public TableColumn<Producto, ImageView> imagenColumnP;

    @FXML
    public TableColumn<Producto, String> nombreColumnP;
    @FXML
    public TableColumn<Producto, String> descripcionColumnP;
    @FXML
    public TableView<CodigoDescuento> codigoTable;
    @FXML
    public TableColumn<CodigoDescuento, String> codigoColumnC;
    @FXML
    public TableColumn<CodigoDescuento, String> porcenColumnC;
    private Stage dialogStage;
    @FXML
    private Button insertarButton;

    //COMPORTAMIENTO

    /**
     * Setter del Stage.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Inicializa la clase controladora.
     * Esta funcion se ejecuta automaticamente tras cargar la vista.
     */
    @FXML
    private void initialize() throws SQLException {
        codigoTable.setVisible(false);
        initData();
        menuItemProducto.setOnAction(event -> {
            try {
                initProductosView();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        menuItemPromo.setOnAction(event ->{
            try {
                initCodigosDescuentoView();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        insertarButton.setOnAction(event -> {
            try {
                openInsertar(dialogStage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Abre el pop-up para insertar un producto.
     */
    private void openInsertar(Stage stageEdicion) throws IOException {
        System.out.println("Se ha pulsado el botón insertar");
        if(isProductoClicked){
            Producto producto = new Producto();
            SceneManager.get().initProductoEditar(false, producto, stageEdicion);
        }else{
            CodigoDescuento codigoDescuento = new CodigoDescuento();
            SceneManager.get().initCodigoDescuentoEditar(false, codigoDescuento, stageEdicion);
        }


    }

    /**
     * Inicializa la tabla de códigos de descuento.
     */
    private void initCodigosDescuentoView() throws SQLException {
        isProductoClicked = false;
        productosTable.setVisible(false);
        codigoTable.setVisible(true);
        codigoTable.setItems(codigoDescuentoRepository.findAll());
        codigoColumnC.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        porcenColumnC.setCellValueFactory(new PropertyValueFactory<>("porcentajeDescuento"));
        editarButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                CodigoDescuento codigoDescuento = codigoTable.getSelectionModel().getSelectedItem();
                try {
                    openEditarCodigo(dialogStage, codigoDescuento);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        eliminarButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                CodigoDescuento codigoDescuento = codigoTable.getSelectionModel().getSelectedItem();
                eliminarCodigo(codigoDescuento);
            }
        });

    }

    /**
     * Inicializa la tabla de productos.
     */
    private void initProductosView() throws SQLException {
        //Controlamos visibilidad de las tablas de codigos y productos
        isProductoClicked = true;
        codigoTable.setVisible(false);
        productosTable.setVisible(true);
        //Inicializamos las columnas de la tabla de productos
        productosTable.setItems(productosRepository.findAll());
        imagenColumnP.setCellValueFactory((TableColumn.CellDataFeatures<Producto, ImageView> param) -> {
            if(param.getValue().getImagen() != null && param.getValue().getImagen().length() > 0) {
                ImageView imageView = new ImageView(new File(Resources.getPath(AppMain.class, "images") + param.getValue().getImagen()).toURI().toString());
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                return new SimpleObjectProperty<>(imageView);
            }else {
                var dirImage = Paths.get(System.getProperty("user.dir") + File.separator + "icons" + File.separator + "maiz.png");
                ImageView imageView = new ImageView(dirImage.toString());
                return new SimpleObjectProperty<>(imageView);
            }
        });
        nombreColumnP.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        descripcionColumnP.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        editarButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                    Producto producto = productosTable.getSelectionModel().getSelectedItem();
                    try {
                        openEditarProducto(dialogStage, producto);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
            }
        });
        eliminarButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Producto producto = productosTable.getSelectionModel().getSelectedItem();
                eliminarProducto(producto);
            }
        });
    }

    /**
     * Abre el pop-up de edición de un producto.
     */
    private void openEditarProducto(Stage stageEdicion, Producto producto) throws IOException {
        System.out.println("Se ha pulsado el botón insertar");
        SceneManager.get().initProductoEditar(true, producto, stageEdicion);

    }

    /**
     * Abre el pop-up de edición de un codigo.
     * @param stageEdicion
     * @param codigo
     * @throws IOException
     */
    private void openEditarCodigo(Stage stageEdicion, CodigoDescuento codigo) throws IOException {
        System.out.println("Se ha pulsado el botón insertar");
        SceneManager.get().initCodigoDescuentoEditar(true, codigo, stageEdicion);

    }

    /**
     * Elimina un producto de la base de datos.
     * @param item
     */
    private void eliminarProducto(Producto item) {
        System.out.println("Eliminar producto");
        System.out.println(item);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Borrar");
        alert.setContentText("¿Está seguro/a? Esta opción no se puede deshacer.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                productosRepository.delete(item);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Elimina un codigo de la base de datos.
     * @param item
     */
    private void eliminarCodigo(CodigoDescuento item) {
        System.out.println("Eliminar producto");
        System.out.println(item);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Borrar");
        alert.setContentText("¿Está seguro/a? Esta opción no se puede deshacer.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                codigoDescuentoRepository.delete(item);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Inicializa las tablas con sus respectivos datos.
     * @throws SQLException
     */
    private void initData() throws SQLException {
        productosTable.setItems(productosRepository.findAll());
        codigoTable.setItems(codigoDescuentoRepository.findAll());
    }
}
