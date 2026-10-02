import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InicioSesion {
    private static final Scanner scanner = new Scanner(System.in);

    public static void menuPrincipal(Supermercado supermercado) {
        GestorAutenticacion gestorAutenticacion = new GestorAutenticacion();
        boolean continuar = true;

        while (continuar) {
            System.out.println("===== Menú de inicio de sesión =====");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Salir");
            System.out.print("Elige una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("ID de empleado: ");
                    int idEmpleado = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Contraseña: ");
                    String contrasena = scanner.nextLine();

                    Empleado empleado = gestorAutenticacion.autenticar(idEmpleado, contrasena, supermercado.getEmpleados());
                    if (empleado != null) {
                        if (empleado.getEsAdministrador()) {
                            menuAdministrador(empleado, supermercado);
                        } else {
                            menuEmpleado(empleado, supermercado);
                        }
                    } else {
                        System.out.println("Credenciales incorrectas.");
                    }
                    break;
                case 2:
                    System.out.println("Saliendo del programa...");
                    continuar = false;
                    scanner.close();
                    break;
                default:
                    System.out.println("Opción no válida, por favor intenta de nuevo.");
                    break;
            }
            System.out.println();
        }
    }

    public static boolean menuEmpleado(Empleado empleado, Supermercado supermercado) {
        boolean continuar = true;
        boolean salirAlPrincipal = false;

        while(continuar){
            System.out.println("===== Menú de Empleados =====");
            System.out.println("1. Gestionar productos");
            System.out.println("2. Gestionar pedidos");
            System.out.println("3. Aplicar promoción");
            if (empleado.getEsAdministrador()) {
                System.out.println("4. Volver al menú de administrador");
            }
            System.out.println("5. Volver al menú principal");
            System.out.print("Elige una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    menuGestionProductos(empleado, supermercado);
                    break;
                case 2:
                    menuGestionPedidos(empleado, supermercado);
                    break;
                case 3: // Aplicar promoción
                    aplicarPromocion(supermercado);
                    break;
                case 4:
                    if (empleado.getEsAdministrador()){
                        System.out.println("Regresando al menú de administrador...");
                        continuar = false;
                        salirAlPrincipal = false;
                    } else {
                        System.out.println("No tienes los permisos necesarios.");
                    }
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    continuar = false;
                    salirAlPrincipal = true;
                    break;
                default:
                    System.out.println("Opción no válida, por favor intenta de nuevo.");
                    break;
            }
            System.out.println();
        }
        return salirAlPrincipal;
    }

    private static void aplicarPromocion(Supermercado supermercado) {
        // Solicitar el ID del producto
        System.out.println("Introduce el ID del producto del almacén al que deseas aplicar la promoción: ");
        int idProducto = scanner.nextInt();

        // Solicitar el ID de la promoción
        System.out.println("Introduce el ID de la promoción: ");
        int idPromocion = scanner.nextInt();

        // Validar la promoción
        Promocion promocion = null;
        for (Promocion p : supermercado.getPromociones()) {
            if (p.getId() == idPromocion) {
                promocion = p;
                break;
            }
        }
        if (promocion == null) {
            System.out.println("Promoción no encontrada.");
            return;
        }

        // Validar si la promoción está activa (opcional)
        if (LocalDate.now().isBefore(promocion.getFechaInicio()) || LocalDate.now().isAfter(promocion.getFechaFin())) {
            System.out.println("La promoción no está activa actualmente.");
            return;
        }

        // Aplicar descuento al producto en el almacén
        boolean descuentoAplicado = false;
        for (Operacion operacion : supermercado.getAlmacen()) {
            Producto producto = operacion.getProducto();
            if (producto.getIdProducto() == idProducto) {
                double precioOriginal = producto.getPrecio();
                double precioConDescuento = precioOriginal - (precioOriginal * promocion.getDescuentoPorcentaje() / 100);
                operacion.getProducto().setPrecio(precioConDescuento);
                descuentoAplicado = true;
            }
        }

        // Verificar si se aplicó el descuento
        if (descuentoAplicado) {
            System.out.println("Descuento aplicado a todas las instancias del producto en el almacén.");
        } else {
            System.out.println("Producto no encontrado en el almacén.");
        }
    }

    private static void menuGestionProductos(Empleado empleado, Supermercado supermercado) {
        boolean continuar = true;

        while (continuar) {
            System.out.println("===== Gestión de Productos =====");
            System.out.println("1. Añadir producto");
            System.out.println("2. Modificar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Comprar productos");
            System.out.println("5. Vender productos");
            System.out.println("6. Mostrar productos en el almacén");
            System.out.println("7. Mostrar todos los productos registrados");
            System.out.println("8. Volver al menú de empleado");
            System.out.print("Elige una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    agregarProducto(supermercado);
                    break;
                case 2:
                    modificarProducto(supermercado);
                    break;
                case 3:
                    eliminarProducto(supermercado);
                    break;
                case 4: // Comprar productos
                    comprarProductos(supermercado);
                    break;
                case 5: // vender productos
                    venderProductos(supermercado);
                    break;
                case 6: // Mostrar productos en mi almacén
                    supermercado.mostrarProductosAlmacen();
                    break;
                case 7:
                    System.out.println("Mostrando todos los productos...");
                    for (Producto p : supermercado.getProductos()) {
                        System.out.println(p);
                    }
                    break;
                case 8:
                    System.out.println("Volviendo al menú de empleado...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida, por favor intenta de nuevo.");
                    break;
            }
            System.out.println();
        }

    }

    private static void agregarProducto(Supermercado supermercado) {
        System.out.println("Introduce los datos del producto:");
        System.out.println("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpia el buffer
        System.out.println("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.println("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.println("Precio: ");
        double precio = scanner.nextDouble();
        System.out.println("Tipo de medida(KILOGRAMOS, LITROS, UNIDADES): ");
        TipoMedida tipoMedida = TipoMedida.valueOf(scanner.next()); // convierte el texto introducido en un objeto TipoMedida (debe ser exactamente igual al nombre del enum)
        System.out.println("Fecha de caducidad (yyyy-mm-dd): ");
        LocalDate fechaCaducidad = LocalDate.parse(scanner.next()); // convierte el texto introducido en un objeto LocalDate (debe ser exactamente igual al formato yyyy-mm-dd)
        System.out.println("¿Es un pack? (true/false): ");
        boolean esPack = scanner.nextBoolean();
        Producto productoAnanido = new Producto(id, nombre, descripcion, precio, tipoMedida, fechaCaducidad, esPack);

        boolean productoExistente = false;
        for (Producto p : supermercado.getProductos()) {
            if (p.getIdProducto() == productoAnanido.getIdProducto()) {
                System.out.println("El ID del producto ya existe.");
                productoExistente = true;
                break;
            }
        }
        if (!productoExistente) {
            supermercado.getProductos().add(productoAnanido);
            System.out.println("Producto añadido exitosamente.");
        }
    }

    private static void modificarProducto(Supermercado supermercado) {
        System.out.println("Introduce el ID del producto a modificar: ");
        int id = scanner.nextInt();
        Producto productoModificar = null;
        for (Producto p : supermercado.getProductos()) {
            if (p.getIdProducto() == id) {
                productoModificar = p;
                break;
            }
        }
        if (productoModificar != null) {
            scanner.nextLine(); // Limpia el buffer
            System.out.println("Introduce los nuevos datos del producto:");
            System.out.println("Nombre (" + productoModificar.getNombre() + "): ");
            String nombre = scanner.nextLine();
            if (!nombre.isEmpty()) {
                productoModificar.setNombre(nombre);
            }
            System.out.println("Descripción (" + productoModificar.getDescripcion() + "): ");
            String descripcion = scanner.nextLine();
            if (!descripcion.isEmpty()) {
                productoModificar.setDescripcion(descripcion);
            }
            System.out.println("Precio (" + productoModificar.getPrecio() + "): ");
            double precio = scanner.nextDouble();
            if (precio > 0) {
                productoModificar.setPrecio(precio);
            }
            System.out.println("Tipo de medida(KILOGRAMOS, LITROS, UNIDADES)(" + productoModificar.getTipoMedida() + "): ");
            TipoMedida tipoMedida = TipoMedida.valueOf(scanner.next());
            productoModificar.setTipoMedida(tipoMedida);
            System.out.println("Fecha de caducidad (" + productoModificar.getFechaCaducidad() + "): ");
            LocalDate fechaCaducidad = LocalDate.parse(scanner.next());
            if (fechaCaducidad.isAfter(LocalDate.now())) {
                productoModificar.setFechaCaducidad(fechaCaducidad);
            }
            System.out.println("¿Es un pack? (true/false)(" + productoModificar.getEsPack() + "): ");
            boolean esPack = scanner.nextBoolean();
            productoModificar.setEsPack(esPack);
            System.out.println("Producto modificado exitosamente.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void eliminarProducto(Supermercado supermercado) {
        System.out.println("Introduce el ID del producto a eliminar: ");
        int id = scanner.nextInt();
        supermercado.getProductos().removeIf(p -> p.getIdProducto() == id);
        supermercado.getAlmacen().removeIf(o -> o.getProducto().getIdProducto() == id);
        System.out.println("Producto eliminado exitosamente.");
    }

    private static void comprarProductos(Supermercado supermercado){
        System.out.println("Introduce el ID del producto a comprar: ");
        int id = scanner.nextInt();
        Producto productoComprar = null;
        for (Producto p : supermercado.getProductos()) {
            if (p.getIdProducto() == id) {
                productoComprar = p;
                break;
            }
        }
        if (productoComprar != null) {
            System.out.println("Introduce la cantidad a comprar: ");
            double cantidad = scanner.nextDouble();
            double precio = productoComprar.getPrecio();
            LocalDate fecha = LocalDate.now();
            boolean compraExitosa = supermercado.comprarProductos(productoComprar, cantidad, fecha, precio);
            if (compraExitosa) {
                System.out.println("Compra realizada exitosamente.");
            } else {
                System.out.println("No se pudo realizar la compra.");
            }
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void venderProductos(Supermercado supermercado){
        System.out.println("Introduce el ID del producto a vender: ");
        int id = scanner.nextInt();
        Producto productoVender = null;
        for (Producto p : supermercado.getProductos()) {
            if (p.getIdProducto() == id) {
                productoVender = p;
                break;
            }
        }
        if (productoVender != null) {
            System.out.println("Introduce la cantidad a vender: ");
            double cantidad = scanner.nextDouble();
            System.out.println("Introduce el precio de venta: ");
            double precio = scanner.nextDouble();
            LocalDate fecha = LocalDate.now();
            boolean ventaExitosa = supermercado.venderProductos(productoVender, cantidad, fecha, precio);
            if (ventaExitosa) {
                System.out.println("Venta realizada exitosamente.");
            } else {
                System.out.println("No se pudo realizar la venta.");
            }
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    public static void menuGestionPedidos(Empleado empleado, Supermercado supermercado){
        boolean continuar = true;


        while (continuar) {
            System.out.println("===== Gestión de Pedidos =====");
            System.out.println("1. Mostrar pedidos");
            System.out.println("2. Modificar pedidos");
            System.out.println("3. Eliminar pedidos");
            System.out.println("4. Volver al menú de empleado");
            System.out.print("Elige una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion){
                case 1: // Mostrar pedidos
                    mostrarPedidos(supermercado);
                    break;
                case 2: // Modificar pedidos
                    modificarPedido(supermercado);
                    break;
                case 3: // Eliminar pedidos
                    eliminarPedido(supermercado);
                    break;
                case 4:
                    System.out.println("Volviendo al menú de empleado...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida, por favor intenta de nuevo.");
                    break;
            }
            System.out.println();
        }

    }

    private static void mostrarPedidos(Supermercado supermercado) {
        System.out.println("===== Pedidos registrados =====");
        for (Pedido p : supermercado.getPedidos()) {
            System.out.println(p);
        }
    }

    private static void modificarPedido(Supermercado supermercado) {
        System.out.println("Introduce el ID del pedido a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpia el buffer

        Pedido pedidoModificar = null;
        for (Pedido p : supermercado.getPedidos()) {
            if (p.getIdPedido() == id) {
                pedidoModificar = p;
                break;
            }
        }
        if (pedidoModificar != null) {
            System.out.println("Introduce los nuevos datos del pedido:");
            System.out.println("Fecha de pedido actual(" + pedidoModificar.getFechaDePedido() + " - yyyy-mm-dd). Nueva fecha de pedido: ");
            LocalDate fechaPedido = LocalDate.parse(scanner.next());
            System.out.println("Fecha de entrega actual(" + pedidoModificar.getFechaDeEntrega() + " - yyyy-mm-dd). Nueva fecha de entrega: ");
            LocalDate fechaEntrega = LocalDate.parse(scanner.next());
            System.out.println("Introduce las nuevas cantidades(ID del producto y nueva cantidad de enteros // Termina con -1): ");
            Map<Producto, Integer> nuevasCantidades = new HashMap<>();
            while (true) {
                System.out.println("ID del producto: ");
                int productoId = scanner.nextInt();
                if (productoId == -1) {
                    break;
                }
                System.out.println("Nueva cantidad: ");
                int cantidad = scanner.nextInt();
                scanner.nextLine();
                Producto producto = buscarProductoPorId(productoId, supermercado.getProductos());
                if (producto != null) {
                    nuevasCantidades.put(producto, cantidad);
                } else {
                    System.out.println("Producto no encontrado.");
                }
            }
            pedidoModificar.setCantidades(nuevasCantidades);
            System.out.println("Pedido modificado exitosamente.");
        }else {
            System.out.println("Pedido no encontrado.");
        }
    }

    private static Producto buscarProductoPorId(int productoId, ArrayList<Producto> productos) {
        for (Producto p : productos) {
            if (p.getIdProducto() == productoId) {
                return p;
            }
        }
        return null;
    }

    private static void eliminarPedido(Supermercado supermercado) {
        System.out.println("Introduce el ID del pedido a eliminar: ");
        int id = scanner.nextInt();
        Pedido pedidoEliminar = null;
        for (Pedido p : supermercado.getPedidos()) {
            if (p.getIdPedido() == id) {
                pedidoEliminar = p;
                break;
            }
        }
        if (pedidoEliminar != null) {
            supermercado.getPedidos().remove(pedidoEliminar);
            System.out.println("Pedido eliminado exitosamente.");
        } else {
            System.out.println("Pedido no encontrado.");
        }
    }

    public static void menuAdministrador(Empleado empleado, Supermercado supermercado) {
        boolean continuar = true;

        while (continuar) {
            System.out.println("===== Menú de Administrador =====");
            System.out.println("1. Menú empleado");
            System.out.println("2. Añadir empleado");
            System.out.println("3. Eliminar empleado");
            System.out.println("4. Ver empleados");
            System.out.println("5. Crear promoción");
            System.out.println("6. Eliminar promoción");
            System.out.println("7. Ver promociones");
            System.out.println("8. Volver al menú principal");
            System.out.print("Elige una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    boolean salirAlPrincipal = menuEmpleado(empleado, supermercado);
                    if (salirAlPrincipal) {
                        continuar = false;
                    }
                    break;
                case 2:
                    agregarEmpleado(supermercado);
                    break;
                case 3:
                    eliminarEmpleado(supermercado);
                    break;
                case 4: // Ver empleados
                    System.out.println("===== Empleados registrados =====");
                    for (Empleado e : supermercado.getEmpleados()) {
                        System.out.println(e);
                    }
                    break;
                case 5: // Crear promoción
                    crearPromocion(supermercado);
                    break;
                    case 6: // Eliminar promoción
                        eliminarPromocion(supermercado);
                        break;
                case 7: // Ver promociones
                    System.out.println("===== Promociones registradas =====");
                    for (Promocion p : supermercado.getPromociones()) {
                        System.out.println(p);
                    }
                    break;
                case 8:
                    System.out.println("Volviendo al menú principal...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida, por favor intenta de nuevo.");
                    break;
            }
            System.out.println();
        }

    }

    private static void agregarEmpleado(Supermercado supermercado) {
        System.out.println("Introduce los datos del nuevo empleado:");
        System.out.println("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpia el buffer
        System.out.println("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.println("¿Es administrador? (true/false): ");
        boolean esAdministrador = scanner.nextBoolean();
        System.out.println("Contraseña: ");
        String contrasena = scanner.next();
        Empleado nuevoEmpleado = new Empleado(id, nombre, esAdministrador, contrasena);
        boolean empleadoExistente = false;
        for (Empleado e : supermercado.getEmpleados()) {
            if (e.getId() == nuevoEmpleado.getId()) {
                System.out.println("El empleado ya existe.");
                empleadoExistente = true;
                break;
            }
        }
        if (!empleadoExistente) {
            supermercado.agregarEmpleado(nuevoEmpleado);
            System.out.println("Empleado añadido exitosamente.");
        }
    }

    private static void eliminarEmpleado(Supermercado supermercado) {
        System.out.println("Introduce el ID del empleado a eliminar: ");
        int id = scanner.nextInt();
        Empleado empleadoEliminar = null;
        for (Empleado e : supermercado.getEmpleados()) {
            if (e.getId() == id) {
                empleadoEliminar = e;
                break;
            }
        }
        if (empleadoEliminar != null) {
            supermercado.eliminarEmpleado(id);
            System.out.println("Empleado eliminado exitosamente.");
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }

    private static void crearPromocion(Supermercado supermercado) {
        System.out.println("Introduce el ID de la promoción: ");
        int idPromocion = scanner.nextInt();
        scanner.nextLine(); // Limpia el buffer
        System.out.println("Descripción de la promoción: ");
        String descripcion = scanner.nextLine();
        System.out.println("Descuento (%): ");
        double descuento = scanner.nextDouble();
        System.out.println("Fecha de inicio (yyyy-mm-dd): ");
        LocalDate fechaInicio = LocalDate.parse(scanner.next());
        System.out.println("Fecha de fin (yyyy-mm-dd): ");
        LocalDate fechaFin = LocalDate.parse(scanner.next());

        boolean promocionExistente = false;
        for (Promocion p : supermercado.getPromociones()) {
            if (p.getId() == idPromocion) {
                System.out.println("El Id de la promoción ya existe.");
                promocionExistente = true;
                break;
            }
        }
        if (!promocionExistente) {
            Promocion promocion = new Promocion(idPromocion, descripcion, descuento, fechaInicio, fechaFin);
            supermercado.getPromociones().add(promocion);
            System.out.println("Promoción creada exitosamente.");
        }
    }
    private static void eliminarPromocion(Supermercado supermercado){
        System.out.println("Introduce el ID de la promoción a eliminar: ");
        int idPromocion = scanner.nextInt();
        Promocion promocionEliminar = null;
        for (Promocion p : supermercado.getPromociones()) {
            if (p.getId() == idPromocion) {
                promocionEliminar = p;
                break;
            }
        }
        if (promocionEliminar != null) {
            supermercado.getPromociones().remove(promocionEliminar);
            System.out.println("Promoción eliminada exitosamente.");
        } else {
            System.out.println("Promoción no encontrada.");
        }
    }
}
