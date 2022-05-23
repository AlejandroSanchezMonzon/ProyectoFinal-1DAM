package es.dam.mcdam.views;

public enum Views {
    ACERCADE("views/acercade-view.fxml"),
    MENUCLIENTE("views/clientemenu-view.fxml"),
    CONSULTAADMINISTRADOR("views/consultaadministrador-view.fxml"),
    EDICIONADMINISTRADOR("views/edicionadministrador-view.fxml"),
    MAIN("views/iniciosesion-view.fxml"),
    MENUADMIN("views/menuadministrador-view.fxml"),
    PROCESOPAGO("views/procesopago-view.fxml"),

    REGISTRARSE("views/registrarse-view.fxml"),
    SPLASH("views/splash-view.fxml"),
    ACTUALIZARCODIGO("views/actualizarcodigo-view.fxml"),
    ACTUALIZARPRODUCTO("views/actualizarproducto-view.fxml");

    private final String view;

    Views(String view) {
        this.view = view;
    }

    public String get() {
        return view;
    }
}
