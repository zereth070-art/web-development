import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import Clases.Alumno;

public class App {
    public static HashMap<String, Alumno> alumnos = new HashMap<>();
    public static HashMap<String, Clases.Profesor> profesores = new HashMap<>();

    public static void main(String[] args) {
        try {
            agregarAlumno();
            agregarProfesor();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException rte) {
            throw new RuntimeException(rte);
        }
    }
    
    public static void agregarAlumno() throws IOException , RuntimeException {
        String nombre;
        String dni;
        try (BufferedReader br = new BufferedReader(new FileReader("alumnos.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                nombre = datos[0];
                dni = datos[1];
                Alumno alumno = new Alumno(nombre, dni);
                alumnos.put(dni, alumno);
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);    
        }catch (RuntimeException rte) {
            throw new RuntimeException(rte);
        }
    }

    public static void agregarProfesor() throws IOException , RuntimeException {
        String nombre;
        String dni;
        String departamento;
        boolean fijo;
        try (BufferedReader br = new BufferedReader(new FileReader("profesores.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(",");
                nombre = datos[0];
                dni = datos[1];
                departamento = datos[2];
                fijo = Boolean.parseBoolean(datos[3]);
                Clases.Profesor profesor = new Clases.Profesor(nombre, dni, departamento, fijo);
                profesores.put(dni, profesor);
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);    
        }catch (RuntimeException rte) {
            throw new RuntimeException(rte);
        }
    }

    public static void mostrarAlumnos() {
        for (Alumno alumno : alumnos.values()) {
           if (alumno.getCurso() > 2) {
            System.out.println(alumno.toString());
           }     
        }
    }

    public static void puedenPedirLibro(){
        for (Alumno alumno: alumnos.values()) {
            if (alumno.puedePedirLibros() == true) {
                System.out.println(alumno.toString());
            }
        }

        for (Clases.Profesor profesor: profesores.values()) {
                    System.out.println(profesor.toString());
                
            }
    }

    public static void añadirPrestamo(String dniAlumno, String tituloLibro) {
        Alumno alumno = alumnos.get(dniAlumno);
        if (alumno != null) {
            HashMap<String, Integer> librosPrestados = alumno.getLibrosPrestados();
            librosPrestados.put(tituloLibro, librosPrestados.getOrDefault(tituloLibro, 0) + 1);
            alumno.setLibrosPrestados(librosPrestados);
        } else {
            System.out.println("Alumno no encontrado.");
        }
    }
}
