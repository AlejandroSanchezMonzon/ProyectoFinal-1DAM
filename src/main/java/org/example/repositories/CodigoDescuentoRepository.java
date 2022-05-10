package org.example.repositories;

import org.example.models.CodigoDescuento;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CodigoDescuentoRepository implements ICodigoDescuentoRepository{
    @Override
    public Optional<List<CodigoDescuento>> findAll() throws SQLException {
        return null;
    }

    @Override
    public Optional<CodigoDescuento> findById(UUID uuid) throws SQLException {
        return Optional.empty();
    }

    @Override
    public CodigoDescuento save(CodigoDescuento entity) throws SQLException {
        return null;
    }

    @Override
    public CodigoDescuento update(CodigoDescuento entity) throws SQLException {
        return null;
    }

    @Override
    public CodigoDescuento delete(CodigoDescuento entity) throws SQLException {
        return null;
    }
}
