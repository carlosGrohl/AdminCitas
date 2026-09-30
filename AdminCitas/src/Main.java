
void main() {

    SistemaCitas sistema = new SistemaCitas();
    int opcion;

    //Menu Principal
    do {
        opcion = sistema.mostrarMenuPrincipal();

        if (opcion == 1){
            //Pantalla de Login: maximo 3 intentos
            int intentos = 0;
            boolean sesionIniciada = false;

            while (!sesionIniciada && intentos < 3){
                sesionIniciada = sistema.iniciarSesion();
                intentos++;
            }

            if (sesionIniciada){
                //Menu del administrador
                boolean sesionCerrada = false;
                do {
                    switch (sistema.mostrarMenuAdministrador()){
                        case 1 -> sistema.gestionarDoctores();
                        case 2 -> sistema.gestionarPacientes();
                        case 3 -> sistema.gestionarCitas();
                        case 4 -> sesionCerrada = sistema.cerrarSesion();
                        default -> IO.println("Opcion no valida.");
                    }
                } while (!sesionCerrada);
            } else {
                IO.println("Demasiados intentos fallidos. Regresando al menu principal.");
            }

        } else if (opcion != 2){
            IO.println("Opcion no valida.");
        }
    } while (opcion != 2);

    //Salir
    IO.println("Hasta luego.");

}
