package es.dam.mcdam;

import es.dam.mcdam.controllers.SplashController;
import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.managers.SceneManager;
import es.dam.mcdam.utils.Properties;
import javafx.application.Application;
import javafx.stage.Stage;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class AppMain extends Application {
    //static Logger logger = LogManager.getLogger(AppMain.class);
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        SceneManager sceneManager = SceneManager.getInstance(AppMain.class);
        sceneManager.initSplash(stage);


    }

    private static void checkServer() {
        System.out.println("Comprobamos la conexión al Servidor BD");
        DataBaseManager controller = DataBaseManager.getInstance();
        try {
            controller.open();
            Optional<ResultSet> rs = controller.select("SELECT 'Hello world'");
            if (rs.isPresent()) {
                rs.get().next();
                controller.close();
                //logger.info("Conexión correcta a la Base de Datos");
                System.out.println("Conexión correcta a la Base de Datos");
            }
        } catch (SQLException e) {
            //logger.error("Error al conectar al servidor de Base de Datos: " + e.getMessage());
            System.out.println("Error al conectar al servidor de Base de Datos: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        mostrarFactura();
        //checkServer();
        //launch();
    }

    private static void mostrarFactura() {
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

    public void onSalirAction() {
        System.exit(0);
    }
}