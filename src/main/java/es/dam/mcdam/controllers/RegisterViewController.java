/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.controllers;

import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.UUID;

public class RegisterViewController {
    //ESTADO
    DataBaseManager db = DataBaseManager.getInstance();
    @FXML
    private TextField nombre;
    @FXML
    private TextField identificacion;
    @FXML
    private TextField password;
    @FXML
    private Button validar;

    //COMPORTAMIENTO

    /**
     * Inicializa la ventana de registro.
     */
    @FXML
    private void initialize() {
        validar.setOnAction(event -> {
            try {
                validarOnClick();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Comprueba que los datos introducidos en la ventana de registro son correctos.
     * @throws SQLException
     */
    @FXML
    private void validarOnClick() throws SQLException {
        if (comprobarDatos(identificacion.getText(), password.getText())){
            String sql = "INSERT INTO personaRegistrada (nombre, correo, contraseña, tipo, uuid) VALUES ('"+nombre.getText()+"','"+identificacion.getText()+"','"+password.getText()+"', 'USER', '"+UUID.randomUUID().toString()+"')";
            db.open();
            db.insert(sql);
            db.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro");
            alert.setHeaderText("Registro correcto");
            alert.setContentText("Usuario registrado correctamente");
            alert.showAndWait();

            Stage scene = (Stage) validar.getScene().getWindow();
            scene.hide();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registro");
            alert.setHeaderText("Registro incorrecto");
            alert.setContentText("Datos incorrectos");
            alert.showAndWait();
        }
    }

    /**
     * Comprueba los datos introducidos en la ventana de registro.
     * @param usuario
     * @param contraseña
     * @return
     */
    private boolean comprobarDatos(String usuario, String contraseña){
        if(Utils.isEmail(usuario) && Utils.isPassword(contraseña)){
            return true;
        }else{
            return false;
        }
    }
}
