module es.dam.mcdam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires lombok;
    requires org.mybatis;


    opens es.dam.mcdam to javafx.fxml;
    exports es.dam.mcdam;
}