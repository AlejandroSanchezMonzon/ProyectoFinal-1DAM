/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.models;

public class LineaPedido {
    //ESTADO
    private final String producto;
    private final int cantidad;
    private final double precio;
    private final double total;

    //COMPORTAMIENTO

    //CONSTRUCTOR
    public LineaPedido(String producto, int cantidad, double precio, double total) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }

    //GETTERS, SETTERS y PROPERTY
    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public double getTotal() {
        return total;
    }

    /**
     * Método que devuelve una cadena con la información de la línea de pedido.
     * @return
     */
    @Override
    public String toString() {
        return " PRODUCTO : " + producto + "<br>" +
                " CANTIDAD : " + cantidad +"<br>" +
                " PRECIO : " + precio + "€"+"<br>";
    }
}
