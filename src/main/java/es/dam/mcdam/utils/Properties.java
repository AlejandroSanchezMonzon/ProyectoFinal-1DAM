package es.dam.mcdam.utils;

import java.io.File;

public class Properties {
    //Base de datos
    private static final String APP_PATH = System.getProperty("user.dir");
    public static final String DB_DIR = APP_PATH + File.separator + "db";
    public static final String DB_FILE = DB_DIR + File.separator + "mcdamsql.sql";

    //General
    public static final String APP_TITLE = "MCDAM";

    //Splash
    public static final int SPLASH_HEIGHT = 500;

    public static final int SPLASH_WIDTH = 500;



    //Acerca de
    public static final String APP_LOGO = "images/logo.png";
    public static final String APP_VERSION = "1.0";
    public static final String APP_AUTHOR = "· Alejandro Sánchez Monzón \n"
                                            + "· Mireya Sánchez Pinzón \n"
                                            + "· Ainhoa Bermejo Esteban \n"
                                            + "· Rubén García-Redondo Marín \n"
                                            + "· Sergio Pérez Fernández";
    public static final String ACERCADE_LINK = "https://github.com/Mireyasanche/McDAM";
    public static final int ACERCADE_HEIGHT = 300;
    public static final int ACERCADE_WIDTH = 500;



}
