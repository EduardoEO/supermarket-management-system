import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Supermercado supermercado = new Supermercado("Mercadona");
            Empleado empleado = new Empleado(1, "Paco", false, "12Hola");
            Empleado empleado1 = new Empleado(2, "Sara", true, "Hola1234");
            Empleado empleado2 = new Empleado(3, "Carlos", false, "Carlos123");
            Empleado empleado3 = new Empleado(4, "Lucia", false, "Lucia123");
            Empleado empleado4 = new Empleado(5, "Manuel", false, "Manu123");
            Empleado empleado5 = new Empleado(6, "Elena", false, "Elena123");
            Empleado empleado6 = new Empleado(7, "David", false, "David123");
            Empleado empleado7 = new Empleado(8, "Laura", true, "LauraAdmin");

            Producto producto = new Producto(1, "Leche", "desnatada", 2.5, TipoMedida.LITROS, LocalDate.of(2222, 12, 5), true);
            Producto producto1 = new Producto(2, "Pan", "integral", 1.5, TipoMedida.UNIDADES, LocalDate.of(2222, 11, 20), false);
            Producto producto2 = new Producto(3, "Atún", "en aceite", 3.5, TipoMedida.UNIDADES, LocalDate.of(2222, 12, 5), true);
            Promocion promocion = new Promocion(1, "Black Friday", 0.5, LocalDate.now(), LocalDate.now().plusDays(5));
            Promocion promocion1 = new Promocion(2, "Navidad", 0.6, LocalDate.of(2001, 12, 12), LocalDate.of(2001, 12, 12).plusDays(20));

            supermercado.agregarEmpleado(empleado);
            supermercado.agregarEmpleado(empleado1);
            supermercado.agregarEmpleado(empleado2);
            supermercado.agregarEmpleado(empleado3);
            supermercado.agregarEmpleado(empleado4);
            supermercado.agregarEmpleado(empleado5);
            supermercado.agregarEmpleado(empleado6);
            supermercado.agregarEmpleado(empleado7);
            supermercado.getProductos().add(producto);
            supermercado.getProductos().add(producto1);
            supermercado.getProductos().add(producto2);
            supermercado.getPromociones().add(promocion);
            supermercado.getPromociones().add(promocion1);

            supermercado.comprarProductos(producto, 1000, LocalDate.now(), 2.5);
            supermercado.comprarProductos(producto1, 500, LocalDate.now(), 1.5);

            // Crear un nuevo pedido
            Map<Producto, Integer> cantidades = Map.of(producto, 10, producto1, 20);
            Map<Producto, Integer> cantidades1 = Map.of(producto2, 50);
            Pedido pedido = new Pedido(1 ,LocalDate.now(), LocalDate.now().plusDays(5), cantidades);
            Pedido pedido1 = new Pedido(2, LocalDate.now(), LocalDate.now().plusDays(7), cantidades1);
            supermercado.getPedidos().add(pedido);
            supermercado.getPedidos().add(pedido1);

            InicioSesion.menuPrincipal(supermercado);

        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Algo ha fallado: " + e.getMessage());
        } 



    }



}