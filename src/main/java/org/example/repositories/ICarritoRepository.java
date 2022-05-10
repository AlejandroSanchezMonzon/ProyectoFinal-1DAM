package org.example.repositories;

import javafx.collections.ObservableList;
import org.example.models.Carrito;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public interface ICarritoRepository extends ICRUDRepository<Carrito, UUID> {

    public Optional<ObservableList<Carrito>> findAllObservable();

    public double getTotal();

    public void deleteAll();
}
