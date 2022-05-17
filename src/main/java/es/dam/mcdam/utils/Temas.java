package es.dam.mcdam.utils;

import javafx.scene.Node;

public class Temas {
    public static void set(Node node, String style) {
        node.getScene().getRoot().getStylesheets().clear();
        var newStyle = AppMain.class.getResource(style).toString();
        node.getScene().getRoot().getStylesheets().add(newStyle);
        System.out.println("Estilo: " + newStyle);
    }

    public static void remove(Node node) {
        node.getScene().getRoot().getStylesheets().clear();
        System.out.println("Estilo: " + "sin estilo");
    }
}
