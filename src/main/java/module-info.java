module es.dam.mcdam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires lombok;
    requires org.mybatis;
//    requires org.apache.logging.log4j.core;
//    requires org.apache.logging.log4j;


    opens es.dam.mcdam to javafx.fxml;
    exports es.dam.mcdam;
    exports es.dam.mcdam.controllers;
    opens es.dam.mcdam.controllers to javafx.fxml;
    opens es.dam.mcdam.models to com.google.gson;
    exports es.dam.mcdam.models;
}