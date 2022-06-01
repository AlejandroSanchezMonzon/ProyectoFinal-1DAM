/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.models;

import java.util.UUID;

public class PersonaRegistrada {
    //ESTADO
    private String uuid = UUID.randomUUID().toString();
    private String nombre;
    private String correo;
    private String contraseña;
    private Tipo tipo;

    //COMPORTAMIENTO

    //CONSTRUCTORES
    public PersonaRegistrada(){
        this.nombre = "";
        this.correo = "";
        this.contraseña = "";
        this.tipo = Tipo.USER;
    }

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

    //GETTERS Y SETTERS
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

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * Método que devuelve una cadena con la información de la persona registrada.
     * @return
     */
    @Override
    public String toString() {
        return  "<br>" +" NOMBRE : " + nombre + "<br>" + " CORREO : " + correo ;
    }
}
