# AdminCitas
Sistema de administración de citas para un consultorio clínico desarrollado en Java.

## Instalación y configuración

### Requisitos previos

- Java Development Kit (JDK) 11 o superior
- IntelliJ IDEA (O cualqiuer IDE que permita trabajar con JAVA)
- Git instalado y configurado

1. Clonar el repositorio:
```bash
git clone <URL_DEL_REPOSITORIO>
cd AdminCitas
```
3. Abrir el proyecto en IntelliJ IDEA( o IDE de preferencia):
   - File → Open → Seleccionar la carpeta del proyecto
   
4. Compilar el proyecto.

### Ejecución

```bash
java -jar AdminCitas.jar
```

### Funcionalidades principales

1. **Control de acceso**: Login de administradores con usuario y contraseña
2. **Gestión de doctores**: Alta de doctores con identificador, nombre y especialidad
3. **Gestión de pacientes**: Alta de pacientes con identificador y nombre
4. **Gestión de citas**: Creación de citas con fecha, hora y motivo
5. **Relación cita-doctor-paciente**: Vinculación de citas con sus respectivos doctores y pacientes

### Almacenamiento de datos

Los datos se almacenan en archivos CSV en la carpeta `data/`:
- `doctores.csv` - Registro de doctores
- `pacientes.csv` - Registro de pacientes
- `citas.csv` - Registro de citas
- `administradores.csv` - Credenciales de acceso

## Créditos
Carlos Ernesto Avila Gomez
Desarrollado como proyecto del curso de Computación en Java.

## Licencia
Educativo.
