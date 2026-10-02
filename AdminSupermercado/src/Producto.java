import java.time.LocalDate;

public class Producto {
    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private TipoMedida tipoMedida;
    private LocalDate fechaCaducidad;
    boolean esPack;
    
    public Producto(int idProducto, String nombre, String descripcion, double precio, TipoMedida tipoMedida, LocalDate fechaCaducidad, boolean esPack) {
        if (idProducto < 0){
            throw new IllegalArgumentException("El id del producto no puede ser menor o igual a 0");
        }
        if (nombre == null || nombre.isEmpty()){
            throw new IllegalArgumentException("El nombre del producto no puede ser nulo o vacío");
        }
        if (descripcion == null || descripcion.isEmpty()){
            throw new IllegalArgumentException("La descripción del producto no puede ser nula o vacía");
        }
        if (precio <= 0){
            throw new IllegalArgumentException("El precio del producto no puede ser menor o igual a 0");
        }
        if (!tipoMedida.equals(TipoMedida.KILOGRAMOS) && !tipoMedida.equals(TipoMedida.LITROS) && !tipoMedida.equals(TipoMedida.UNIDADES)){
            throw new IllegalArgumentException("El tipo de medida debe ser KILOGRAMOS, LITROS o UNIDADES");
        }
        if (fechaCaducidad.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("La fecha de caducidad no puede ser anterior a la fecha actual");
        }
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipoMedida = tipoMedida;
        this.fechaCaducidad = fechaCaducidad;
        this.esPack = esPack;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public TipoMedida getTipoMedida() {
        return tipoMedida;
    }

    public void setTipoMedida(TipoMedida tipoMedida) {
        this.tipoMedida = tipoMedida;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public boolean getEsPack() {
        return esPack;
    }

    public void setEsPack(boolean esPack) {
        this.esPack = esPack;
    }

    @Override
    public String toString() {
        return "ID del Producto: " + idProducto +
                ", nombre: " + nombre + ", descripción: " + descripcion + ", precio: " + precio +
                ", tipoMedida: " + tipoMedida + ", fecha de Caducidad: " + fechaCaducidad +
                ", es Pack: " + esPack + ".";
    }
}
