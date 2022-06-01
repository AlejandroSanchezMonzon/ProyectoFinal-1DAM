/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.dam.mcdam.models.ItemCarrito;
import java.sql.SQLException;
import java.util.Optional;

public class CarritoRepository implements ICarritoRepository {
    //ESTADO
    private static CarritoRepository instance;

    private final ObservableList<ItemCarrito> items = FXCollections.observableArrayList();

    //CONSTRUCTOR
    private CarritoRepository() {
    }

    //SINGLETON
    public static CarritoRepository getInstance() {
        if (instance == null) {
            instance = new CarritoRepository();
        }
        return instance;
    }

    /**
     * Método que encuentra todos los items del carrito.
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList<ItemCarrito> findAll()throws SQLException{
        return items;
    }

    /**
     * Método que encuen item del carrito en base a su ID.
     * @param uuid Id del elemento
     * @return
     * @throws SQLException
     */
    @Override
    public Optional<ItemCarrito> findById(String uuid) throws SQLException {
        for(ItemCarrito item: items){
            if(item.getUuid().equals(uuid)){
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    /**
     * Método que guarda un item en el carrito.
     * @param entity Elemento a insertar
     * @return
     * @throws SQLException
     */
    @Override
    public ItemCarrito save(ItemCarrito entity) throws SQLException {
        items.add(entity);
        return entity;
    }

    /**
     * Método que actualiza un item en el carrito.
     * @param entity Elemento a actualizar
     * @return
     * @throws SQLException
     */
    @Override
    public ItemCarrito update(ItemCarrito entity) throws SQLException {
        int index = items.indexOf(entity);
        items.set(index, entity);
        return entity;
    }

    /**
     * Método que elimina un item del carrito.
     * @param entity Elemento a eliminar
     * @return
     * @throws SQLException
     */
    @Override
    public ItemCarrito delete(ItemCarrito entity) throws SQLException {
        items.remove(entity);
        return entity;
    }

    /**
     * Método que elimina todos los items del carrito.
     */
    @Override
    public void deleteAll(){
        items.clear();
    }

    /**
     * Método que calcula el precio total del carrito.
     * @return
     */
    @Override
    public double getTotal() {
        return items.stream().mapToDouble(ItemCarrito::getTotal).sum();
    }
}
