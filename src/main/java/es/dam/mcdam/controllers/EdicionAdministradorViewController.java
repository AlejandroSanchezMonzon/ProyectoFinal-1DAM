package es.dam.mcdam.controllers;

import es.dam.mcdam.AppMain;
import es.dam.mcdam.managers.SceneManager;
import es.dam.mcdam.models.CodigoDescuento;
import es.dam.mcdam.models.Producto;
import es.dam.mcdam.repositories.CodigoDescuentoRepository;
import es.dam.mcdam.repositories.ProductoRepository;
import es.dam.mcdam.utils.Properties;
import es.dam.mcdam.utils.Resources;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Optional;

public class EdicionAdministradorViewController {
    private final ProductoRepository productosRepository = ProductoRepository.getInstance();
    private final CodigoDescuentoRepository codigoDescuentoRepository = CodigoDescuentoRepository.getInstance();
    private Stage dialogStage;
    @FXML
    private ListView<Producto> listaProductos;
    @FXML
    private ListView<CodigoDescuento> listaCodigoDescuento;
    @FXML
    private Button productoButton;
    @FXML
    private Button codigoPromocionalButton;
    @FXML
    private Button insertarButton;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    @FXML
    private void initialize() throws SQLException {
        productoButton.setOnAction(event -> {
            initProductosView();
            try {
                initData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        codigoPromocionalButton.setOnAction(event ->{
                    initCodigosDescuentoView();
            try {
                initData();
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

    private void openInsertar(Stage stageEdicion) throws IOException {
        System.out.println("Se ha pulsado el botón insertar");
        Producto producto = new Producto();
        SceneManager.get().initProductoEditar(false, producto, stageEdicion);

    }

    private void initCodigosDescuentoView() {
        listaCodigoDescuento.setCellFactory(param -> new ListCell<>() {
            @Override
            public void updateItem(CodigoDescuento item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox();
                    hBox.setSpacing(10);

                    VBox vbox = new VBox();
                    vbox.setSpacing(10);
                    Label codigo = new Label(item.getCodigo());
                    Label descuento = new Label(Float.toString(item.getPorcentajeDescuento()) + " %");
                    vbox.getChildren().addAll(codigo, descuento);
                    // Imagen
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(75);
                    imageView.setFitWidth(50);
                    //System.out.println(dirImage);
                    imageView.setImage(new Image(Resources.get(AppMain.class, Properties.CODIGODESCUENTO_DEFAULT)));
                    // Boton eliminar
                    Button botonEliminar = new Button("Eliminar");
                    botonEliminar.setOnAction(event -> {
                        eliminarCodigo(item);

                    });
                    botonEliminar.setStyle("-fx-background-color: #ef5858");
                    botonEliminar.setTextFill(Color.WHITE);
                    // Boton actualizar
                    Button botonActualizar = new Button("Actualizar");
                    botonActualizar.setOnAction(event -> {
                        actualizarCodigo(item);
                    });
                    botonActualizar.setStyle("-fx-background-color: black");
                    botonActualizar.setTextFill(Color.WHITE);
                    hBox.setAlignment(Pos.CENTER);
                    hBox.getChildren().addAll(imageView, vbox, botonActualizar, botonEliminar);

                    setGraphic(hBox);
                }
            }
        });

        listaCodigoDescuento.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                CodigoDescuento codigoDescuento = listaCodigoDescuento.getSelectionModel().getSelectedItem();
                actualizarCodigo(codigoDescuento);
            }
        });
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
        listaCodigoDescuento.refresh();
    }

    private void initProductosView(){
        listaProductos.setCellFactory(param -> new ListCell<>(){
            @Override
            public void updateItem(Producto item, boolean empty){
                super.updateItem(item,empty);
                    HBox hBox = new HBox();
                    hBox.setSpacing(10);

                    VBox vbox = new VBox();
                    vbox.setSpacing(10);
                    Label nombre = new Label(item.getNombre());
                    Label precio = new Label(Float.toString(item.getPrecio()));
                    Label descripcion = new Label(item.getDescripcion());
                    Label disponible = new Label(String.valueOf(item.getDisponible()));
                    Label codigoDescuento = new Label(item.getCodigoDescuento().getCodigo());
                    vbox.getChildren().addAll(nombre, precio, descripcion, disponible, codigoDescuento);
                    // Imagen
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(75);
                    imageView.setFitWidth(50);
                    var dirImage = Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "es" + File.separator + "dam" + File.separator + "mcdam" + File.separator + "images" + File.separator + item.getImagen());
                    //System.out.println(dirImage);

                    // Boton eliminar
                    Button botonEliminar = new Button("Eliminar");
                    botonEliminar.setOnAction(event -> {
                        eliminarProducto(item);
                    });
                    botonEliminar.setStyle("-fx-background-color: #ef5858");
                    botonEliminar.setTextFill(Color.WHITE);
                    // Boton actualizar
                    Button botonActualizar = new Button("Actualizar");
                    botonActualizar.setOnAction(event -> {
                        actualizarProducto(item);
                    });
                    botonActualizar.setStyle("-fx-background-color: black");
                    botonActualizar.setTextFill(Color.WHITE);
                    hBox.setAlignment(Pos.CENTER);
                    hBox.getChildren().addAll(imageView, vbox, botonActualizar, botonEliminar);
                    setGraphic(hBox);
                }

        });
        listaProductos.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Producto producto =listaProductos.getSelectionModel().getSelectedItem();
                actualizarProducto(producto);
            }
        });
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
        listaProductos.refresh();
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
        listaProductos.setItems(productosRepository.findAll());
        listaCodigoDescuento.setItems(codigoDescuentoRepository.findAll());

    }






}
