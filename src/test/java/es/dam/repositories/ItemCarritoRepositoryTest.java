package es.dam.repositories;

import es.dam.mcdam.models.ItemCarrito;
import es.dam.mcdam.repositories.CarritoRepository;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Carro Compra Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemCarritoRepositoryTest {
    private final CarritoRepository carritoRepository = CarritoRepository.getInstance();

    private final ItemCarrito pTest1 = new ItemCarrito("1","hamburguesa", "", 1.00f, 1);
    private final ItemCarrito pTest1V2 = new ItemCarrito("1","hamburguesa", "", 1.00f, 2);
    private final ItemCarrito pTest2 = new ItemCarrito("2","nugget", "", 3.75f, 2);
    private final ItemCarrito pTest3 = new ItemCarrito("3","patatas", "", 2.50f, 5);


    @BeforeAll
    void setUp(){
        carritoRepository.deleteAll();
    }

    @Test
    void findAll(){
        try {
            var resVacio = carritoRepository.findAll();
            carritoRepository.save(pTest1);
            carritoRepository.save(pTest2);
            carritoRepository.save(pTest3);
            var resLleno = carritoRepository.findAll();

            assertAll(
                    () -> assertFalse(resVacioOptional.isPresent()),
                    () -> assertTrue(resLlenoOptional.isPresent()),
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
            carritoRepository.save(pTest1);
            var resOptional = carritoRepository.findById(pTest1.getUuid());
            var res = resOptional.get();
            assertAll(
                    () -> assertTrue(resOptional.isPresent()),
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getUuid(), res.getUuid()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getImagen(), res.getImagen()),
                    () -> assertEquals(pTest1.getPrecio(), res.getPrecio()),
                    () -> assertEquals(pTest1.getCantidad(), res.getCantidad())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void save() {
        try {
            var res = carritoRepository.save(pTest1);
            assertAll(
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getUuid(), res.getUuid()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getImagen(), res.getImagen()),
                    () -> assertEquals(pTest1.getPrecio(), res.getPrecio()),
                    () -> assertEquals(pTest1.getCantidad(), res.getCantidad())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void update() {
        try {
            var res = carritoRepository.save(pTest1);
            var resUpd = carritoRepository.update(pTest1V2);
            var resBusquedaOpt = carritoRepository.findById(pTest1V2.getUuid());
            var resBusqueda = resBusquedaOpt.get();
            assertAll(
                    () -> assertTrue(resBusquedaOpt.isPresent()),
                    () -> assertEquals(pTest1V2.getUuid(), resBusqueda.getUuid()),
                    () -> assertEquals(pTest1V2.getNombre(), resBusqueda.getNombre()),
                    () -> assertEquals(pTest1V2.getImagen(), resBusqueda.getImagen()),
                    () -> assertEquals(pTest1V2.getPrecio(), resBusqueda.getPrecio()),
                    () -> assertEquals(pTest1V2.getCantidad(), resBusqueda.getCantidad())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void delete() {
        try {
            carritoRepository.save(pTest1);
            var res = carritoRepository.delete(pTest1);

            assertAll(
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getUuid(), res.getUuid()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getImagen(), res.getImagen()),
                    () -> assertEquals(pTest1.getPrecio(), res.getPrecio()),
                    () -> assertEquals(pTest1.getCantidad(), res.getCantidad())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void deleteAll() {
        try {
            carritoRepository.save(pTest1);
            carritoRepository.save(pTest2);
            carritoRepository.save(pTest3);
            carritoRepository.deleteAll();

            assertAll(
                    () -> assertTrue(carritoRepository.findAll().isEmpty()),
                    () -> assertTrue(carritoRepository.findById(pTest1.getUuid()).isEmpty()),
                    () -> assertTrue(carritoRepository.findById(pTest2.getUuid()).isEmpty()),
                    () -> assertTrue(carritoRepository.findById(pTest3.getUuid()).isEmpty())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void getTotal() {
        try {
            carritoRepository.save(pTest1);
            var res = carritoRepository.getTotal();
            assertAll(
                    () -> assertEquals(2f, res)
            );
        }catch (Exception e){
            fail();
        }
    }
}