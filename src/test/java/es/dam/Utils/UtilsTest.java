package es.dam.Utils;

import es.dam.mcdam.utils.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test de la clase Utils")
public class UtilsTest {

    @Test
    public void isEmailTest() {
        String email = "email@dam.com";
        String emailIncorrecto = "email@dam";
        String emailIncorrecto2 = "emaildam.com";
        assertAll(
                () -> assertTrue(Utils.isEmail(email)),
                () -> assertFalse(Utils.isEmail(emailIncorrecto)),
                () -> assertFalse(Utils.isEmail(emailIncorrecto2))
        );
    }

    @Test
    public void isPasswordTest(){
        String password = "password";
        String passwordIncorrecto = "pass";
        String passwordIncorrecto2 = "passwor";
        String passwordIncorrecto3 = "pass34";
        assertAll(
                () -> assertTrue(Utils.isPassword(password)),
                () -> assertFalse(Utils.isPassword(passwordIncorrecto)),
                () -> assertFalse(Utils.isPassword(passwordIncorrecto2)),
                () -> assertFalse(Utils.isPassword(passwordIncorrecto3))
        );
    }


    @Test
    public void isTarjetaCreditoTest(){
        String tarjeta = "9382918439289203";
        String tarjetaIncorrecto = "098709870987098";
        String tarjetaIncorrecto2 = "12341234123412341";
        String tarjetaIncorrecto3 = "F234123412341234";
        assertAll(
                () -> assertTrue(Utils.isTarjetaCredito(tarjeta)),
                () -> assertFalse(Utils.isTarjetaCredito(tarjetaIncorrecto)),
                () -> assertFalse(Utils.isTarjetaCredito(tarjetaIncorrecto2)),
                () -> assertFalse(Utils.isTarjetaCredito(tarjetaIncorrecto3))
        );
    }
    

}
