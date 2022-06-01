/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.controllers;

import es.dam.mcdam.AppMain;
import es.dam.mcdam.models.PersonaRegistrada;
import es.dam.mcdam.models.Producto;
import es.dam.mcdam.repositories.CodigoDescuentoRepository;
import es.dam.mcdam.repositories.PersonaRegistradaRepository;
import es.dam.mcdam.repositories.ProductoRepository;
import es.dam.mcdam.utils.Resources;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;

public class ConsultaAdministradorViewController {
    //ESTADO
    private final PersonaRegistradaRepository personaRepository = PersonaRegistradaRepository.getInstance();
    private final ProductoRepository productoRepository = ProductoRepository.getInstance();
    private final CodigoDescuentoRepository codigoDescuentoRepository = CodigoDescuentoRepository.getInstance();
    @FXML
    private TableView<PersonaRegistrada> usuariosTable;
    @FXML
    private TableColumn nombreColumn;
    @FXML
    private TableColumn correoColumn;
    @FXML
    private TableColumn contraseñaColumn;
    @FXML
    private TableColumn tipoColumn;
    @FXML
    private TableColumn uuidColumn;
    @FXML
    private TableView<Producto> productosTable;
    @FXML
    public TableColumn<Producto, ImageView> imagenColumnP;
    @FXML
    public TableColumn<Producto, String> nombreColumnP;
    @FXML
    public TableColumn<Producto, String> descripcionColumnP;
    @FXML
    private MenuItem opcionProducto;
    @FXML
    private MenuItem opcionUsuario;

    //COMPORTAMIENTO

    /**
     * Método que inicializa la vista.
     */
    @FXML
    private void initialize() throws SQLException {
        initData();
        opcionProducto.setOnAction(event -> {
            try {
                initProductosView();
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

    /**
     * Método que inicializa la vista de productos con los campos en activo.
     */
    private void initPersonasView() throws SQLException {
        productosTable.setVisible(false);
        usuariosTable.setVisible(true);
        usuariosTable.setItems(personaRepository.findAll());
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        correoColumn.setCellValueFactory(new PropertyValueFactory<>("correo"));
        contraseñaColumn.setCellValueFactory(new PropertyValueFactory<>("contraseña"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        uuidColumn.setCellValueFactory(new PropertyValueFactory<>("uuid"));
    }

    /**
     * Método que inicializa la vista de productos con los campos en activo.
     */
    private void initProductosView() throws SQLException {
        usuariosTable.setVisible(false);
        productosTable.setVisible(true);
        productosTable.setItems(productoRepository.findAll());
        imagenColumnP.setCellValueFactory((TableColumn.CellDataFeatures<Producto, ImageView> param) -> {
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
        nombreColumnP.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        descripcionColumnP.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    }

    /**
     * Método que inicializa los datos de la vista.
     */
    private void initData() throws SQLException {
        usuariosTable.setItems(personaRepository.findAll());
        productosTable.setItems(productoRepository.findAll());
    }
}
