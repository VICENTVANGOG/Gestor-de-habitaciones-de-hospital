<<<<<<< HEAD
## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
=======

🏥 Gestor Hospitalario
Proyecto académico desarrollado en Java para administrar pacientes y habitaciones de un hospital mediante un menú por consola. Está diseñado para ejecutarse en NetBeans y practicar programación orientada a objetos y estructuras de datos.

📋 Características
Registro de pacientes con identificación, nombre, edad, nivel de triage y motivo de ingreso.
Asignación manual de pacientes a habitaciones.
Gestión de 30 habitaciones, distribuidas en 5 pisos con 6 habitaciones por piso.
Consulta de habitaciones disponibles y ocupadas.
Registro de salida y liberación de habitaciones.
Consulta de todos los pacientes registrados.
Búsqueda de pacientes por identificación.
Historial de ingresos y salidas por habitación.
Consulta de la matriz de ocupación y sus tripletas.
Validaciones para evitar identificaciones duplicadas, dobles ocupaciones y salidas incorrectas.
🧱 Estructuras de datos
1. Matriz dispersa con tripletas
Representa la ocupación de las habitaciones en una matriz de 5 filas por 6 columnas. Cada tripleta contiene fila, columna y valor. El valor 1 indica que la habitación está ocupada; las posiciones que no tienen tripleta se consideran disponibles (0).

2. Lista simplemente enlazada de pacientes
Conserva los pacientes registrados durante la ejecución, incluso después de que hayan salido del hospital.

3. Lista simplemente enlazada de historial
Guarda los movimientos de las habitaciones, incluyendo el paciente, la acción realizada y la fecha y hora.

4. Arreglo de habitaciones
Contiene los 30 objetos Habitacion, con su número, piso, posición y estado actual.

🗂️ Organización del proyecto
GestorHospitalario/
└── src/
    └── hospital/
        ├── Main.java
        ├── Paciente.java
        ├── Habitacion.java
        ├── Tripleta.java
        ├── MatrizDispersa.java
        ├── NodoPaciente.java
        ├── ListaPacientes.java
        ├── NodoHistorial.java
        ├── RegistroHistorial.java
        ├── ListaHistorial.java
        └── GestorHospital.java
🛠️ Requisitos
JDK de Java instalado.
Apache NetBeans.
No se requieren librerías externas ni conexión a una base de datos.
▶️ Cómo ejecutar en NetBeans
Descarga o clona el proyecto.
Abre NetBeans y crea un proyecto Java Application llamado GestorHospitalario.
Dentro de Source Packages, crea el paquete hospital.
Copia los archivos .java dentro de ese paquete.
Abre Main.java.
Ejecuta el archivo o configura hospital.Main como clase principal del proyecto.
🖥️ Menú del sistema
1. Registrar paciente
2. Asignar habitación
3. Ver habitaciones disponibles
4. Ver habitaciones ocupadas
5. Dar salida a un paciente
6. Ver todos los pacientes
7. Ver historial de una habitación
8. Ver matriz dispersa (tripletas)
9. Buscar paciente por identificación
10. Ver historial completo
0. Salir
🧪 Prueba rápida
Registra un paciente con identificación 1001.
Asigna al paciente la habitación 8.
Consulta las habitaciones ocupadas.
Consulta la matriz dispersa y verifica que la habitación esté representada como ocupada.
Registra la salida del paciente 1001.
Consulta el historial de la habitación 8; deben aparecer el ingreso y la salida.
Intenta dar salida al mismo paciente otra vez; el sistema debe rechazar la operación porque no tiene una hospitalización activa.
⚠️ Consideraciones
Persistencia en memoria: los registros se mantienen solo mientras el programa está en ejecución. Al cerrar el programa, los datos se pierden.
Triage académico: los niveles de triage se guardan como datos de ejemplo. La asignación es manual y este proyecto no sustituye protocolos ni decisiones del personal sanitario.
Proyecto educativo: está pensado para practicar estructuras de datos; no debe utilizarse como sistema clínico real.
📚 Tecnologías
Java
Programación orientada a objetos
Listas simplemente enlazadas implementadas manualmente
Matriz dispersa mediante tripletas
NetBeans
👨‍💻 Autor
Proyecto académico de práctica de estructuras de datos y programación en Java.

>>>>>>> e72203f4bcaf9882b4bdec5d102a7cc2802b380b
