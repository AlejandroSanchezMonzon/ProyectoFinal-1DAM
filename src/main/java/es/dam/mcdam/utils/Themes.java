/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.utils;

public enum Themes {
    OSCURO("styles/modena.css");
    private final String view;

    Themes(String view) {
        this.view = view;
    }

    public String get() {
        return view;
    }
}
