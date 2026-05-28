public class App {
    public static void main(String[] args) {
        try {
            int a = leerEntero("10");
            int b = leerEntero("0");
            System.out.println(dividir(a, b));
        } catch (NumberFormatException e) {
            System.out.println("Numero invalido: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Operacion invalida: " + e.getMessage());
        }
    }

    public static int leerEntero(String texto) throws NumberFormatException {
        return Integer.parseInt(texto);
    }

    public static int dividir(int a, int b) throws IllegalArgumentException {
        if (b == 0) {
            throw new IllegalArgumentException("No se puede dividir entre cero");
        }

        return a / b;
    }
}
