package org.example.repositories;

import org.example.models.ItemCarrito;


public interface ICarritoRepository extends ICRUDObservable<ItemCarrito, String> {
    public double getTotal();
}
