import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        File archivo = new File("RecuperacionJava\\Ejercicio 4\\src\\productos.txt");
        HashMap<String, Double> productos = new HashMap<>();
        try(BufferedReader  br = new BufferedReader(new FileReader(archivo))){
           String linea;
           while((linea = br.readLine()) != null){
            String[] partes = linea.split(",");
            if(partes.length == 2){
                String nombre = partes[0].trim();
                double precio = Double.parseDouble(partes[1].trim());
                productos.put(nombre, precio);
                
            } else {
                System.out.println("Formato incorrecto en la línea: " + linea);
            }
            productos.entrySet().
            stream().
            filter(entry -> entry.getValue() > 50).
            forEach(k ->
                System.out.println("producto: " + k.getKey() + ", precio: " + k.getValue())
            );
            
            System.out.println("Cantidad de productos con precio menor a 30: " +
            productos.entrySet().
            stream().
            filter(entry -> entry.getValue() < 30).count());

            productos.keySet().forEach( k ->
                System.out.println("Producto: " + k.toUpperCase())
            );

        }
        }catch(IOException e){
                System.out.println("Error al leer el archivo: " + e.getMessage());
        }

    }
}

