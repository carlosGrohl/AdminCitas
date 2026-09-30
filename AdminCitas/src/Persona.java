public class Persona {
    //Atributos
    private String Id;
    private String Nombre;

    //Constructor
    Persona(String Id, String Nombre){
        this.Id = Id;
        this.Nombre = Nombre;
    }

    //Metodos
    String getId(){ return Id; }
    String getNombre(){ return Nombre; }

    void setId(String Id){ this.Id = Id; }
    void setNombre(String Nombre){ this.Nombre = Nombre; }

    //Sobrecargo el metodo toString
    @Override
    public String toString(){
        return "ID: " + Id + " - Nombre: "+ Nombre + ".";
    }


}
