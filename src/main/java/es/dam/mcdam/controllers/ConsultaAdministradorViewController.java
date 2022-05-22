package es.dam.mcdam.controllers;

import es.dam.mcdam.models.Pedido;
import es.dam.mcdam.models.PersonaRegistrada;
import es.dam.mcdam.repositories.PedidoRepository;
import es.dam.mcdam.repositories.PersonaRegistradaRepository;
import es.dam.mcdam.utils.Properties;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.SQLException;

public class ConsultaAdministradorViewController {
    private final PersonaRegistradaRepository personaRepository = PersonaRegistradaRepository.getInstance();
    private final PedidoRepository pedidoRepository = PedidoRepository.getInstance();

    @FXML
    private ListView<PersonaRegistrada> listaPersonas;

    @FXML
    private ListView<Pedido> listaPedidos;

    @FXML
    private Button pedido;

    @FXML
    private Button usuariosRegistrados;

    @FXML
    private void initialize() throws SQLException {
        initData();
        pedido.setOnAction(event -> initPedidosView());
        usuariosRegistrados.setOnAction(event -> initPersonasView());
    }

    private void initPedidosView() {
        listaPedidos.setCellFactory(param -> new ListCell<>(){
            @Override
            public void updateItem(Pedido item, boolean empty){
                super.updateItem(item,empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox();
                    hBox.setSpacing(10);

                    VBox vbox = new VBox();
                    vbox.setSpacing(10);
                    Label cliente = new Label(item.getCliente().getNombre());
                    Label compra = new Label(item.getCompra().toString());
                    Label metodoPago = new Label(item.getMetodoPago());
                    Label total = new Label(Float.toString(item.getTotal()));
                    Label id = new Label(item.getUuid());
                    vbox.getChildren().addAll(cliente, compra, metodoPago, total, id);
                    // Imagen
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(75);
                    imageView.setFitWidth(50);
                    imageView.setImage(new Image(Properties.PEDIDO_DEFAULT));
                    hBox.setAlignment(Pos.CENTER);
                    hBox.getChildren().addAll(imageView, vbox);
                    setGraphic(hBox);
                }
            }
        });
    }

    private void initPersonasView() {
        listaPersonas.setCellFactory(param -> new ListCell<>(){
            @Override
            public void updateItem(PersonaRegistrada item, boolean empty){
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
                    Label correo = new Label(item.getCorreo());
                    Label contraseña = new Label(item.getContraseña());
                    Label tipo = new Label(item.getTipo().toString());
                    Label id = new Label(item.getUuid());
                    vbox.getChildren().addAll(nombre, correo, contraseña, tipo, id);
                    // Imagen
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(75);
                    imageView.setFitWidth(50);
                    imageView.setImage(new Image(Properties.USER_DEFAULT));
                    hBox.setAlignment(Pos.CENTER);
                    hBox.getChildren().addAll(imageView, vbox);
                    setGraphic(hBox);
                }
            }
        });
    }

    private void initData() throws SQLException {
        listaPersonas.setItems(personaRepository.findAll());
        listaPedidos.setItems(pedidoRepository.findAll());
    }


}
