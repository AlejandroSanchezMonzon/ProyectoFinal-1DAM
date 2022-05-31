package es.dam.mcdam.controllers;

import es.dam.mcdam.utils.Properties;
import es.dam.mcdam.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.*;

public class ProcesoPagoViewController {

    @FXML
    public Button confirmarButton;
    @FXML
    public TextField cvsTxt;
    @FXML
    public DatePicker caducidadPick;
    @FXML
    public TextField tarjetaTxt;
    @FXML
    public Button tarjetaButton;
    @FXML
    public Button efectivoButton;
    @FXML
    public VBox datosTarjeta;

    boolean isEfectivo = true;

    public void initialize() {
        datosTarjeta.setVisible(false);
        efectivoButton.setOnAction(event -> {
            System.out.println("Efectivo");
            isEfectivo = true;
            datosTarjeta.setVisible(false);
        });
        tarjetaButton.setOnAction(event -> {
            System.out.println("Tarjeta");
            isEfectivo = false;
            datosTarjeta.setVisible(true);
        });
        confirmarButton.setOnAction(event -> {
            System.out.println("Confirmar");
            try {
                confirmar(isEfectivo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private boolean comprobarDatos() {
        return (Utils.isTarjetaCredito(tarjetaTxt.getText()) && Utils.isCaducidad(caducidadPick.getValue()) && Utils.isCvs(cvsTxt.getText()));
    }

    private void confirmar(boolean isEfectivo) {
        if (isEfectivo) {
            System.out.println("Efectivo");
            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Efectivo");
                alert.setContentText("Acuda a la caja para pagar su pedido.");
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Tarjeta");
            if (comprobarDatos()) {
                mostrarFactura();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Datos incorrectos");
                alert.showAndWait();
            }
        }
    }

    private void mostrarFactura() {
        String str = "";
        String h = "";
        String t = "";
        try {
            FileOutputStream fos = new FileOutputStream( Properties.DATA_DIR+ File.separator + "factura.html");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            h = "<!DOCTYPE html>\n" +
                    "<html lang=\"es-ES\">\n" +
                    "    <head> \n" +
                    "            <title>Recibo mcdam</title>\n" +
                    "            <meta charset=\"utf-8\">\n" +
                    "            <link rel=\"stylesheet\" href=" + Properties.DATA_DIR + File.separator + "estilos.css\">\n" +
                    "\n" +
                    "    </head>\n" +
                    "    <body>  \n" +
                    "        <div id=\"contenedor\">\n" +
                    "\n" +
                    "            <div id=\"cabecera\">\n" +
                    "\n" +
                    "            <img src=" + Properties.IMAGES_DIR + File.separator + "McDAMNoFondo1.png\" height=\"200\" width=\"200\">\n" +
                    "\n" +
                    "            <p>McDam</p>\n" +
                    "\n" +
                    "            </div> <!-- /Cabezera-->\n" +
                    "\n" +
                    "            <div id=\"contenido\">\n" +
                    "\n" +
                    "                \n" +
                    "                <p> IES Luis Vives</p>\n" +
                    "                <p>Numero de tienda: 773-260-6457</p>\n" +
                    "            \n" +
                    "\n" +
                    "            </div><!-- /Contenido-->\n" +
                    "\n" +
                    "\n" +
                    "            <div id=\"contenido2\">\n" +
                    "                <table class=\"venta\">\n" +
                    "                    <tr>\n" +
                    "                        <td>\n" +
                    "                            <p>Producto1</p> \n" +
                    "                            <p>Producto2</p>\n" +
                    "                            <p>Producto3</p>\n" +
                    "                            <p>Producto4</p>\n" +
                    "                            <p>Producto5</p>\n" +
                    "                            <p>Producto6</p>\n" +
                    "                        </td>\n" +
                    "                        <td>\n" +
                    "                            <p class=\"derecha\"> 5 €</p>\n" +
                    "                            <p class=\"derecha\"> 3 €</p>\n" +
                    "                            <p class=\"derecha\"> 2 €</p>\n" +
                    "                            <p class=\"derecha\"> 1.99 €</p>\n" +
                    "                            <p class=\"derecha\"> 7.50 €</p>\n" +
                    "                            <p class=\"derecha\"> 4 €</p>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td>\n" +
                    "                            <p>Subtotal:</p> \n" +
                    "                            <h2>TOTAL: </h2>\n" +
                    "                        </td>\n" +
                    "                        <td>\n" +
                    "                            <p class=\"derecha\"> 23,49€</p>\n" +
                    "                            <h2 class=\"derecha\"> 23,49€</h2>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "                <br>\n" +
                    "                <br>\n" +
                    "                <p>Para gestionar la emisión de su factura envíe un e-mail a: facturacion@mcdam.com</p>\n" +
                    "                <br>\n" +
                    "                <br>\n" +
                    "                <img src=" + Properties.IMAGES_DIR + File.separator + "barcode.png\" style=\"text-align: center;\">\n" +
                    "                <br>\n" +
                    "                <br>\n" +
                    "                <br>\n" +
                    "                <br>\n" +
                    "                \n" +
                    "            </div><!-- /Contenido-->\n" +
                    "        </div> <!-- /Contenedor-->\n" +
                    "    </body>\n" +
                    "</html>\n" +
                    "\n" +
                    "\n";

            oos.writeUTF(h);

            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
