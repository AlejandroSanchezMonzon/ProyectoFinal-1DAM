package es.dam.mcdam.views;

public enum Views {
    ACERCADE("views/acercade-view.fxml"),
    MENUCLIENTE("views/clientemenu.fxml"),
    CONSULTAADMINISTRADOR("views/consultaadministrador-view.fxml"),
    EDICIONADMINISTRADOR("views/edicionadministrador-view.fxml"),
    MAIN("views/iniciosesion.fxml"),
    MENUADMIN("views/menuadministrador.fxml"),
    PROCESOPAGO("views/procesopago-view.fxml"),
    REGISTRARSE("views/registrarse-view.fxml"),
    SPLASH("views/Splash.fxml");

    private final String view;

    Views(String view) {
        this.view = view;
    }

    public String get() {
        return view;
    }
}
