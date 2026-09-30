import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Formato del archivo: Id,Nombre
public class PacienteRepositorio implements InterfaceRepositorio<Paciente> {

    //Atributos
    private String archivo;

    //Constructor
    PacienteRepositorio(String archivo){
        this.archivo = archivo;
    }

    //Metodos de la interfaz
    @Override
    public boolean guardar(Paciente entidad){
        if (existe(entidad.getId())) return false; //No se permiten Id repetidos
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))){
            bw.write(aLinea(entidad));
            bw.newLine();
            return true;
        } catch (IOException e){
            IO.println("Error al guardar el paciente.");
            return false;
        }
    }

    @Override
    public boolean eliminar(String id){
        List<Paciente> pacientes = listarTodos();
        boolean eliminado = pacientes.removeIf(p -> p.getId().equals(id));
        if (eliminado) escribirTodos(pacientes);
        return eliminado;
    }

    @Override
    public Paciente buscarPorId(String id){
        for (Paciente p : listarTodos()){
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    @Override
    public List<Paciente> listarTodos(){
        List<Paciente> pacientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))){
            String registro;
            //recorre el archivo
            while ((registro = br.readLine()) != null){
                String[] datos = registro.split(",");
                if (datos.length < 2) continue; //ignora lineas vacias o mal formadas
                pacientes.add(new Paciente(datos[0].trim(), datos[1].trim()));
            }
        } catch (FileNotFoundException e){
            //Si el archivo aun no existe, simplemente no hay pacientes
        } catch (IOException e){
            IO.println("Error al leer el archivo de pacientes.");
        }
        return pacientes;
    }

    @Override
    public boolean existe(String id){
        return buscarPorId(id) != null;
    }

    //Metodos propios
    List<Paciente> buscarPorNombre(String Nombre){
        List<Paciente> resultado = new ArrayList<>();
        for (Paciente p : listarTodos()){
            if (p.getNombre().toLowerCase().contains(Nombre.toLowerCase())) resultado.add(p);
        }
        return resultado;
    }

    private String aLinea(Paciente p){
        return p.getId() + "," + p.getNombre();
    }

    //Reescribe el archivo completo (se usa al eliminar)
    private void escribirTodos(List<Paciente> pacientes){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))){
            for (Paciente p : pacientes){
                bw.write(aLinea(p));
                bw.newLine();
            }
        } catch (IOException e){
            IO.println("Error al escribir el archivo de pacientes.");
        }
    }
}
