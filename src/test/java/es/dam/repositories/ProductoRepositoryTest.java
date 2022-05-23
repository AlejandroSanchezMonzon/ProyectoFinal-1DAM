package es.dam.repositories;

import es.dam.mcdam.models.CodigoDescuento;
import es.dam.mcdam.models.Producto;
import es.dam.mcdam.repositories.ProductoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductoRepositoryTest {
    private final ProductoRepository productoRepository = ProductoRepository.getInstance();

    private final CodigoDescuento codigo1 = new CodigoDescuento("A111",30.00f);
    private final CodigoDescuento codigo2 = new CodigoDescuento("B222",40.00f);
    private final CodigoDescuento codigo3 = new CodigoDescuento("C333",50.00f);

    private final Producto pTest1 = new Producto(
            UUID.randomUUID().toString(),"hamburguesa", 1.00f, "", "Hamburguesa de carne y queso", true, codigo1
    );
    private final Producto pTest1V2 = new Producto(
            UUID.randomUUID().toString(),"hamburguesa", 1.00f, "", "Hamburguesa de carne y queso", false, codigo1
    );
    private final Producto pTest2 = new Producto(
            UUID.randomUUID().toString(),"nuggets", 1.00f, "", "12 nuggets de pollo ", true, codigo2
    );
    private final Producto pTest3 = new Producto(
            UUID.randomUUID().toString(),"patatas", 2.00f, "", "Patatas normales medianas", true, codigo3
    );


    @BeforeAll
    void setUp() throws SQLException {
        productoRepository.deleteAll();
    }

    @Test
    void findAll() {
        try {
            var resVacio = productoRepository.findAll();
            productoRepository.save(pTest1);
            productoRepository.save(pTest2);
            productoRepository.save(pTest3);
            var resLleno = productoRepository.findAll();
            assertAll(
                    () -> assertEquals(3, resLleno.size()),
                    () -> assertEquals(pTest1, resLleno.get(0)),
                    () -> assertEquals(pTest2, resLleno.get(1)),
                    () -> assertEquals(pTest3, resLleno.get(2))
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void findById() {
        try {
            productoRepository.save(pTest1);
            var resOptional = productoRepository.findById(pTest1.getUuid());
            var res = resOptional.get();
            assertAll(
                    () -> assertTrue(resOptional.isPresent()),
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getUuid(), res.getUuid()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getPrecio(), res.getPrecio()),
                    () -> assertEquals(pTest1.getImagen(), res.getImagen()),
                    () -> assertEquals(pTest1.getDescripcion(), res.getDescripcion()),
                    () -> assertEquals(pTest1.getDisponible(), res.getDisponible()),
                    () -> assertEquals(pTest1.getCodigoDescuento(), res.getCodigoDescuento())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void save() {
        try {
            var res = productoRepository.save(pTest1);
            assertAll(
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getUuid(), res.getUuid()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getPrecio(), res.getPrecio()),
                    () -> assertEquals(pTest1.getImagen(), res.getImagen()),
                    () -> assertEquals(pTest1.getDescripcion(), res.getDescripcion()),
                    () -> assertEquals(pTest1.getDisponible(), res.getDisponible()),
                    () -> assertEquals(pTest1.getCodigoDescuento(), res.getCodigoDescuento())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void update() {
        try {
            var res = productoRepository.save(pTest1);
            var resUpd = productoRepository.update(pTest1V2);
            var resBusquedaOpt = productoRepository.findById(pTest1V2.getUuid());
            var resBusqueda = resBusquedaOpt.get();
            assertAll(
                    () -> assertTrue(resBusquedaOpt.isPresent()),
                    () -> assertEquals(pTest1.getUuid(), res.getUuid()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getPrecio(), res.getPrecio()),
                    () -> assertEquals(pTest1.getImagen(), res.getImagen()),
                    () -> assertEquals(pTest1.getDescripcion(), res.getDescripcion()),
                    () -> assertEquals(pTest1.getDisponible(), res.getDisponible()),
                    () -> assertEquals(pTest1.getCodigoDescuento(), res.getCodigoDescuento())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void delete() {
        try {
            productoRepository.save(pTest1);
            var res = productoRepository.delete(pTest1);

            assertAll(
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getUuid(), res.getUuid()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getPrecio(), res.getPrecio()),
                    () -> assertEquals(pTest1.getImagen(), res.getImagen()),
                    () -> assertEquals(pTest1.getDescripcion(), res.getDescripcion()),
                    () -> assertEquals(pTest1.getDisponible(), res.getDisponible()),
                    () -> assertEquals(pTest1.getCodigoDescuento(), res.getCodigoDescuento())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void deleteAll() {
        try {
            productoRepository.save(pTest1);
            productoRepository.save(pTest2);
            productoRepository.save(pTest3);
            productoRepository.deleteAll();

            assertAll(
                    () -> assertTrue(productoRepository.findAll().isEmpty()),
                    () -> assertTrue(productoRepository.findById(pTest1.getUuid()).isEmpty()),
                    () -> assertTrue(productoRepository.findById(pTest2.getUuid()).isEmpty()),
                    () -> assertTrue(productoRepository.findById(pTest3.getUuid()).isEmpty())
            );
        }catch (Exception e){
            fail();
        }
    }
}