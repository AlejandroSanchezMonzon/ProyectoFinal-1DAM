package es.dam.mcdam.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.dam.mcdam.models.ItemCarrito;
import java.sql.SQLException;
import java.util.Optional;

public class CarritoRepository implements ICarritoRepository {
    private static CarritoRepository instance;

    private final ObservableList<ItemCarrito> items = FXCollections.observableArrayList();

    private CarritoRepository() {
    }

    public static CarritoRepository getInstance() {
        if (instance == null) {
            instance = new CarritoRepository();
        }
        return instance;
    }

    @Override
    public ObservableList<ItemCarrito> findAll()throws SQLException{
        return items;
    }

    @Override
    public Optional<ItemCarrito> findById(String uuid) throws SQLException {
        for(ItemCarrito item: items){
            if(item.getUuid().equals(uuid)){
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    @Override
    public ItemCarrito save(ItemCarrito entity) throws SQLException {
        items.add(entity);
        return entity;
    }

    @Override
    public ItemCarrito update(ItemCarrito entity) throws SQLException {
        int index = items.indexOf(entity);
        items.set(index, entity);
        return entity;
    }

    @Override
    public ItemCarrito delete(ItemCarrito entity) throws SQLException {
        items.remove(entity);
        return entity;
    }

    @Override
    public void deleteAll(){
        items.clear();
    }

    @Override
    public double getTotal() {
        return items.stream().mapToDouble(ItemCarrito::getTotal).sum();
    }
}
