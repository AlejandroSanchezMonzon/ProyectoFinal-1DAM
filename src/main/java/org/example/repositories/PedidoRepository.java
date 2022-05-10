package org.example.repositories;

import org.example.models.Pedido;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PedidoRepository implements IPedidoRepository{
    @Override
    public Optional<List<Pedido>> findAll() throws SQLException {
        return null;
    }

    @Override
    public Optional<Pedido> findById(UUID uuid) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Pedido save(Pedido entity) throws SQLException {
        return null;
    }

    @Override
    public Pedido update(Pedido entity) throws SQLException {
        return null;
    }

    @Override
    public Pedido delete(Pedido entity) throws SQLException {
        return null;
    }
}
