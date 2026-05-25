package Clases;

public class Profesor extends Clases.Abstractas.Persona {
    private String departamento;
    private boolean fijo;
    
    public Profesor(String nombre, String dni, String departamento, boolean fijo) {
        super(nombre, dni);
        this.departamento = departamento;
        this.fijo = fijo;
    }

    @Override
    public boolean puedePedirLibros() {
        return true; // Los profesores pueden pedir libros sin restricciones
    }
    
}
