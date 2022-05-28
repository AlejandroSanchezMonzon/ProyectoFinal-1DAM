package es.dam.mcdam.repositories;

import es.dam.mcdam.models.ItemCarrito;


public interface ICarritoRepository extends ICRUDObservable<ItemCarrito, String> {
    public double getTotal();
}
