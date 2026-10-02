public class Empleado {
    private int id;
    private String nombre;
    private boolean esAdministrador;
    private String contrasena;

    public Empleado(int id, String nombre, boolean esAdministrador, String contrasena) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("El id no puede ser menor o igual a 0");
        }
        if (contrasena == null || contrasena.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }
        this.id = id;
        this.nombre = nombre;
        this.esAdministrador = esAdministrador;
        this.contrasena = contrasena;
    }

    public int getId() {
        return id;
    }

    public String getContrasena() {
        return contrasena;
    }

    public boolean getEsAdministrador() {
        return esAdministrador;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "ID del Empleado: " + id +
                ", Nombre: " + nombre +
                ", Es Administrador: " + esAdministrador;
    }
}
