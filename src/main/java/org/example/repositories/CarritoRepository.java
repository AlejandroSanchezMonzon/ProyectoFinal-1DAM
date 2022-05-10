package org.example.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.models.Carrito;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CarritoRepository implements ICarritoRepository {
    private static CarritoRepository instance;

    private final ObservableList<Carrito> items = FXCollections.observableArrayList();

    private CarritoRepository() {
    }

    public static CarritoRepository getInstance() {
        if (instance == null) {
            instance = new CarritoRepository();
        }
        return instance;
    }

    @Override
    public Optional<List<Carrito>> findAll() throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<ObservableList<Carrito>> findAllObservable(){
        if(items.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(items);
    }

    @Override
    public Optional<Carrito> findById(UUID uuid) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Carrito save(Carrito entity) throws SQLException {
        items.add(entity);
        return entity;
    }

    @Override
    public Carrito update(Carrito entity) throws SQLException {
        //TODO: items.update(entity);
        return entity;
    }

    @Override
    public Carrito delete(Carrito entity) throws SQLException {
        items.remove(entity);
        return entity;
    }

    @Override
    public void deleteAll(){
        items.clear();
    }

    @Override
    public double getTotal() {
        var total = 0.0f;
        for(Carrito carrito: items){
            total += carrito.getPrecio();
        }
        return total;
    }
}
