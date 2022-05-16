package org.example.models;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.UUID;

public class PersonaRegistrada {
    private String uuid = UUID.randomUUID().toString();
    private final String nombre;
    private final String correo;
    private final String contraseña;
    private final Tipo tipo;

    public PersonaRegistrada(String uuid, String nombre, String correo, String contraseña, Tipo tipo) {
        this.uuid = uuid;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.tipo = tipo;
    }
    public PersonaRegistrada(String nombre, String correo, String contraseña, Tipo tipo) {
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.tipo = tipo;
    }

    public String getUuid() {
        return uuid;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public Tipo getTipo() {
        return tipo;
    }


    @Override
    public String toString() {
        return "Administrador{" + "uuid=" + uuid + ", nombre=" + nombre + ", correo=" + correo + ", contraseña=" + contraseña + '}';
    }
}
