package es.dam.mcdam.views;

public enum Views {
    SPLASH(""),
    MAIN(""),
    ACERCADE(""),
    PERSONAEDITAR(""),
    ESTADISTICAS("");

    private final String view;

    Views(String view) {
        this.view = view;
    }

    public String get() {
        return view;
    }
}
