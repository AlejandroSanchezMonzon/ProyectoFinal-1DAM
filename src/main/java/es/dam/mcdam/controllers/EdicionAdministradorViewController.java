package es.dam.mcdam.controllers;

import es.dam.mcdam.AppMain;
import es.dam.mcdam.managers.SceneManager;
import es.dam.mcdam.models.CodigoDescuento;
import es.dam.mcdam.models.Producto;
import es.dam.mcdam.repositories.CodigoDescuentoRepository;
import es.dam.mcdam.repositories.ProductoRepository;
import es.dam.mcdam.utils.Properties;
import es.dam.mcdam.utils.Resources;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Optional;

public class EdicionAdministradorViewController {
    private final ProductoRepository productosRepository = ProductoRepository.getInstance();
    private final CodigoDescuentoRepository codigoDescuentoRepository = CodigoDescuentoRepository.getInstance();

    @FXML
    private ListView<Producto> listaProductos;
    @FXML
    private ListView<CodigoDescuento> listaCodigoDescuento;
    @FXML
    private Button producto;
    @FXML
    private Button codigoPromocional;

    @FXML
    private Label nombreLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label precioLabel;
    @FXML
    private Label descripcionLabel;
    @FXML
    private Label disponibleLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label codigoDescuentoAsociadoLabel;

    @FXML
    private ImageView imagenProductoView;

    @FXML
    private Label codidoLabel;
    @FXML
    private Label descuentoLabel;

    @FXML
    private ImageView imagenCodigoView;


    @FXML
    private void initialize() throws SQLException {
        producto.setOnAction(event -> initProductosView());
        codigoPromocional.setOnAction(event -> initCodigosDescuentoView());
        initData();

    }

    private void onInsertarAction() throws IOException {
        System.out.println("Se ha pulsado el botón insertar");
        Producto producto = new Producto();
        boolean aceptarClicked = SceneManager.get().initProductoEditar(false, producto);
        if (aceptarClicked) {
            salvarImagen(producto);
            try {
                productosRepository.save(producto);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            setDataInfo(producto);
        }
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
            // personasTable.getItems().remove(p);
        }

    }

    private void initProductosView(){
        listaProductos.setCellFactory(param -> new ListCell<>(){
            @Override
            public void updateItem(Producto item, boolean empty){
                super.updateItem(item,empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
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

    private void initData() {
        try {
            listaProductos.setItems(productosRepository.findAll());
            listaCodigoDescuento.setItems(codigoDescuentoRepository.findAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setDataInfo(Producto producto) {
        System.out.println("Se ha seleccionado el producto: " + producto);
        nombreLabel.setText(producto.getNombre());
        idLabel.setText(producto.getUuid());
        precioLabel.setText(Float.toString(producto.getPrecio()));
        descripcionLabel.setText(producto.getDescripcion());
        disponibleLabel.setText(String.valueOf(producto.getDisponible()));
        codigoDescuentoAsociadoLabel.setText(producto.getCodigoDescuento().getCodigo());
        // La imagen, si no eiste cargamos la de por defecto, si no la que tiene
        if (!producto.getImagen().isBlank() && Files.exists(Paths.get(producto.getImagen()))) {
            System.out.println("Cargando imagen: " + producto.getImagen());
            Image image = new Image(new File(producto.getImagen()).toURI().toString());
            System.out.println("Imagen cargada: " + image.getUrl());
            imagenProductoView.setImage(image);
        } else {
            System.out.println("No existe la imagen. Usando imagen por defecto");
            imagenProductoView.setImage(new Image(Resources.get(AppMain.class, "icons/maiz.png")));
            producto.setImagen(Resources.getPath(AppMain.class, "icons/maiz.png"));
            System.out.println("Nueva información de imagen: " + producto);
        }
    }

    private void salvarImagen(Producto producto) {
        try {
            productosRepository.storeImagen(producto);
        } catch (IOException e) {
            System.out.println("No se ha podido salvar la imagen del producto: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
