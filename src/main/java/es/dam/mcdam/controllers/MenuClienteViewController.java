/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.dam.mcdam.AppMain;
import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.managers.SceneManager;
import es.dam.mcdam.models.*;
import es.dam.mcdam.repositories.*;
import es.dam.mcdam.services.Storage;
import es.dam.mcdam.utils.Resources;
import es.dam.mcdam.utils.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class MenuClienteViewController {
    //ESTADO
    private final DataBaseManager db = DataBaseManager.getInstance();
    private final Storage storage = Storage.getInstance();
    private final ProductoRepository productosRepository = ProductoRepository.getInstance(db, storage);
    private final CarritoRepository carritoRepository = CarritoRepository.getInstance();
    private final PedidoRepository pedidoRepository = PedidoRepository.getInstance(db);
    private final ObservableList<Integer> cantidadList = FXCollections.observableArrayList();



    @FXML
    private TableView<Producto> productoTable;
    @FXML
    private TableColumn<Producto, ImageView> imagenPColumn;
    @FXML
    private TableColumn<Producto, String> productoPColumn;
    @FXML
    private TableColumn<Producto, Double> precioPColumn;
    @FXML
    private TableColumn<Producto, Button> botonColumn;
    @FXML
    private TableView<ItemCarrito> carritoTable;
    @FXML
    private TextField txtTotal;
    @FXML
    private TableColumn<ItemCarrito, ImageView> imagenColumn;
    @FXML
    private TableColumn<ItemCarrito, String> productoColumn;
    @FXML
    private TableColumn<ItemCarrito, Double> precioColumn;
    @FXML
    private TableColumn<ItemCarrito, Integer> cantidadColumn;

    private PersonaRegistrada userActual;

    //COMPORTAMIENTO

    /**
     * Inicializa la vista del menú del cliente.
     */
    @FXML
    private void initialize() {
        cantidadList.addAll(1, 2, 3, 4, 5);
        try {
            initProductosView();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            initTableView();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            initData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inicializa con los datos de ka base de datos.
     * @throws SQLException
     */
    private void initData() throws SQLException {
        productoTable.setItems(productosRepository.findAll());
        carritoTable.setItems(carritoRepository.findAll());
    }

    /**
     * Elimina un producto del carrito.
     * @param actionEvent
     * @throws SQLException
     */
    @FXML
    private void onEliminarAction(ActionEvent actionEvent) throws SQLException {
        ItemCarrito item = carritoTable.getSelectionModel().getSelectedItem();
        if (item != null) {
            carritoRepository.delete(item);
            carritoTable.refresh();
            calcularTotal();
        }
    }

    /**
     * Al pulsar el boton de terminar, se abre la ventana de pago.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    private void onTerminarAction(ActionEvent actionEvent) throws SQLException, IOException {
        if (carritoRepository.findAll().size() > 0) {
            System.out.println("Terminar");
            List<LineaPedido> compra = carritoRepository.findAll().stream()
                    .map(item -> new LineaPedido(item.getNombre(), item.getCantidad(), item.getPrecio(), item.getTotal()))
                    .collect(Collectors.toList());
            Pedido pedido = new Pedido(compra,userActual, "EFECTIVO" );
            Pedido pedidoAlmacenado = pedidoRepository.save(pedido);
            if (pedidoAlmacenado != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Iniciando proceso pago.");
                alert.setHeaderText("Total: " + pedidoAlmacenado.getTotal() + " €");
                System.out.println("Venta realizada con éxito. Total " + pedidoAlmacenado.getTotal() + " €");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    System.out.println(gson.toJson(pedidoAlmacenado));
                    carritoRepository.deleteAll();
                    carritoTable.refresh();
                    calcularTotal();
                    SceneManager.get().initProcesoPago(pedido);
                    Stage scene = (Stage) txtTotal.getScene().getWindow();
                    scene.hide();
                } else {
                    System.out.println("Cancelado");
                }
            }
        } else {
            System.out.println("No hay productos en el carrito");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No hay productos en el carrito");
            alert.setContentText("Por favor, añada productos al carrito");
            alert.showAndWait();
        }
    }

    /**
     * Se inicializa la tabla de productos.
     */
    private void initProductosView() throws SQLException {
        productoTable.setItems(productosRepository.findAll());
        imagenPColumn.setCellValueFactory((TableColumn.CellDataFeatures<Producto, ImageView> param) -> {
            if(param.getValue().getImagen() != null && param.getValue().getImagen().length() > 0) {
                ImageView imageView =new ImageView(new File(Resources.getPath(AppMain.class, "images") + param.getValue().getImagen()).toURI().toString());
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                return new SimpleObjectProperty<>(imageView);
            }else {
                var dirImage = Paths.get(System.getProperty("user.dir") + File.separator + "icons" + File.separator + "maiz.png");
                ImageView imageView = new ImageView(dirImage.toString());
                return new SimpleObjectProperty<>(imageView);
            }
        });
        productoPColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioPColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        botonColumn.setCellValueFactory(param -> {
            Button button = new Button("Añadir");
            button.setStyle("-fx-background-color: #9cb7e5");
            button.setOnAction(event -> {
                try {
                    Producto producto = productosRepository.findAll().stream()
                            .filter(p -> p.getNombre().equals(param.getValue().getNombre()))
                            .findFirst().get();
                    añadirProducto(producto);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            return new SimpleObjectProperty<>(button);
        });

    }

    /**
     * Método que añade un producto al carrito.
     * @param item
     * @throws SQLException
     */
    private void añadirProducto(Producto item) throws SQLException {
        System.out.println("Añadir producto");
        System.out.println(item);
        ItemCarrito carritoItem = new ItemCarrito(item.getUuid(),item.getNombre(), item.getImagen(), item.getPrecio(), 1);
        carritoRepository.save(carritoItem);
        carritoTable.refresh();
        carritoTable.getSelectionModel().select(carritoItem);
        calcularTotal();
    }

     /**
     * Método que calcula el total del carrito.
     */
    private void calcularTotal() {
        txtTotal.setText(carritoRepository.getTotal() + " €");
    }

    /**
     * Método que inicializa la tabla de carrito.
     */
    private void initTableView() throws SQLException {
        System.out.println("Inicializando columnas...");
        carritoTable.setItems(carritoRepository.findAll());
        productoColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        precioColumn.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());
        cantidadColumn.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty().asObject());
        setCantidadCell();
        imagenColumn.setCellValueFactory((TableColumn.CellDataFeatures<ItemCarrito, ImageView> param) -> {
            if(param.getValue().getImagen() != null && param.getValue().getImagen().length() > 0) {
                ImageView imageView =new ImageView(new File(Resources.getPath(AppMain.class, "images") + param.getValue().getImagen()).toURI().toString());
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                return new SimpleObjectProperty<>(imageView);
            }else {
                var dirImage = Paths.get(System.getProperty("user.dir") + File.separator + "icons" + File.separator + "maiz.png");
                ImageView imageView = new ImageView(dirImage.toString());
                return new SimpleObjectProperty<>(imageView);
            }
        });
    }


    /**
     * Método que establece la celda de la cantidad.
     */
    private void setCantidadCell() {
        cantidadColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            public void updateItem(Integer item, boolean empty) {
                if (item != null) {
                    ChoiceBox choice = new ChoiceBox(cantidadList);
                    choice.getSelectionModel().select(cantidadList.indexOf(item));
                    choice.setOnAction(event -> {
                        var cantidad = (Integer) choice.getSelectionModel().getSelectedItem();
                        var carritoItem = getTableView().getItems().get(getIndex());
                        carritoItem.setCantidad(cantidad);
                        calcularTotal();
                    });
                    setGraphic(choice);
                }
            }
        });
    }

    /**
     * Método que establece el cliente de la sesión.
     * @param userActual
     */
    public void setClienteActual(PersonaRegistrada userActual) {
        this.userActual = userActual;
    }
}
