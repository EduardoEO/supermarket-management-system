import java.util.ArrayList;

public class GestorAutenticacion {
    public Empleado autenticar(int idEmpleado, String contrasena, ArrayList<Empleado> empleados) {
        for (Empleado e : empleados) {
            if (e.getId() == idEmpleado && e.getContrasena().equals(contrasena)) {
                return e;
            }
        }
        return null; // No autenticado
    }
}
