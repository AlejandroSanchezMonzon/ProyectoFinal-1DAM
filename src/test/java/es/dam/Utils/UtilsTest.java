package es.dam.mcdam.Utils;

import es.dam.mcdam.utils.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test de la clase Utils")
public class UtilsTest {

    @Test
    public void isEmailtest() {
        String email = "email@dam.com";
        String emailIncorrecto = "email@dam";
        String emailIncorrecto2 = "emaildam.com";
        assertAll(
                () -> assertTrue(Utils.isEmail(email)),
                () -> assertFalse(Utils.isEmail(emailIncorrecto)),
                () -> assertFalse(Utils.isEmail(emailIncorrecto2))
        );

    }

}
