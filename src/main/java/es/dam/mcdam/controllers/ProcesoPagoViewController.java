/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.controllers;

import es.dam.mcdam.models.Pedido;
import es.dam.mcdam.utils.Properties;
import es.dam.mcdam.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;

public class ProcesoPagoViewController {
    //ESTADO
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
    @FXML
    public VBox camposTarjeta;

    boolean isEfectivo = true;
    private static Pedido pedido;

    //COMPORTAMIENTO

    /**
     * Inicializa el controlador.
     */
    public void initialize() {
        datosTarjeta.setVisible(false);
        camposTarjeta.setVisible(false);

        efectivoButton.setOnAction(event -> {
            System.out.println("Efectivo");
            isEfectivo = true;
            datosTarjeta.setVisible(false);
            camposTarjeta.setVisible(false);
            pedido.setMetodoPago("Efectivo");
        });
        tarjetaButton.setOnAction(event -> {
            System.out.println("Tarjeta");
            isEfectivo = false;
            datosTarjeta.setVisible(true);
            camposTarjeta.setVisible(true);
            pedido.setMetodoPago("Tarjeta");
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

    /**
     * Comprueba que los datos introducidos son correctos.
     * @return
     */
    private boolean comprobarDatos() {
        return (Utils.isTarjetaCredito(tarjetaTxt.getText()) && Utils.isCaducidad(caducidadPick.getValue()) && Utils.isCvs(cvsTxt.getText()));
    }

    /**
     * Confirma el pago.
     * @param isEfectivo
     */
    private void confirmar(boolean isEfectivo) {
        if (isEfectivo) {
            System.out.println("Efectivo");
            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Efectivo");
                alert.setContentText("Acuda a la caja para pagar su pedido.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    mostrarFactura();
                    Stage scene = (Stage) efectivoButton.getScene().getWindow();
                    scene.hide();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Tarjeta");
            if (comprobarDatos()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tarjeta");
                alert.setContentText("Acuda a la caja para esperar su pedido.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    mostrarFactura();
                    Stage scene = (Stage) tarjetaButton.getScene().getWindow();
                    scene.hide();

                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Datos incorrectos");
                alert.showAndWait();
            }
        }
    }

    /**
     * Crea el html con la factura.
     */
    private static void mostrarFactura() {
        String str = "";
        String h = "";
        String t = "";
        String factura = pedido.toString();
        String total = Utils.redondeoPrecio(pedido.getTotal());
        try {
            FileOutputStream fos = new FileOutputStream( Properties.DATA_DIR+ File.separator + "factura.html");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            h = "<!DOCTYPE html>\n" +
                    "<html lang=\"es-ES\">\n" +
                    "    <head> \n" +
                    "            <title>Recibo mcdam</title>\n" +
                    "            <meta charset=\"utf-8\">\n" +
                    "            <link rel=\"stylesheet\" href=\"" + Properties.CSS_FILE +"\">\n" +
                    "\n" +
                    "    </head>\n" +
                    "    <body>  \n" +
                    "        <div id=\"contenedor\">\n" +
                    "\n" +
                    "            <div id=\"cabecera\">\n" +
                    "\n" +
                    "            <img src=\"" + Properties.HTML_IMAGE + "\" height=\"200\" width=\"200\">\n" +
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
                                                 factura + "\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td>\n" +
                    "                            <p>Subtotal:</p> \n" +
                    "                            <h2>TOTAL: </h2>\n" +
                    "                        </td>\n" +
                    "                        <td>\n" +
                    "                            <p class=\"derecha\"> " + total +"</p>\n" +
                    "                            <h2 class=\"derecha\"> "+ total +"</h2>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "                <br>\n" +
                    "                <br>\n" +
                    "                <p>Para gestionar la emisión de su factura envíe un e-mail a: facturacion@mcdam.com</p>\n" +
                    "                <br>\n" +
                    "                <br>\n" +
                    "                <img src=\"" + Properties.HTML_IMAGE2 + "\" style=\"text-align: center;\">\n" +
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

    /**
     * Establece el pedido.
     * @param pedido
     */
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
