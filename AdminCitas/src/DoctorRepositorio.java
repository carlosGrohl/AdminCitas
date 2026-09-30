import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Formato del archivo: Id,Nombre,Especialidad
public class DoctorRepositorio implements InterfaceRepositorio<Doctor> {

    //Atributos
    private String archivo;

    //Constructor
    DoctorRepositorio(String archivo){
        this.archivo = archivo;
    }

    //Metodos de la interfaz
    @Override
    public boolean guardar(Doctor entidad){
        if (existe(entidad.getId())) return false; //No se permiten Id repetidos
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))){
            bw.write(aLinea(entidad));
            bw.newLine();
            return true;
        } catch (IOException e){
            IO.println("Error al guardar el doctor.");
            return false;
        }
    }

    @Override
    public boolean eliminar(String id){
        List<Doctor> doctores = listarTodos();
        boolean eliminado = doctores.removeIf(d -> d.getId().equals(id));
        if (eliminado) escribirTodos(doctores);
        return eliminado;
    }

    @Override
    public Doctor buscarPorId(String id){
        for (Doctor d : listarTodos()){
            if (d.getId().equals(id)) return d;
        }
        return null;
    }

    @Override
    public List<Doctor> listarTodos(){
        List<Doctor> doctores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))){
            String registro;
            //recorre el archivo
            while ((registro = br.readLine()) != null){
                String[] datos = registro.split(",");
                if (datos.length < 3) continue; //ignora lineas vacias o mal formadas
                doctores.add(new Doctor(datos[0].trim(), datos[1].trim(), datos[2].trim()));
            }
        } catch (FileNotFoundException e){
            //Si el archivo aun no existe, simplemente no hay doctores
        } catch (IOException e){
            IO.println("Error al leer el archivo de doctores.");
        }
        return doctores;
    }

    @Override
    public boolean existe(String id){
        return buscarPorId(id) != null;
    }

    //Metodos propios
    List<Doctor> buscarPorEspecialidad(String Especialidad){
        List<Doctor> resultado = new ArrayList<>();
        for (Doctor d : listarTodos()){
            if (d.getEspecialidad().equalsIgnoreCase(Especialidad)) resultado.add(d);
        }
        return resultado;
    }

    private String aLinea(Doctor d){
        return d.getId() + "," + d.getNombre() + "," + d.getEspecialidad();
    }

    //Reescribe el archivo completo (se usa al eliminar)
    private void escribirTodos(List<Doctor> doctores){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))){
            for (Doctor d : doctores){
                bw.write(aLinea(d));
                bw.newLine();
            }
        } catch (IOException e){
            IO.println("Error al escribir el archivo de doctores.");
        }
    }
}
