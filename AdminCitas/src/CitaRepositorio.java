import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//Formato del archivo: Id,IdDoctor,IdPaciente,Fecha,Hora,Motivo
//En el archivo solo se guardan los Id del doctor y paciente; al leer se buscan en sus repositorios.
public class CitaRepositorio implements InterfaceRepositorio<Cita> {

    //Atributos
    private String archivo;
    private DoctorRepositorio doctorRepo;
    private PacienteRepositorio pacienteRepo;

    //Constructor
    CitaRepositorio(String archivo, DoctorRepositorio doctorRepo, PacienteRepositorio pacienteRepo){
        this.archivo = archivo;
        this.doctorRepo = doctorRepo;
        this.pacienteRepo = pacienteRepo;
    }

    //Metodos de la interfaz
    @Override
    public boolean guardar(Cita entidad){
        if (existe(entidad.getId())) return false; //No se permiten Id repetidos
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))){
            bw.write(aLinea(entidad));
            bw.newLine();
            return true;
        } catch (IOException e){
            IO.println("Error al guardar la cita.");
            return false;
        }
    }

    @Override
    public boolean eliminar(String id){
        List<Cita> citas = listarTodos();
        boolean eliminado = citas.removeIf(c -> c.getId().equals(id));
        if (eliminado) escribirTodos(citas);
        return eliminado;
    }

    @Override
    public Cita buscarPorId(String id){
        for (Cita c : listarTodos()){
            if (c.getId().equals(id)) return c;
        }
        return null;
    }

    @Override
    public List<Cita> listarTodos(){
        List<Cita> citas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))){
            String registro;
            //recorre el archivo
            while ((registro = br.readLine()) != null){
                //El limite 6 permite que el Motivo contenga comas
                String[] datos = registro.split(",", 6);
                if (datos.length < 6) continue; //ignora lineas vacias o mal formadas

                Doctor doctor = doctorRepo.buscarPorId(datos[1].trim());
                Paciente paciente = pacienteRepo.buscarPorId(datos[2].trim());
                if (doctor == null || paciente == null) continue; //el doctor o paciente ya no existe

                citas.add(new Cita(datos[0].trim(), doctor, paciente,
                        LocalDate.parse(datos[3].trim()),
                        LocalTime.parse(datos[4].trim()),
                        datos[5].trim()));
            }
        } catch (FileNotFoundException e){
            //Si el archivo aun no existe, simplemente no hay citas
        } catch (IOException e){
            IO.println("Error al leer el archivo de citas.");
        }
        return citas;
    }

    @Override
    public boolean existe(String id){
        return buscarPorId(id) != null;
    }

    //Metodos propios
    List<Cita> buscarPorDoctor(String idDoctor){
        List<Cita> resultado = new ArrayList<>();
        for (Cita c : listarTodos()){
            if (c.getDoctor().getId().equals(idDoctor)) resultado.add(c);
        }
        return resultado;
    }

    List<Cita> buscarPorPaciente(String idPaciente){
        List<Cita> resultado = new ArrayList<>();
        for (Cita c : listarTodos()){
            if (c.getPaciente().getId().equals(idPaciente)) resultado.add(c);
        }
        return resultado;
    }

    private String aLinea(Cita c){
        return c.getId() + "," + c.getDoctor().getId() + "," + c.getPaciente().getId() + "," +
               c.getFecha() + "," + c.getHora() + "," + c.getMotivo();
    }

    //Reescribe el archivo completo (se usa al eliminar)
    private void escribirTodos(List<Cita> citas){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))){
            for (Cita c : citas){
                bw.write(aLinea(c));
                bw.newLine();
            }
        } catch (IOException e){
            IO.println("Error al escribir el archivo de citas.");
        }
    }
}
