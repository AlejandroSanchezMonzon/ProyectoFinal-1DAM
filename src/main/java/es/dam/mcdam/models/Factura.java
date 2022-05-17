package es.dam.mcdam.models;

import java.time.LocalDateTime;

public class Factura {
    private String titulo;
    private String fecha = LocalDateTime.now().toString();
    private Pedido pedido;

    public Factura(String titulo, String fecha, Pedido pedido) {
        this.titulo = titulo;
        this.pedido = pedido;
    }

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
