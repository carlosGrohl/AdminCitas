import java.io.*;

public class Administrador {

    //Atributos
    private String Usuario;
    private String Password;

    //Constructor
    Administrador(String Usuario, String Password){
        this.Usuario = Usuario;
        this.Password = Password;
    }

    //Metodos
    void setUsuario(String Usuario){
        this.Usuario = Usuario;
    }
    void setPassword(String Password){
        this.Password = Password;
    }

    String getUsuario(){ return Usuario; }
    String getPassword(){ return Password; }

    static boolean Validar(String Usuario, String Password){
        boolean Encontrado = false ;
        try{
            BufferedReader br = new BufferedReader(new FileReader("data/Administradores.csv"));
            String registro;
            //recorre el archivo
            while ((registro = br.readLine()) != null){
                String[] datos = registro.split(",");
                String Usu = datos[0];
                String Pass = datos[1];

                /**/
                if(Usuario.equals(Usu) && Password.equals(Pass)){
                    Encontrado = true;
                }
            }
            br.close();
        }catch (FileNotFoundException e){
            IO.println("No existe un archivo de Administradores.");
        } catch (IOException e) {
            IO.println("Error al leer el archivo.");
        }
        return Encontrado;
    }
}
