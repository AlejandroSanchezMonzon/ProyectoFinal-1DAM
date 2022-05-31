package es.dam.mcdam.services;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public interface Backup<T>{

    ObservableList<T> restore() throws IOException;
}
