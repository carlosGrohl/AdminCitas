 public class Doctor extends Persona{

    //Atributos
     private String Especialidad;

     //Constructor
     Doctor(String Id, String Nombre, String Especialidad){
        super(Id, Nombre); //Llamo al constructor de la clase padre (Persona)
        this.Especialidad = Especialidad;
     }

     //Metodos
     String getEspecialidad(){ return Especialidad; }

     void SetEspecialidad(String Especialidad){
        this.Especialidad = Especialidad;
     }

     @Override
     public String toString(){
         return super.toString()+"["+ Especialidad +"] ";
     }

}
