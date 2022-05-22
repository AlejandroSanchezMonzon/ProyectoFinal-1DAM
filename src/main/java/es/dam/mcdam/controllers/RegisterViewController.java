package es.dam.mcdam.controllers;

import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class RegisterViewController {


    DataBaseManager db = DataBaseManager.getInstance();
    @FXML
    private TextField identificacion;
    @FXML
    private TextField password;
    @FXML
    private Button validar;

    @FXML
    private void validarOnClick() throws SQLException {
        if (comprobarDatos(identificacion.getText(), password.getText())){
            String sql = "INSERT INTO usuarios (identificacion, contraseña) VALUES ('"+identificacion.getText()+"','"+password.getText()+"')";
            db.open();
            db.insert(sql);
            db.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro");
            alert.setHeaderText("Registro correcto");
            alert.setContentText("Usuario registrado correctamente");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registro");
            alert.setHeaderText("Registro incorrecto");
            alert.setContentText("Datos incorrectos");
            alert.showAndWait();
        }
    }

    private boolean comprobarDatos(String usuario, String contraseña){
        if(Utils.isEmail(usuario) && Utils.isPassword(contraseña)){
            return true;
        }else{
            return false;
        }
    }

}
