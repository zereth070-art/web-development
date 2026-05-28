import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class App {
    
    public static HashMap<String, Producto> productos = new HashMap<>();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Crear un objeto de la clase Producto
            Producto producto1 = new Producto("P001", "Laptop", 999.99, 10);
            Producto producto2 = new Producto("P002", "Smartphone", 499.99, 20);
            Producto producto3 = new Producto("P003", "Tablet", 299.99, 15);
    
            // Agregar productos a la lista
            App app = new App();
            app.productos.put(producto1.getCodigo(), producto1);
            app.productos.put(producto2.getCodigo(), producto2);
            app.productos.put(producto3.getCodigo(), producto3);
            
            double valorTotalInventario = 0.0;

            //Muestra los productos sin stock
            for(Producto producto : app.productos.values()){
                if(producto.getStock() == 0){
                    System.out.println("Producto sin stock: " + producto.getNombre());
                }
                //Calcula el valor total del inventario
                valorTotalInventario += producto.getPrecio() * producto.getStock();
            }
            System.out.println("Valor total del inventario: " + valorTotalInventario);
            //Vende unidades de un  producto

            //Comprueba si el producto existe antes de vender
                
    }

    public static void venderProducto(){
        String codigo;
        System.out.println("Ingrese el código del producto a vender: ");
        codigo = scanner.nextLine();

        Producto producto = productos.get(codigo);
        if (producto != null) {
            if (producto.getStock() > 0) {
                producto.setStock(producto.getStock() - 1);
                System.out.println("Producto vendido: " + producto.getNombre());
            } else {
                System.out.println("Producto sin stock: " + producto.getNombre());
            }
        } else {
            System.out.println("Producto no encontrado.");
        }
    }
}
