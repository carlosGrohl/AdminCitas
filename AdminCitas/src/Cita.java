import java.time.LocalDate;
import java.time.LocalTime;

public class Cita {

    //Atributos
    private String Id;
    private Doctor Doctor;
    private Paciente Paciente;
    private LocalDate Fecha;
    private LocalTime Hora;
    private String Motivo;

    //Constructor
    Cita(String Id, Doctor Doctor, Paciente Paciente, LocalDate Fecha, LocalTime Hora, String Motivo){
        this.Id = Id;
        this.Doctor = Doctor;
        this.Paciente = Paciente;
        this.Fecha = Fecha;
        this.Hora = Hora;
        this.Motivo = Motivo;
    }

    //Metodos
    String getId(){ return Id; }
    Doctor getDoctor(){ return Doctor; }
    Paciente getPaciente(){ return Paciente; }
    LocalDate getFecha(){ return Fecha; }
    LocalTime getHora(){ return Hora; }
    String getMotivo(){ return Motivo; }

    void setId(String Id){ this.Id = Id; }
    void setDoctor(Doctor Doctor){ this.Doctor = Doctor; }
    void setPaciente(Paciente Paciente){ this.Paciente = Paciente; }
    void setFecha(LocalDate Fecha){ this.Fecha = Fecha; }
    void setHora(LocalTime Hora){ this.Hora = Hora; }
    void setMotivo(String Motivo){ this.Motivo = Motivo; }

    //Sobrecargo el metodo toString
    @Override
    public String toString(){
        return  Id + " | " + Fecha + " " + Hora + " | " +
                this.Doctor.getNombre() + " | " +
                this.Paciente.getNombre();
    }
}
