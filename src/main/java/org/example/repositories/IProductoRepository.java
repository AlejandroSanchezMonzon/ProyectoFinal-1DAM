package org.example.repositories;

import org.example.models.Producto;

import java.util.UUID;

public interface IProductoRepository extends ICRUDRepository<Producto, UUID> {
}
