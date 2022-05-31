package es.dam.mcdam.controllers;

import es.dam.mcdam.AppMain;
import es.dam.mcdam.models.Pedido;
import es.dam.mcdam.models.PersonaRegistrada;
import es.dam.mcdam.repositories.PedidoRepository;
import es.dam.mcdam.repositories.PersonaRegistradaRepository;
import es.dam.mcdam.utils.Properties;
import es.dam.mcdam.utils.Resources;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.SQLException;

public class ConsultaAdministradorViewController {
    private final PersonaRegistradaRepository personaRepository = PersonaRegistradaRepository.getInstance();
    private final PedidoRepository pedidoRepository = PedidoRepository.getInstance();

    @FXML
    private TableView<PersonaRegistrada> usuariosTable;

    @FXML
    private TableColumn nombreColumn;

    @FXML
    private TableColumn tipoColumn;

    @FXML
    private TableView<Pedido> pedidoTable;

    @FXML
    private TableColumn numColumn;

    @FXML
    private TableColumn precioColumn;

    @FXML
    private MenuItem opcionPedido;

    @FXML
    private MenuItem opcionUsuario;


    @FXML
    private void initialize() throws SQLException {
        initData();
        opcionPedido.setOnAction(event -> {
            try {
                initPedidosView();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        opcionUsuario.setOnAction(event -> {
            try {
                initPersonasView();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void initPersonasView() throws SQLException {
        usuariosTable.setVisible(false);
        usuariosTable.setVisible(true);
        usuariosTable.setItems(personaRepository.findAll());
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }

    private void initPedidosView() throws SQLException {
        pedidoTable.setVisible(false);
        pedidoTable.setVisible(true);
        pedidoTable.setItems(pedidoRepository.findAll());
        numColumn.setCellValueFactory(new PropertyValueFactory<>("uuid"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private void initData() throws SQLException {
        usuariosTable.setItems(personaRepository.findAll());
        pedidoTable.setItems(pedidoRepository.findAll());
    }


}
