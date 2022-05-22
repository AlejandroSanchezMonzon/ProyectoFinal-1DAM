package es.dam.mcdam.controllers;

import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.managers.SceneManager;
import es.dam.mcdam.utils.Utils;
import es.dam.mcdam.views.Views;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;



public class LoginViewController {

    DataBaseManager db = DataBaseManager.getInstance();
    @FXML
    private TextField identificacion;
    @FXML
    private TextField password;
    @FXML
    private Button validar;

    @FXML
    private void validarOnClick() throws SQLException {
        try{
            if(comprobarDatos(identificacion.getText(), password.getText())) {
                SceneManager sceneManager = SceneManager.get();
                if(comprobarTipoUsuario(identificacion.getText())) {
                    try{
                        sceneManager.initMenuAdmin();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    try{
                        sceneManager.initMenuCliente();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }catch(SQLException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al validar los datos");
                alert.setContentText(e.getMessage());
            }
    }

    private boolean comprobarDatos(String usuario, String contraseña) throws SQLException {
        String sql = "SELECT nombre, contraseña FROM personaRegistrada WHERE nombre = ? AND contraseña = ?";
        db.open();
        var result = db.select(sql, usuario, contraseña).orElseThrow(() -> new SQLException("Error al comprobar los datos. El usuario o la contaseña no son válidos."));
        db.close();
        return true;
    }

    private boolean comprobarTipoUsuario(String usuario) throws SQLException {
        String sql = "SELECT tipo FROM personaRegistrada WHERE nombre = ?";
        boolean isAdmin = false;
        db.open();
        var result = db.select(sql, usuario).orElseThrow(() -> new SQLException("Error al comprobar los datos."));
        while (result.next()) {
            if (result.getString("tipo").equals("ADMIN")) {
                isAdmin = true;
            } else {
                isAdmin = false;
            }
        }
        db.close();
        return isAdmin;
    }
}
