import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    public static ArrayList<Tarea> tareas = new ArrayList<>();
    public static void main(String[] args) {
        int opcion = 0; 
        do {
           try {
             System.out.println("Menú:");
            System.out.println("1. Leer tareas");
            System.out.println("2. Mostrar pendientes");
            System.out.println("3. Contar tareas hechas");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(System.console().readLine());
                switch (opcion) {
                    case 1:
                        leerTareas();
                        break;
                    case 2:
                        mostrarPendientes();
                        break;
                    case 3:
                        contarTareasHechas();
                        break;
                    case 4:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
                continue;
            }
           } catch (IOException e) {
            System.out.println("Error de lectura/escritura: " + e.getMessage());
           }

        }while (opcion != 4 );
    }

    public static void leerTareas() throws IOException{
        File archivoTareas = new File("Ejercicios/RecuperacionJava/Ejercicio 2/src/tareas.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(archivoTareas))) {
            String linea;
            while ((linea = br.readLine()) != null) {
               String[] partes = linea.split(",");
                String nombreTarea = partes[0].trim();
                String hecho = partes[1].trim();
                tareas.add(new Tarea(nombreTarea, hecho));
                System.out.println("Tarea: " + nombreTarea + " - Hecho: " + hecho);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        }
    }   

    public static void mostrarPendientes()  {
        for (Tarea tarea : tareas) {
            if (tarea.getEstado().equalsIgnoreCase("pendiente")) {
                System.out.println("Tarea pendiente: " + tarea.getNombre());
            }
        }
    }

    public static void contarTareasHechas() {
        int contador = 0;
        for (Tarea tarea : tareas) {
            if (tarea.getEstado().equalsIgnoreCase("hecho")) {
                contador++;
            }
        }
        System.out.println("Número de tareas hechas: " + contador);
    }
}
