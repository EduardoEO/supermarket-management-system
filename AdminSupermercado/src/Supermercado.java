import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Supermercado {
    private String nombre;
    private ArrayList<Producto> listaProductos;
    private ArrayList<Empleado> empleados;
    private ArrayList<Operacion> almacen;
    private ArrayList<Pedido> pedidos;
    private ArrayList<Promocion> promociones;

    public Supermercado(String nombre) {
        this.nombre = nombre;
        this.almacen = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.listaProductos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.promociones = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }
    public ArrayList<Producto> getProductos() {
        return listaProductos;
    }
    public ArrayList<Operacion> getAlmacen() {
        return almacen;
    }
    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }
    public ArrayList<Promocion> getPromociones() {
        return promociones;
    }

    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
    }

    public void eliminarEmpleado(int idEmpleado) {
        empleados.removeIf(e -> e.getId() == idEmpleado);
    }

    public boolean comprarProductos(Producto producto, double cantidad, LocalDate fecha, double precio){
        if (cantidad <= 0 || precio <= 0) {
            throw new IllegalArgumentException("La cantidad y el precio deben ser mayores a 0");
        }
        boolean productoExistente = false;
        for (Operacion o : almacen){ // Recorrer el almacén para ver si el producto existe
            if (o.getProducto().getIdProducto() == producto.getIdProducto()){
                o.setCantidad(o.getCantidad() + cantidad); // Actualizar la cantidad de productos en el almacén
                productoExistente = true;
                break;
            }
        }
        if (!productoExistente){ // Si el producto no existe, se añade al almacén una nueva operación
            try {
                Operacion operacion = new Operacion(TipoOperacion.COMPRA, producto, cantidad, fecha, precio);
                almacen.add(operacion);
            }catch (Exception e){
                System.out.println("Error al comprar productos: " + e.getMessage());
                return false;
            }
        }
        return true; // Se realizó la compra correctamente
    }
    public boolean venderProductos(Producto producto, double cantidad, LocalDate fecha, double precio){
        if (cantidad <= 0 || precio <= 0) {
            throw new IllegalArgumentException("La cantidad y el precio deben ser mayores a 0");
        }
        boolean productoExistente = false;
        for (Operacion o : almacen){ // Buscar el producto en el almacén
            if (o.getProducto().getIdProducto() == producto.getIdProducto()){
                if (o.getCantidad() >= cantidad) {
                    o.setCantidad(o.getCantidad() - cantidad); // Actualizar la cantidad de productos en el almacén
                    productoExistente = true;
                } else {
                    System.out.println("No hay suficientes productos en el almacén");
                    return false;
                }
                break;
            }
        }
        if (!productoExistente) {
            System.out.println("El producto no se encuentra en el almacén");
            return false;
        }
        return true; // Se realizó la venta correctamente
    }

    public void mostrarProductosAlmacen(){
        System.out.println("===== Productos en Almacén =====");
        for (Operacion operacion : almacen) {
            String mensaje = "Producto: " + operacion.getProducto().getNombre() +
                    ", Cantidad: " + operacion.getCantidad() +
                    ", Precio de compra: " + operacion.getPrecioCompra();

            double precioVenta;
            if (!operacion.getProducto().getEsPack()) {
                precioVenta = operacion.getPrecioCompra() * 1.15;
                mensaje += ", Precio de venta (+15% sin pack): " + precioVenta;
            } else {
                precioVenta = operacion.getPrecioCompra() * 1.10;
                mensaje += ", Precio de venta (+10% con pack): " + precioVenta;
            }

            for (Promocion promocion : promociones) {
                if (promocion.getId() == operacion.getProducto().getIdProducto()) {
                    double precioConDescuento = precioVenta * (1 - promocion.getDescuentoPorcentaje() / 100);
                    mensaje += ", Precio con descuento: " + precioConDescuento;
                    break;
                }
            }

            System.out.println(mensaje);
        }
    }

    public boolean aplicarPromocion(int idProducto, Promocion promocion) {
        for (Operacion operacion : almacen) {
            if (operacion.getProducto().getIdProducto() == idProducto) {
                operacion.getProducto().setPrecio(operacion.getProducto().getPrecio() * (1 - promocion.getDescuentoPorcentaje() / 100));
                return true;
            }
        }
        System.out.println("Producto no encontrado en el almacén.");
        return false;
    }
}
