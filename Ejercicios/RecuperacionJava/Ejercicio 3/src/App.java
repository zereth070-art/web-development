import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        HashMap<String, Integer> contadorPalabras = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src\\palabras.txt"))) {
            String linea;
            String palabraMasRepetida = "";
            int maxRepeticiones = 0;

            while ((linea = br.readLine()) != null) {
                String[] palabras = linea.split("[\\s\\p{Punct}]+"); // Dividir por espacios y signos de puntuación
            
                for (String palabra : palabras) {
                    if (!palabra.isBlank()){
                    contadorPalabras.put(palabra.toLowerCase(), contadorPalabras.getOrDefault(palabra.toLowerCase(), 0) + 1);
                    }
                }
            }

            for (HashMap.Entry<String, Integer> entry : contadorPalabras.entrySet()) {
                if (entry.getValue() > maxRepeticiones) {
                    maxRepeticiones = entry.getValue();
                    palabraMasRepetida = entry.getKey();
                }
            }

        System.out.println("La palabra más repetida es: " + palabraMasRepetida + " con " + maxRepeticiones + " repeticiones.");
        
    } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } 
}
}

