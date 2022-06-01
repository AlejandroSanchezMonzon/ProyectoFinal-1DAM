/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.models;

import java.util.List;
import java.util.UUID;

public class Pedido {
    //ESTADO
    private final String uuid = UUID.randomUUID().toString();
    private final double total;
    private  String metodoPago;
    private final List<LineaPedido> compra;
    private final PersonaRegistrada cliente;

    //COMPORTAMIENTO

    //CONSTRUCTOR
    public Pedido(List<LineaPedido> compra, PersonaRegistrada cliente, String metodoPago) {
        this.compra = compra;
        this.cliente = cliente;
        this.total = getTotal();
    }

    //GETTERS y SETTERS
    public String getUuid() {
        return uuid;
    }

    public double getTotal() {
        return compra.stream().mapToDouble(LineaPedido::getTotal).sum();
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public List<LineaPedido> getCompra() {
        return compra;
    }

    public PersonaRegistrada getCliente() {
        return cliente;
    }

    /**
     * Méto para mostrar los datos del pedido.
     * @return
     */
    @Override
    public String toString() {
        return "- PEDIDO:" + "<br>" + " PAGO REALIZADO MEDIANTE : " + metodoPago + "<br>" +"- COMPRA : " + "<br>" + compra + "<br>"+"- CLIENTE : " + cliente ;
    }
}
