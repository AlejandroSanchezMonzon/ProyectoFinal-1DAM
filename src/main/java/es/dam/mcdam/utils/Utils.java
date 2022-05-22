package es.dam.mcdam.utils;

import java.io.IOException;
import java.util.Optional;

public class Utils {
    public static void openBrowser(String url) throws IOException {
        new ProcessBuilder("x-www-browser", url).start();
    }

    public static boolean isEmail(String email){
        String regex = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
        return email.matches(regex);
    }

    public static boolean isTarjetaCredito(String tarjeta){
        String regex = "^[0-9]{16}$";
        return tarjeta.matches(regex);
    }

    public static boolean isPassword(String password){
        String regex = ".{8,}";
        return password.matches(regex);
    }
    public static Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }



}
