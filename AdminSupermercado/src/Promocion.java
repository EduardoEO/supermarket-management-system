import java.time.LocalDate;

public class Promocion {
    private int id;
    private String descripcion;
    private double descuentoPorcentaje;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Promocion(int id, String descripcion, double descuentoPorcentaje, LocalDate fechaInicio, LocalDate fechaFin) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id de la promoción debe ser mayor a 0");
        }
        if (descuentoPorcentaje <= 0) {
            throw new IllegalArgumentException("El descuento de la promoción debe ser mayor a 0");
        }
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripción de la promoción no puede estar vacía");
        }
        this.id = id;
        this.descripcion = descripcion;
        this.descuentoPorcentaje = descuentoPorcentaje;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getDescuentoPorcentaje() {
        return descuentoPorcentaje;
    }

    public void setDescuentoPorcentaje(double descuento) {
        this.descuentoPorcentaje = descuento;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "Promoción: " + "Id: " + id + ", descripción: " + descripcion + ", descuento: " + descuentoPorcentaje +
                ", fechaInicio: " + fechaInicio +
                ", fechaFin: " + fechaFin;
    }
}