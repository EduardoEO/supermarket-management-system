import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class Pedido {
    private ArrayList<Producto> pedidos;
    private int idPedido;
    private LocalDate fechaDePedido;
    private LocalDate fechaDeEntrega;
    private Map<Producto, Integer> cantidades;

    public Pedido(int idPedido, LocalDate fechaDePedido, LocalDate fechaDeEntrega, Map<Producto, Integer> cantidades) {
        if (idPedido < 0) {
            throw new IllegalArgumentException("El id del pedido no puede ser menor o igual a 0");
        }
        if (fechaDePedido == null || fechaDeEntrega == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        if (cantidades == null || cantidades.isEmpty() || cantidades.values().stream().anyMatch(cantidad -> cantidad <= 0)) {
            throw new IllegalArgumentException("No se puede crear un pedido sin cantidades válidas.");
        }
        if (fechaDeEntrega.isBefore(fechaDePedido)) {
            throw new IllegalArgumentException("La fecha de entrega no puede ser anterior a la fecha de pedido.");
        }
        this.idPedido = idPedido;
        this.fechaDePedido = fechaDePedido;
        this.fechaDeEntrega = fechaDeEntrega;
        this.cantidades = cantidades;
    }

    public ArrayList<Producto> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Producto> pedidos) {
        this.pedidos = pedidos;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDate getFechaDePedido() {
        return fechaDePedido;
    }

    public void setFechaDePedido(LocalDate fechaDePedido) {
        this.fechaDePedido = fechaDePedido;
    }

    public LocalDate getFechaDeEntrega() {
        return fechaDeEntrega;
    }

    public void setFechaDeEntrega(LocalDate fechaDeEntrega) {
        this.fechaDeEntrega = fechaDeEntrega;
    }

    public Map<Producto, Integer> getCantidades() {
        return cantidades;
    }

    public void setCantidades(Map<Producto, Integer> cantidades) {
        this.cantidades = cantidades;
    }

    @Override
    public String toString() {
        return "Pedido:" + "Id del pedido: " + idPedido +
                ", Fecha De Pedido: " + fechaDePedido +
                ", Fecha De Entrega: " + fechaDeEntrega +
                ", cantidades: " + cantidades;
    }
}
