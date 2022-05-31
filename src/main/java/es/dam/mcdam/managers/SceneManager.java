package es.dam.mcdam.managers;

import es.dam.mcdam.AppMain;
import es.dam.mcdam.controllers.*;
import es.dam.mcdam.models.CodigoDescuento;
import es.dam.mcdam.models.Pedido;
import es.dam.mcdam.models.PersonaRegistrada;
import es.dam.mcdam.models.Producto;
import es.dam.mcdam.repositories.PedidoRepository;
import es.dam.mcdam.repositories.PersonaRegistradaRepository;
import es.dam.mcdam.services.Backup;
import es.dam.mcdam.services.BackupJSON;
import es.dam.mcdam.utils.Properties;
import es.dam.mcdam.utils.Resources;
import es.dam.mcdam.views.Views;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;


public class SceneManager {
    private static SceneManager instance;
    private final Class<?> appClass;
//    Logger logger = LogManager.getLogger(SceneManager.class);

    private Stage mainStage;

    private SceneManager(Class<?> appClass) {
        this.appClass = appClass;
        System.out.println("SceneManager created");
    }

    public static SceneManager getInstance(Class<?> appClass) {
        if (instance == null) {
            instance = new SceneManager(appClass);
        }
        return instance;
    }

    public static SceneManager get() {
        return instance;
    }

    public void changeScene(Node node, Views view) throws IOException {
        //logger.info("Loading scene " + view.get());
        System.out.println("Loading scene " + view.get());
        Stage stage = (Stage) node.getScene().getWindow();
        //oldStage.hide(); // Oculto la anterior
        Parent root = FXMLLoader.load(Objects.requireNonNull(appClass.getResource(view.get())));
        Scene newScene = new Scene(root, Properties.APP_WIDTH, Properties.APP_HEIGHT);
        //logger.info("Scene " + view.get() + " loaded");
        System.out.println("Scene " + view.get() + " loaded");
        stage.setScene(newScene);
        stage.show();
    }

