package es.dam.mcdam.views;

import java.io.File;

public enum Views {
    ACERCADE("views" + File.separator + "acercade-view.fxml"),
    MENUCLIENTE("views" + File.separator + "clientemenu-view.fxml"),
    CONSULTAADMINISTRADOR("views" + File.separator + "consultaadministrador-view.fxml"),
    EDICIONADMINISTRADOR("views" + File.separator + "edicionadministrador-view.fxml"),
    MAIN("views" + File.separator + "iniciosesion-view.fxml"),
    MENUADMIN("views" + File.separator + "menuadministrador-view.fxml"),
    PROCESOPAGO("views" + File.separator + "procesopago-view.fxml"),

    REGISTRARSE("views" + File.separator + "registrarse-view.fxml"),
    SPLASH("views" + File.separator + "splash-view.fxml"),
    ACTUALIZARCODIGO("views" + File.separator + "actualizarcodigo-view.fxml"),
    ACTUALIZARPRODUCTO("views" + File.separator + "actualizarproducto-view.fxml");

    private final String view;

    Views(String view) {
        this.view = view;
    }

    public String get() {
        return view;
    }
}
