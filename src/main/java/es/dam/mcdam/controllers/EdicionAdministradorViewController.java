package es.dam.mcdam.controllers;

import es.dam.mcdam.AppMain;
import es.dam.mcdam.managers.SceneManager;
import es.dam.mcdam.models.CodigoDescuento;
import es.dam.mcdam.models.Producto;
import es.dam.mcdam.repositories.CodigoDescuentoRepository;
import es.dam.mcdam.repositories.ProductoRepository;
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
import es.dam.mcdam.utils.Properties;

public class EdicionAdministradorViewController {
    private final ProductoRepository productosRepository = ProductoRepository.getInstance();
    private final CodigoDescuentoRepository codigoDescuentoRepository = CodigoDescuentoRepository.getInstance();
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
    public TableColumn descripcionColumnP;
    @FXML
    public TableView<CodigoDescuento> codigoTable;
    @FXML
    public TableColumn codigoColumn;
    @FXML
    public TableColumn porcentajeColumn;
    private Stage dialogStage;


    @FXML
    private Button insertarButton;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
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
        menuItemPromo.setOnAction(event ->{initCodigosDescuentoView();});
        insertarButton.setOnAction(event -> {
            try {
                openInsertar(dialogStage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void openInsertar(Stage stageEdicion) throws IOException {
        System.out.println("Se ha pulsado el botón insertar");
        Producto producto = new Producto();
        SceneManager.get().initProductoEditar(false, producto, stageEdicion);

    }

    private void initCodigosDescuentoView() {
        productosTable.setVisible(false);
        codigoTable.setVisible(true);


    }
    private void actualizarCodigo(CodigoDescuento item){
        System.out.println("Actualizar código descuento");
        System.out.println(item);
        try {
            codigoDescuentoRepository.update(item);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void eliminarCodigo(CodigoDescuento item){
        System.out.println("Eliminar código descuento");
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
        //menuItemPromo.refresh();
    }

    private void initProductosView() throws SQLException {
        codigoTable.setVisible(false);
        productosTable.setVisible(true);
        productosTable.setItems(productosRepository.findAll());
        imagenColumnP.setCellValueFactory((TableColumn.CellDataFeatures<Producto, ImageView> param) -> {
            if(param.getValue().getImagen() != null && param.getValue().getImagen().length() > 0) {
                ImageView imageView = new ImageView(param.getValue().getImagen());
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                return new SimpleObjectProperty<>(imageView);
            }else {
                var dirImage = Paths.get(System.getProperty("user.dir") + File.separator + "icons" + File.separator + "maiz.png");
                ImageView imageView = new ImageView(dirImage.toString());
                return new SimpleObjectProperty<>(imageView);
            }
        });
        descripcionColumnP.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        editarButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                    Producto producto = productosTable.getSelectionModel().getSelectedItem();
                    try {
                        openEditar(dialogStage, producto);
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

    private void openEditar(Stage stageEdicion, Producto producto) throws IOException {
        System.out.println("Se ha pulsado el botón insertar");
        SceneManager.get().initProductoEditar(true, producto, stageEdicion);

    }

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
        //listaProductos.refresh();
    }

    private void actualizarProducto(Producto item) {
        System.out.println("Actualizar producto");
        System.out.println(item);
        try {
            productosRepository.update(item);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initData() throws SQLException {
        productosTable.setItems(productosRepository.findAll());
        codigoTable.setItems(codigoDescuentoRepository.findAll());

    }






}
