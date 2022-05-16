package org.example.repositories;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ICRUDRepository <T, ID> {
    /**
     * Devuelve Optional de una lista de todos los elementos del repositorio
     *
     * @return Lista de elementos
     * @throws SQLException Si hay algún error en la base de datos
     */
    Optional<List<T>> findAll() throws SQLException;

    /**
     * Devuelve un Optional del elemento dado un id
     *
     * @param id Id del elemento
     * @return Optional del elemento
     * @throws SQLException Si hay algún error en la base de datos
     */
    Optional<T> findById(String id) throws SQLException;

    /**
     * Inserta un elemento en el repositorio
     *
     * @param entity Elemento a insertar
     * @return Elemento insertado
     * @throws SQLException Si hay algún error en la base de datos
     */
    T save(T entity) throws SQLException;

    /**
     * Actualiza un elemento en el repositorio
     *
     * @param entity Elemento a actualizar
     * @return Elemento actualizado
     * @throws SQLException Si hay algún error en la base de datos
     */
    T update(T entity) throws SQLException;

    /**
     * Elimina un elemento del repositorio
     *
     * @return Elemento eliminado
     * @throws SQLException Si hay algún error en la base de datos
     */
    T delete(T entity) throws SQLException;
}
