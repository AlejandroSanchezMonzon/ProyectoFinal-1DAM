module es.dam.mcdam {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.dam.mcdam to javafx.fxml;
    exports es.dam.mcdam;
}