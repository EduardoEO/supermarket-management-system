import java.time.LocalDate;

public class Operacion {
    private TipoOperacion tipoOperacion;
    private Producto producto;
    double cantidad;
    private LocalDate fecha;
    private double precioCompra;


    public Operacion(TipoOperacion tipoOperacion, Producto producto, double cantidad, LocalDate fecha, double precioCompra) throws Exception {
        if(cantidad <= 0){
            throw new Exception();
        }
        if(precioCompra <= 0){
            throw new Exception();
        }
        if (fecha.isBefore(LocalDate.now())) {
            throw new Exception();
        }
        this.tipoOperacion = tipoOperacion;
        this.producto = producto;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.precioCompra = precioCompra;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getCantidad() {
        return cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
}
