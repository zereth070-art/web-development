package Clases;

import java.util.HashMap;

public class Alumno extends Clases.Abstractas.Persona{
    private int curso;
    private HashMap<String, Integer> librosPrestados; // Mapa de título de libro a cantidad prestada


    public Alumno(String nombre, String dni) {
        super(nombre, dni);
        this.curso = curso;
        this.librosPrestados = librosPrestados;
    }


    @Override
    public boolean puedePedirLibros() {
        if (librosPrestados.size() > 3) {
            return true;
        }
        return false;
    }


    public int getCurso() {
        return curso;
    }


    @Override
    public String toString() {
        return "Alumno [curso=" + curso + ", librosPrestados=" + librosPrestados + "]";
    }


    public void setCurso(int curso) {
        this.curso = curso;
    }


    public HashMap<String, Integer> getLibrosPrestados() {
        return librosPrestados;
    }


    public void setLibrosPrestados(HashMap<String, Integer> librosPrestados) {
        this.librosPrestados = librosPrestados;
    }   
    
}
