/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.repositories;

import es.dam.mcdam.models.ItemCarrito;


public interface ICarritoRepository extends ICRUDObservable<ItemCarrito, String> {

    /**
     * Método que devuelve el total del carrito.
     * @return
     */
    public double getTotal();
}
