/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.models;

import java.time.LocalDateTime;

public class Factura {
    //ESTADO
    private String titulo;
    private String fecha = LocalDateTime.now().toString();
    private Pedido pedido;

    //COMPORTAMIENTO

    //CONSTRUCTOR
    public Factura(String titulo, String fecha, Pedido pedido) {
        this.titulo = titulo;
        this.pedido = pedido;
    }

    //GETTERSy SETTERS
    public String getTitulo() {
        return titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
