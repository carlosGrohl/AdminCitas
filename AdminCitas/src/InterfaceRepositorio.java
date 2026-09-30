import java.util.List;

//Plantilla generica: T es el tipo de objeto que maneja el repositorio (Doctor, Paciente, Cita)
public interface InterfaceRepositorio<T> {

    boolean guardar(T entidad);
    boolean eliminar(String id);
    T buscarPorId(String id);
    List<T> listarTodos();
    boolean existe(String id);
}
