public class Paciente extends Persona{

    //Contructor
    Paciente(String Id, String Nombre){
        super(Id, Nombre);
    }

    //Metodos
    @Override
    public String toString(){
        return "Paciente: " + super.toString();
    }
}
