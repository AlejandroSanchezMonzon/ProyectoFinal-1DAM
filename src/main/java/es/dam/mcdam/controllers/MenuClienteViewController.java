package es.dam.mcdam.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.dam.mcdam.AppMain;
import es.dam.mcdam.managers.SceneManager;
import es.dam.mcdam.models.*;
import es.dam.mcdam.repositories.CarritoRepository;
import es.dam.mcdam.repositories.PedidoRepository;
import es.dam.mcdam.repositories.ProductoRepository;
import es.dam.mcdam.utils.Resources;
import es.dam.mcdam.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
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
    private final ProductoRepository productosRepository = ProductoRepository.getInstance();
    private final CarritoRepository carritoRepository = CarritoRepository.getInstance();
    private final ObservableList<Integer> cantidadList = FXCollections.observableArrayList();
    @FXML
    private ListView<Producto> listProductos;
    @FXML
    private TableView<ItemCarrito> carritoTable;
    @FXML
    private TextField txtTotal;
    @FXML
    private TableColumn<ItemCarrito, String> imagenColumn;
    @FXML
    private TableColumn<ItemCarrito, String> productoColumn;
    @FXML
    private TableColumn<ItemCarrito, Double> precioColumn;
    @FXML
    private TableColumn<ItemCarrito, Integer> cantidadColumn;

    private PersonaRegistrada userActual;

    @FXML
    private void initialize() {
        cantidadList.addAll(1, 2, 3, 4, 5);
        initProductosView();
        initTableView();
        try {
            initData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initData() throws SQLException {
        listProductos.setItems(productosRepository.findAll());
        carritoTable.setItems(carritoRepository.findAll());
    }

    @FXML
    private void onEliminarAction(ActionEvent actionEvent) throws SQLException {
        ItemCarrito item = carritoTable.getSelectionModel().getSelectedItem();
        if (item != null) {
            carritoRepository.delete(item);
            carritoTable.refresh();
            calcularTotal();
        }
    }

    @FXML
    private void onTerminarAction(ActionEvent actionEvent) throws SQLException, IOException {
        if (carritoRepository.findAll().size() > 0) {
            System.out.println("Terminar");
            List<LineaPedido> compra = carritoRepository.findAll().stream()
                    .map(item -> new LineaPedido(item.getNombre(), item.getCantidad(), item.getPrecio(), item.getTotal()))
                    .collect(Collectors.toList());
            Pedido pedido = new Pedido(compra,userActual, "EFECTIVO" );
            Pedido pedidoAlmacenado = PedidoRepository.getInstance().save(pedido);
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
    private void initProductosView() {
        listProductos.setCellFactory(param -> new ListCell<>() {
            @Override
            public void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox();
                    hBox.setSpacing(10);

                    VBox vbox = new VBox();
                    vbox.setSpacing(10);
                    Label nombre = new Label(item.getNombre());
                    nombre.setStyle("-fx-font-weight: bold");
                    Label precio = new Label(Utils.redondeoPrecio(item.getPrecio()) + " €");
                    vbox.getChildren().addAll(nombre, precio);
                    ImageView imageView = new ImageView(new File(Resources.getPath(AppMain.class, "images") + item.getImagen()).toURI().toString());                    imageView.setFitHeight(75);
                    imageView.setFitWidth(50);
                    var dirImage = Paths.get(item.getImagen());
                    imageView.setImage(new Image(dirImage.toUri().toString()));

                    Button button = new Button("Añadir");
                    button.setOnAction(event -> {
                        try {
                            añadirProducto(item);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    hBox.setAlignment(Pos.CENTER);
                    hBox.getChildren().addAll(imageView, vbox, button);

                    setGraphic(hBox);
                }
            }
        });

        listProductos.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                Producto producto = listProductos.getSelectionModel().getSelectedItem();
                try {
                    añadirProducto(producto);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void añadirProducto(Producto item) throws SQLException {
        System.out.println("Añadir producto");
        System.out.println(item);
        ItemCarrito carritoItem = new ItemCarrito(item.getUuid(),item.getNombre(), item.getImagen(), item.getPrecio(), 1);
        carritoRepository.save(carritoItem);
        carritoTable.refresh();
        carritoTable.getSelectionModel().select(carritoItem);
        calcularTotal();
    }
    private void calcularTotal() {
        txtTotal.setText(carritoRepository.getTotal() + " €");
    }

    private void initTableView() {
        System.out.println("Inicializando columnas...");
        carritoTable.setEditable(false);
        productoColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        precioColumn.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());
        cantidadColumn.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty().asObject());
        setCantidadCell();
        imagenColumn.setCellValueFactory(cellData -> cellData.getValue().imagenProperty());
        setImageCell();
    }

    private void setImageCell() {
        imagenColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            public void updateItem(String item, boolean empty) {
                if (item != null) {
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    var dirImage = Paths.get(System.getProperty("user.dir") + File.separator + "img" + File.separator + item);
                    imageView.setImage(new Image(dirImage.toUri().toString()));
                    setGraphic(imageView);
                }
            }
        });
    }

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

    public void setClienteActual(PersonaRegistrada userActual) {
        this.userActual = userActual;
    }
}
