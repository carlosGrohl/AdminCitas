import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class SistemaCitas {

    //Atributos
    private DoctorRepositorio doctorRepo;
    private PacienteRepositorio pacienteRepo;
    private CitaRepositorio citaRepo;
    private Administrador adminActual;

    //Constructor
    SistemaCitas(){
        doctorRepo = new DoctorRepositorio("data/Doctores.csv");
        pacienteRepo = new PacienteRepositorio("data/Pacientes.csv");
        citaRepo = new CitaRepositorio("data/Citas.csv", doctorRepo, pacienteRepo);
    }

    //Menus (se llaman desde Main)
    int mostrarMenuPrincipal(){
        IO.println("\n*** MENU PRINCIPAL ***");
        IO.println("1. Iniciar sesion");
        IO.println("2. Salir");
        return leerOpcion("Opcion: ");
    }

    int mostrarMenuAdministrador(){
        IO.println("\n*** MENU (" + adminActual.getUsuario() + ") ***");
        IO.println("1. Gestionar Doctores");
        IO.println("2. Gestionar Pacientes");
        IO.println("3. Gestionar Citas");
        IO.println("4. Cerrar Sesion");
        return leerOpcion("Opcion: ");
    }

    //Pantalla de Login
    boolean iniciarSesion(){
        IO.println("\n--- Login ---");
        String Usuario = IO.readln("Usuario: ").trim();
        String Password = IO.readln("Contrasena: ").trim();
        if (Administrador.Validar(Usuario, Password)){
            adminActual = new Administrador(Usuario, Password);
            IO.println("Bienvenido, " + Usuario + ".");
            return true;
        }
        IO.println("Usuario o contrasena incorrectos.");
        return false;
    }

    //Regresa true si el administrador confirma que quiere cerrar sesion
    boolean cerrarSesion(){
        String respuesta = IO.readln("Cerrar sesion? (S/N): ").trim();
        if (respuesta.equalsIgnoreCase("S")){
            IO.println("Sesion cerrada.");
            adminActual = null;
            return true;
        }
        return false;
    }

    //Submenus
    void gestionarDoctores(){
        int opcion;
        do {
            opcion = mostrarSubmenu("Doctores");
            switch (opcion){
                case 1 -> altaDoctor();
                case 2 -> buscarDoctor();
                case 3 -> MostrarDoctores();
                case 4 -> eliminarDoctor();
                case 5 -> {}
                default -> IO.println("Opcion no valida.");
            }
        } while (opcion != 5);
    }

    void gestionarPacientes(){
        int opcion;
        do {
            opcion = mostrarSubmenu("Pacientes");
            switch (opcion){
                case 1 -> altaPaciente();
                case 2 -> buscarPaciente();
                case 3 -> MostrarPacientes();
                case 4 -> eliminarPaciente();
                case 5 -> {}
                default -> IO.println("Opcion no valida.");
            }
        } while (opcion != 5);
    }

    void gestionarCitas(){
        int opcion;
        do {
            opcion = mostrarSubmenu("Citas");
            switch (opcion){
                case 1 -> altaCita();
                case 2 -> buscarCita();
                case 3 -> MostrarCitas();
                case 4 -> eliminarCita();
                case 5 -> {}
                default -> IO.println("Opcion no valida.");
            }
        } while (opcion != 5);
    }

    //Doctores
    private void altaDoctor(){
        String Id = IO.readln("Id del doctor: ").trim();
        if (doctorRepo.existe(Id)){
            IO.println("Ya existe un doctor con ese Id.");
            return;
        }
        String Nombre = IO.readln("Nombre: ").trim();
        String Especialidad = IO.readln("Especialidad: ").trim();

        if (doctorRepo.guardar(new Doctor(Id, Nombre, Especialidad))){
            IO.println("Doctor registrado.");
        }
    }

    private void buscarDoctor(){
        Doctor doctor = doctorRepo.buscarPorId(IO.readln("Id del doctor: ").trim());
        if (doctor == null){
            IO.println("No existe ese doctor.");
            return;
        }
        IO.println(doctor);
    }

    private void MostrarDoctores(){
        List<Doctor> doctores = doctorRepo.listarTodos();
        if (doctores.isEmpty()){
            IO.println("No hay doctores registrados.");
            return;
        }
        for (Doctor d : doctores) IO.println(d);
    }

    private void eliminarDoctor(){
        String Id = IO.readln("Id del doctor: ").trim();
        if (!citaRepo.buscarPorDoctor(Id).isEmpty()){
            IO.println("No se puede eliminar: el doctor tiene citas registradas.");
            return;
        }
        if (doctorRepo.eliminar(Id)){
            IO.println("Doctor eliminado.");
        } else {
            IO.println("No existe ese doctor.");
        }
    }

    //Pacientes
    private void altaPaciente(){
        String Id = IO.readln("Id del paciente: ").trim();
        if (pacienteRepo.existe(Id)){
            IO.println("Ya existe un paciente con ese Id.");
            return;
        }
        String Nombre = IO.readln("Nombre: ").trim();

        if (pacienteRepo.guardar(new Paciente(Id, Nombre))){
            IO.println("Paciente registrado.");
        }
    }

    private void buscarPaciente(){
        Paciente paciente = pacienteRepo.buscarPorId(IO.readln("Id del paciente: ").trim());
        if (paciente == null){
            IO.println("No existe ese paciente.");
            return;
        }
        IO.println(paciente);
    }

    private void MostrarPacientes(){
        List<Paciente> pacientes = pacienteRepo.listarTodos();
        if (pacientes.isEmpty()){
            IO.println("No hay pacientes registrados.");
            return;
        }
        for (Paciente p : pacientes) IO.println(p);
    }

    private void eliminarPaciente(){
        String Id = IO.readln("Id del paciente: ").trim();
        if (!citaRepo.buscarPorPaciente(Id).isEmpty()){
            IO.println("No se puede eliminar: el paciente tiene citas registradas.");
            return;
        }
        if (pacienteRepo.eliminar(Id)){
            IO.println("Paciente eliminado.");
        } else {
            IO.println("No existe ese paciente.");
        }
    }

    //Citas
    private void altaCita(){
        String Id = IO.readln("Id de la cita: ").trim();
        if (citaRepo.existe(Id)){
            IO.println("Ya existe una cita con ese Id.");
            return;
        }

        Doctor doctor = doctorRepo.buscarPorId(IO.readln("Id del doctor: ").trim());
        if (doctor == null){
            IO.println("No existe ese doctor.");
            return;
        }

        Paciente paciente = pacienteRepo.buscarPorId(IO.readln("Id del paciente: ").trim());
        if (paciente == null){
            IO.println("No existe ese paciente.");
            return;
        }

        LocalDate Fecha;
        LocalTime Hora;
        try {
            Fecha = LocalDate.parse(IO.readln("Fecha (AAAA-MM-DD): ").trim());
            Hora = LocalTime.parse(IO.readln("Hora (HH:MM): ").trim());
        } catch (DateTimeParseException e){
            IO.println("Formato de fecha u hora no valido.");
            return;
        }

        //Evita que el doctor tenga dos citas a la misma fecha y hora
        for (Cita c : citaRepo.buscarPorDoctor(doctor.getId())){
            if (c.getFecha().equals(Fecha) && c.getHora().equals(Hora)){
                IO.println("El doctor ya tiene una cita en esa fecha y hora.");
                return;
            }
        }

        String Motivo = IO.readln("Motivo: ").trim();

        if (citaRepo.guardar(new Cita(Id, doctor, paciente, Fecha, Hora, Motivo))){
            IO.println("Cita registrada.");
        }
    }

    private void buscarCita(){
        Cita cita = citaRepo.buscarPorId(IO.readln("Id de la cita: ").trim());
        if (cita == null){
            IO.println("No existe esa cita.");
            return;
        }
        IO.println(cita);
        IO.println("Motivo: " + cita.getMotivo());
    }

    private void MostrarCitas(){
        List<Cita> citas = citaRepo.listarTodos();
        if (citas.isEmpty()){
            IO.println("No hay citas registradas.");
            return;
        }
        for (Cita c : citas) IO.println(c);
    }

    private void eliminarCita(){
        if (citaRepo.eliminar(IO.readln("Id de la cita: ").trim())){
            IO.println("Cita eliminada.");
        } else {
            IO.println("No existe esa cita.");
        }
    }

    //Metodos de apoyo
    private int mostrarSubmenu(String titulo){
        IO.println("\n--- " + titulo + " ---");
        IO.println("1. Alta");
        IO.println("2. Buscar");
        IO.println("3. Mostrar");
        IO.println("4. Eliminar");
        IO.println("5. Regresar");
        return leerOpcion("Opcion: ");
    }

    //Lee un numero del teclado; si no es numero regresa -1
    private int leerOpcion(String mensaje){
        try {
            return Integer.parseInt(IO.readln(mensaje).trim());
        } catch (NumberFormatException e){
            return -1;
        }
    }
}
