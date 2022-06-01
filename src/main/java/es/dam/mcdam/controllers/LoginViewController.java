/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.controllers;

import es.dam.mcdam.AppMain;
import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.managers.SceneManager;
import es.dam.mcdam.models.PersonaRegistrada;
import es.dam.mcdam.models.Tipo;
import es.dam.mcdam.repositories.PersonaRegistradaRepository;
import es.dam.mcdam.utils.Temas;
import es.dam.mcdam.utils.Themes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;



public class LoginViewController {
    //ESTADO
    PersonaRegistradaRepository personaRepository = PersonaRegistradaRepository.getInstance();
    DataBaseManager db = DataBaseManager.getInstance();
    @FXML
    private TextField identificacion;
    @FXML
    private TextField password;
    @FXML
    private Button validar;
    @FXML
    private Hyperlink register;
    @FXML
    private ImageView acercaDe;

    SceneManager sceneManager = SceneManager.getInstance(AppMain.class);

    //COMPORTAMIENTO

    /**
     * Inicializa la ventana de login.
     */
    @FXML
    public void initialize() {
        register.setOnAction(event -> {
            try {
                sceneManager.initRegisterView();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        validar.setOnAction(event -> {
            try {
                validarOnClick();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        acercaDe.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                sceneManager.initAcercaDe();
                event.consume();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Comprueba que el usuario y la contraseña introducidos son correctos y en base al tipo de usuario, abre una ventana u otra.
     * @throws SQLException
     */
    @FXML
    private void validarOnClick() throws SQLException {
        try{
            if(comprobarDatos(identificacion.getText(), password.getText())) {
                SceneManager sceneManager = SceneManager.get();
                if(comprobarTipoUsuario(identificacion.getText())) {
                    try{
                        Stage scene = (Stage) identificacion.getScene().getWindow();
                        scene.setResizable(false);
                        scene.hide();
                        sceneManager.initMenuAdmin();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    try{
                        sceneManager.initMenuCliente(personaRepository.findByCorreo(identificacion.getText()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al validar los datos");
                alert.setContentText("El usuario o la contraseña no son válidos.");
                alert.showAndWait();
            }
        }catch(SQLException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al validar los datos");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
    }

    /**
     * Comprueba que los datos introducidos son correctos.
     * @param usuario
     * @param contraseña
     * @return
     * @throws SQLException
     */
    private boolean comprobarDatos(String usuario, String contraseña) throws SQLException {
        String sql = "SELECT * FROM personaRegistrada WHERE correo = ? AND contraseña = ?";
        PersonaRegistradaRepository rep = PersonaRegistradaRepository.getInstance();
        final ObservableList<PersonaRegistrada> repository = FXCollections.observableArrayList();
        db.open();
        var result = db.select(sql, usuario, contraseña).orElseThrow(() -> new SQLException("Error al comprobar los datos. El usuario o la contaseña no son válidos."));
        while (result.next()) {
            repository.add(
                    new PersonaRegistrada(
                            result.getString("uuid"),
                            result.getString("nombre"),
                            result.getString("correo"),
                            result.getString("contraseña"),
                            Tipo.valueOf(result.getString("tipo"))

                    )
            );
        }
        db.close();
        if (repository.isEmpty()) {
            System.out.println("El usuario o la contraseña no son válidos.");
            return false;
        }
        return true;
    }

    /**
     * Comprueba que el usuario es de tipo administrador.
     * @param usuario
     * @return
     * @throws SQLException
     */
    private boolean comprobarTipoUsuario(String usuario) throws SQLException {
        String sql = "SELECT tipo FROM personaRegistrada WHERE correo = ?";
        db.open();
        var result = db.select(sql, usuario).orElseThrow(() -> new SQLException("Error al comprobar los datos."));
        while (result.next()) {
            if (result.getString("tipo").equals("ADMIN")) {
                db.close();
                return true;
            } else {
                db.close();
               return false;
            }
        }
        db.close();
        return false;
    }

    /**
     * Salir de la aplicación.
     */
    public void onSalirAction() {
        System.exit(0);
    }

    /**
     * Activa el modo oscuro.
     * @param actionEvent
     */
    public void onMenuModoOscuroAction(ActionEvent actionEvent) {
        System.out.println("Se ha pulsado accion Modena");
        Temas.set(this.acercaDe, Themes.OSCURO.get());
    }

    /**
     * Reeestablece el tema por defecto.
     * @param actionEvent
     */
    public void onMenuLimpiarAction(ActionEvent actionEvent) {
    }
}
