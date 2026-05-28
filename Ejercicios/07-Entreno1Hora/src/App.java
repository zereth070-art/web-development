import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        HashMap<String, Aula> aulas = new HashMap<>();
        ArrayList<String> reservasValidas = new ArrayList<>();

        // 1. Leer aulas y guardarlas en el mapa.

        // 2. Leer reservas y validar.

        // 3. Mostrar validas y rechazadas.

        // 4. Escribir salida/reservas_validas.txt.
    }

    public static void cargarAulas(HashMap<String, Aula> aulas) {
        Path ruta = Path.of("datos", "aulas.txt");

        try (BufferedReader br = Files.newBufferedReader(ruta)) {
            String linea;

            while ((linea = br.readLine()) != null) {
                // TODO: split, validar campos, crear Aula y meter en HashMap.
            }
        } catch (IOException e) {
            System.out.println("Error leyendo aulas: " + e.getMessage());
        }
    }

    public static void procesarReservas(HashMap<String, Aula> aulas, ArrayList<String> reservasValidas) {
        Path ruta = Path.of("datos", "reservas.txt");

        try (BufferedReader br = Files.newBufferedReader(ruta)) {
            String linea;

            while ((linea = br.readLine()) != null) {
                // TODO: split, validar campos, buscar aula y decidir si vale.
            }
        } catch (IOException e) {
            System.out.println("Error leyendo reservas: " + e.getMessage());
        }
    }

    public static void escribirReservasValidas(ArrayList<String> reservasValidas) {
        Path carpetaSalida = Path.of("salida");
        Path archivoSalida = carpetaSalida.resolve("reservas_validas.txt");

        try {
            Files.createDirectories(carpetaSalida);

            try (BufferedWriter bw = Files.newBufferedWriter(archivoSalida)) {
                for (String reserva : reservasValidas) {
                    bw.write(reserva);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo reservas: " + e.getMessage());
        }
    }
}