    public void initMain() throws IOException {
        //logger.info("Iniciando Main");
        System.out.println("Iniciando Main");
        Platform.setImplicitExit(true);
        //logger.info("Loading scene " + Views.MAIN.get());
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(appClass.getResource(Views.MAIN.get())));
        Scene scene = new Scene(fxmlLoader.load(), Properties.APP_WIDTH, Properties.APP_HEIGHT);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image(Resources.get(AppMain.class, Properties.APP_ICON)));
        stage.setTitle(Properties.APP_TITLE);
        stage.initStyle(StageStyle.DECORATED);
        //logger.info("Scene Main loaded");
        System.out.println("Scene Main loaded");
        // Por si salimos
        stage.setOnCloseRequest(event -> {
            fxmlLoader.<LoginViewController>getController().onSalirAction();
        });
        stage.setScene(scene);
        mainStage = stage;
        stage.show();
    }

    public void initSplash(Stage stage) throws IOException, InterruptedException {
        Platform.setImplicitExit(false);
        //logger.info("Iniciando Splash");
        System.out.println("Iniciando Splash");
        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource(Views.SPLASH.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.SPLASH_WIDTH, Properties.SPLASH_HEIGHT);
        stage.getIcons().add(new Image(Resources.get(AppMain.class, Properties.APP_ICON)));
        stage.setTitle(Properties.APP_TITLE);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        //logger.info("Scene Splash loaded");
        System.out.println("Scene Splash loaded");
        stage.show();
    }

    public void initMenuCliente(PersonaRegistrada userActual) throws IOException {
        //logger.info("Iniciando Menu Cliente");
        System.out.println("Iniciando Menu Cliente");
        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource(Views.MENUCLIENTE.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.MENUCLIENTE_WIDTH, Properties.MENUCLIENTE_HEIGHT);
        Stage stage = new Stage();
        stage.setResizable(false);
        MenuClienteViewController controller = fxmlLoader.getController();
        controller.setClienteActual(userActual);
        stage.getIcons().add(new Image(Resources.get(AppMain.class, Properties.APP_ICON)));
        stage.setTitle(Properties.APP_TITLE);
        stage.initStyle(StageStyle.DECORATED);
        //logger.info("Scene Menu Cliente loaded");
        System.out.println("Scene Menu Cliente loaded");
        stage.setScene(scene);
        stage.show();
    }

    public void initMenuAdmin() throws IOException {
        //logger.info("Iniciando Menu Admin");
        System.out.println("Iniciando Menu Admin");
        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource(Views.MENUADMIN.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.MENUADMIN_WIDTH, Properties.MENUADMIN_HEIGHT);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image(Resources.get(AppMain.class, Properties.APP_ICON)));
        stage.setTitle(Properties.APP_TITLE);
        stage.initStyle(StageStyle.DECORATED);
        //logger.info("Scene Menu Admin loaded");
        System.out.println("Scene Menu Admin loaded");
        stage.setScene(scene);
        stage.show();
    }

    public void initAcercaDe() throws IOException {
        //logger.info("Iniciando AcercaDe");
        System.out.println("Iniciando AcercaDe");
        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource(Views.ACERCADE.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.ACERCADE_WIDTH, Properties.ACERCADE_HEIGHT);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initOwner(mainStage);
        stage.setTitle("Acerca de");
        stage.setResizable(false);
        // Le hacemos los setters a los elementos del controlador
        fxmlLoader.<AcercaDeViewController>getController().setDialogStage(stage);
        stage.setScene(scene);
        //logger.info("Scene AcercaDe loaded");
        System.out.println("Scene AcercaDe loaded");
        stage.showAndWait();
    }

    public void initConsultaAdministrador() throws IOException {
        //logger.info("Iniciando consulta administrador");
        System.out.println("Iniciando consulta administrador");
        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource(Views.CONSULTAADMINISTRADOR.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.CONSULTAADMINISTRADOR_WIDTH, Properties.CONSULTAADMINISTRADOR_HEIGHT);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image(Resources.get(AppMain.class, Properties.APP_ICON)));
        stage.setTitle(Properties.APP_TITLE);
        stage.initStyle(StageStyle.DECORATED);
        //logger.info("Scene Consulta Admin loaded");
        System.out.println("Scene Consulta Admin loaded");
        stage.setScene(scene);
        stage.showAndWait();

    }
    public void initEdicionAdministrador() throws IOException {
        //logger.info("Iniciando edicion administrador");
        System.out.println("Iniciando edicion administrador");
        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource(Views.EDICIONADMINISTRADOR.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.EDICIONADMINISTRADOR_WIDTH, Properties.EDICIONADMINISTRADOR_HEIGHT);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image(Resources.get(AppMain.class, Properties.APP_ICON)));
        stage.setTitle(Properties.APP_TITLE);
        stage.initStyle(StageStyle.DECORATED);
        fxmlLoader.<EdicionAdministradorViewController>getController().setDialogStage(stage);
        //logger.info("Scene Consulta Admin loaded");
        System.out.println("Scene Edicion Admin loaded");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void initProcesoPago(Pedido pedido) throws IOException {
        //logger.info("Iniciando proceso pago");
        System.out.println("Iniciando proceso pago");
        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource(Views.PROCESOPAGO.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.PROCESOPAGO_WIDTH, Properties.PROCESOPAGO_HEIGHT);
        Stage stage = new Stage();
        stage.setResizable(false);
        ProcesoPagoViewController controller = fxmlLoader.getController();
        controller.setPedido(pedido);
        stage.getIcons().add(new Image(Resources.get(AppMain.class, Properties.APP_ICON)));
        stage.setTitle(Properties.APP_TITLE);
        stage.initStyle(StageStyle.DECORATED);
        //logger.info("Scene Proceso Pago loaded");
        System.out.println("Scene Proceso Pago loaded");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public boolean initProductoEditar(boolean editarModo, Producto producto, Stage stageEdicion) throws IOException {
        System.out.println("Iniciando Actualizar Producto");
        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource(Views.ACTUALIZARPRODUCTO.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.ACTUALIZARPRODUCTO_WIDTH, Properties.ACTUALIZARPRODUCTO_HEIGHT);
        Stage stage =new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mainStage);
        stage.setTitle(editarModo ? "Editar Producto" : "Nuevo Producto");
        stage.setResizable(false);
        ActualizarProductoViewController controller = fxmlLoader.getController();
        controller.setDialogStage(stage);
        controller.setEditarModo(editarModo);
        controller.setProducto(producto);
        stage.setScene(scene);
        stage.showAndWait();
        stageEdicion.close();
        return controller.isAceptarClicked();
    }

    public boolean initCodigoDescuentoEditar(boolean editarModo, CodigoDescuento codigoDescuento, Stage stageEdicion) throws IOException {
        System.out.println("Iniciando Actualizar Codigo Descuento");
        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource(Views.ACTUALIZARCODIGO.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.ACTUALIZARCODIGO_WIDTH, Properties.ACTUALIZARCODIGO_HEIGHT);
        Stage stage =new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mainStage);
        stage.setTitle(editarModo ? "Editar Código Descuento" : "Nueva Código Descuento");
        stage.setResizable(false);
        ActualizarCodigoDescuentoViewController controller = fxmlLoader.getController();
        controller.setDialogStage(stage);
        controller.setEditarModo(editarModo);
        controller.setCodigoDescuento(codigoDescuento);
        stage.setScene(scene);
        stage.showAndWait();
        stageEdicion.close();
        return controller.isAceptarClicked();
    }

    public void initRegisterView() throws IOException {
        //logger.info("Iniciando registro");
        System.out.println("Iniciando Registro");
        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource(Views.REGISTRARSE.get()));
        Scene scene = new Scene(fxmlLoader.load(), Properties.REGISTER_WIDTH, Properties.REGISTER_HEIGHT);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle(Properties.APP_TITLE);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void initBackup() throws SQLException, IOException {
        System.out.println("Iniciando Backup");
        BackupJSON save = BackupJSON.getInstance();
        PersonaRegistradaRepository personaRegistradaRepository = PersonaRegistradaRepository.getInstance();
        PedidoRepository pedidoRepository = PedidoRepository.getInstance();
        save.backupPersonas(personaRegistradaRepository.findAll());
        save.backupPedidos(pedidoRepository.findAllString());
    }
}
