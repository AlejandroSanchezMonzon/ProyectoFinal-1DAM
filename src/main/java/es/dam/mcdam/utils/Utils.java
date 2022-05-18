package es.dam.mcdam.utils;

import java.io.IOException;

public class Utils {
    public static void openBrowser(String url) throws IOException {
        new ProcessBuilder("x-www-browser", url).start();
    }

    public static boolean isEmail(String email){
        String regex = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
        return email.matches(regex);
    }

}
