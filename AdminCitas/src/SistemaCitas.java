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

    //Punto de entrada del sistema (se llama desde Main)
    void iniciar(){
        int opcion;
        do {
            opcion = mostrarMenuPrincipal();
            switch (opcion){
                case 1 -> {
                    if (iniciarSesion()){
                        int opcionAdmin;
                        do {
                            opcionAdmin = mostrarMenuAdministrador();
                            switch (opcionAdmin){
                                case 1 -> gestionarDoctores();
                                case 2 -> gestionarPacientes();
                                case 3 -> gestionarCitas();
                                case 0 -> {
                                    IO.println("Sesion cerrada.");
                                    adminActual = null;
                                }
                                default -> IO.println("Opcion no valida.");
                            }
                        } while (opcionAdmin != 0);
                    }
                }
                case 0 -> IO.println("Hasta luego.");
                default -> IO.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    //Menus
    private int mostrarMenuPrincipal(){
        IO.println("\n=== CLINICA - MENU PRINCIPAL ===");
        IO.println("1. Iniciar sesion");
        IO.println("0. Salir");
        return leerOpcion("Opcion: ");
    }

    private int mostrarMenuAdministrador(){
        IO.println("\n=== MENU ADMINISTRADOR (" + adminActual.getUsuario() + ") ===");
        IO.println("1. Gestionar doctores");
        IO.println("2. Gestionar pacientes");
        IO.println("3. Gestionar citas");
        IO.println("0. Cerrar sesion");
        return leerOpcion("Opcion: ");
    }

    private boolean iniciarSesion(){
        String Usuario = IO.readln("Usuario: ");
        String Password = IO.readln("Password: ");
        if (Administrador.Validar(Usuario, Password)){
            adminActual = new Administrador(Usuario, Password);
            IO.println("Bienvenido, " + Usuario + ".");
            return true;
        }
        IO.println("Usuario o password incorrectos.");
        return false;
    }

    private void gestionarDoctores(){
        int opcion;
        do {
            IO.println("\n--- Doctores ---");
            IO.println("1. Alta de doctor");
            IO.println("2. Mostrar doctores");
            IO.println("0. Regresar");
            opcion = leerOpcion("Opcion: ");
            switch (opcion){
                case 1 -> altaDoctor();
                case 2 -> MostrarDoctores();
                case 0 -> {}
                default -> IO.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private void gestionarPacientes(){
        int opcion;
        do {
            IO.println("\n--- Pacientes ---");
            IO.println("1. Alta de paciente");
            IO.println("2. Mostrar pacientes");
            IO.println("0. Regresar");
            opcion = leerOpcion("Opcion: ");
            switch (opcion){
                case 1 -> altaPaciente();
                case 2 -> MostrarPacientes();
                case 0 -> {}
                default -> IO.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private void gestionarCitas(){
        int opcion;
        do {
            IO.println("\n--- Citas ---");
            IO.println("1. Alta de cita");
            IO.println("2. Mostrar citas");
            IO.println("0. Regresar");
            opcion = leerOpcion("Opcion: ");
            switch (opcion){
                case 1 -> altaCita();
                case 2 -> MostrarCitas();
                case 0 -> {}
                default -> IO.println("Opcion no valida.");
            }
        } while (opcion != 0);
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

    private void MostrarDoctores(){
        List<Doctor> doctores = doctorRepo.listarTodos();
        if (doctores.isEmpty()){
            IO.println("No hay doctores registrados.");
            return;
        }
        for (Doctor d : doctores) IO.println(d);
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

    private void MostrarPacientes(){
        List<Paciente> pacientes = pacienteRepo.listarTodos();
        if (pacientes.isEmpty()){
            IO.println("No hay pacientes registrados.");
            return;
        }
        for (Paciente p : pacientes) IO.println(p);
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

    private void MostrarCitas(){
        List<Cita> citas = citaRepo.listarTodos();
        if (citas.isEmpty()){
            IO.println("No hay citas registradas.");
            return;
        }
        for (Cita c : citas) IO.println(c);
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
