🏥 Gestor Hospitalario

Sistema de gestión de pacientes y habitaciones hospitalarias

Proyecto académico desarrollado en Java para la asignatura Algoritmos y Programación 3 del Politécnico Colombiano Jaime Isaza Cadavid. Su propósito es administrar pacientes y habitaciones de un hospital mediante un menú interactivo por consola, aplicando programación orientada a objetos y estructuras de datos implementadas manualmente.

👥 Autores
José Miguel Barrientos Rivera
Jorge Chávez

Institución: Politécnico Colombiano Jaime Isaza Cadavid
Asignatura: Algoritmos y Programación 3
Docente: Rodolfo Luna Guzmán
Fecha: 3 de noviembre de 2026

📋 1. Descripción del proyecto

El Gestor Hospitalario es una aplicación de consola desarrollada en Java que permite llevar el control de pacientes registrados, habitaciones disponibles y ocupadas, ingresos, salidas e historiales de movimientos.

El sistema está diseñado con fines académicos para practicar programación orientada a objetos, listas simplemente enlazadas y matrices dispersas mediante tripletas.

🎯 2. Objetivos
Objetivo general

Administrar pacientes y habitaciones de un hospital mediante un menú por consola, aplicando programación orientada a objetos y estructuras de datos implementadas manualmente.

Objetivos específicos
Registrar pacientes con identificación, nombre, edad, nivel de triage y motivo de ingreso.
Gestionar 30 habitaciones distribuidas en cinco pisos con seis habitaciones por piso.
Representar la ocupación mediante una matriz dispersa con tripletas.
Conservar el historial de ingresos y salidas con fecha y hora.
Validar identificaciones duplicadas, dobles ocupaciones y salidas incorrectas.
⚙️ 3. Características del sistema
Registro de pacientes con sus respectivos datos personales.
Validación de identificaciones duplicadas.
Asignación manual de pacientes a habitaciones disponibles.
Administración de 30 habitaciones distribuidas en cinco pisos.
Consulta de habitaciones disponibles y ocupadas.
Registro de salida de pacientes y liberación de habitaciones.
Consulta de todos los pacientes registrados, incluidos los que ya salieron.
Búsqueda de pacientes por identificación.
Historial de ingresos y salidas por habitación.
Consulta del historial completo de movimientos.
Visualización de la matriz dispersa y sus tripletas.
Validaciones para evitar dobles ocupaciones y salidas incorrectas.
🧱 4. Estructuras de datos

El proyecto utiliza cuatro estructuras de datos principales:

4.1. Matriz dispersa con tripletas

Representa la ocupación de las habitaciones mediante una matriz de 5 filas por 6 columnas.

Cada tripleta contiene:

Fila.
Columna.
Valor.

El valor 1 indica que la habitación está ocupada. Las posiciones que no tienen tripleta se consideran disponibles (valor 0).

4.2. Lista simplemente enlazada de pacientes

Almacena los pacientes registrados durante la ejecución del programa. Los pacientes permanecen registrados incluso después de haber salido del hospital.

4.3. Lista simplemente enlazada de historial

Almacena los movimientos realizados en las habitaciones, incluyendo:

Información del paciente.
Acción realizada (ingreso o salida).
Fecha y hora del movimiento.
4.4. Arreglo de habitaciones

Contiene los 30 objetos de tipo Habitacion, cada uno con su número, piso, posición y estado actual.

🖥️ 5. Menú del sistema

La aplicación cuenta con un menú interactivo por consola con las siguientes opciones:

text
========== GESTOR HOSPITALARIO ==========

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

📌 6. Requisitos funcionales

Los requisitos funcionales describen las operaciones que debe realizar el sistema.

Código	Requisito
RF-01	Registrar pacientes con identificación, nombre, edad, triage y motivo de ingreso.
RF-02	Impedir el registro de pacientes con identificaciones duplicadas.
RF-03	Conservar los pacientes registrados durante la ejecución.
RF-04	Gestionar 30 habitaciones distribuidas en cinco pisos.
RF-05	Permitir la asignación manual de habitaciones.
RF-06	Impedir asignar habitaciones ocupadas.
RF-07	Consultar habitaciones disponibles y ocupadas.
RF-08	Registrar salidas y liberar habitaciones.
RF-09	Rechazar salidas de pacientes sin hospitalización activa.
RF-10	Mostrar todos los pacientes registrados.
RF-11	Buscar pacientes por identificación.
RF-12	Registrar ingresos y salidas con fecha y hora.
RF-13	Consultar historiales por habitación y completos.
RF-14	Representar la ocupación mediante una matriz dispersa de 5 × 6.
RF-15	Considerar disponibles las posiciones sin tripleta.
RF-16	Mostrar la matriz y sus tripletas desde el menú.
🛠️ 7. Requisitos no funcionales
RNF-01. Plataforma: debe ejecutarse utilizando un JDK de Java y Apache NetBeans.
RNF-02. Dependencias: no requiere librerías externas ni conexión a una base de datos.
RNF-03. Interfaz: funciona mediante un menú numerado por consola.
RNF-04. Implementación: utiliza listas enlazadas implementadas manualmente.
RNF-05. Persistencia: los datos permanecen en memoria y se pierden al cerrar el programa.
RNF-06. Mantenibilidad: el código está organizado en clases separadas dentro del paquete hospital.
🏗️ 8. Arquitectura del sistema

El sistema está organizado mediante programación orientada a objetos. La clase Main presenta el menú y delega las operaciones a GestorHospital, que coordina los demás componentes.

Clase	Responsabilidad
Main	Menú por consola y punto de entrada.
GestorHospital	Lógica principal de registro, asignación, salida y consultas.
Paciente	Almacena los datos de los pacientes.
Habitacion	Representa el número, piso, posición y estado de cada habitación.
Tripleta	Representa una terna de fila, columna y valor.
MatrizDispersa	Gestiona la ocupación mediante tripletas.
NodoPaciente	Nodo de la lista de pacientes.
ListaPacientes	Administra la lista simplemente enlazada de pacientes.
NodoHistorial	Nodo de la lista de historial.
RegistroHistorial	Almacena los datos de cada movimiento.
ListaHistorial	Administra los movimientos registrados.
📁 9. Organización del proyecto

La estructura de archivos propuesta para el proyecto es la siguiente:

text
GestorHospitalario/
│
├── src/
│   └── hospital/
│       ├── Main.java
│       ├── Paciente.java
│       ├── Habitacion.java
│       ├── Tripleta.java
│       ├── MatrizDispersa.java
│       ├── NodoPaciente.java
│       ├── ListaPacientes.java
│       ├── NodoHistorial.java
│       ├── RegistroHistorial.java
│       ├── ListaHistorial.java
│       └── GestorHospital.java
│
└── README.md

▶️ 10. Requisitos para ejecutar el proyecto

Se necesita:

JDK de Java instalado.
Apache NetBeans.
No se requieren librerías externas.
No se necesita conexión a una base de datos.
Ejecución en NetBeans
Descargar o clonar el repositorio.
Crear un proyecto Java Application llamado GestorHospitalario.
Crear el paquete hospital dentro de Source Packages.
Copiar los archivos .java dentro del paquete.
Abrir Main.java.
Ejecutar el archivo o configurar hospital.Main como clase principal.
🧪 11. Criterios de aceptación

Para verificar el funcionamiento básico del sistema se propone la siguiente prueba:

Paso	Acción	Resultado esperado
1	Registrar un paciente con identificación 1001.	Paciente registrado correctamente.
2	Asignarle la habitación 8.	Habitación 8 ocupada.
3	Consultar habitaciones ocupadas.	Aparece la habitación 8.
4	Consultar la matriz dispersa.	La habitación 8 aparece con valor 1.
5	Registrar la salida del paciente 1001.	Habitación liberada.
6	Consultar el historial de la habitación 8.	Aparecen el ingreso y la salida.
7	Intentar dar salida nuevamente al paciente 1001.	El sistema rechaza la operación por no existir una hospitalización activa.

⚠️ 12. Restricciones y consideraciones
Persistencia en memoria: los registros existen únicamente mientras el programa está en ejecución. Al cerrarlo, los datos se pierden.
Triage académico: los niveles de triage se almacenan como datos de ejemplo. La asignación de habitaciones es manual y no depende del nivel de triage.
Proyecto educativo: está diseñado para practicar estructuras de datos y programación orientada a objetos. No debe utilizarse como sistema clínico real.
Alcance limitado: no incluye interfaz gráfica, autenticación de usuarios, base de datos ni asignación automática de habitaciones.

📚 13. Tecnologías utilizadas
Java.
Programación orientada a objetos.
Listas simplemente enlazadas implementadas manualmente.
Matriz dispersa mediante tripletas.
Apache NetBeans.
JDK de Java.


📦 14. Entregables del desarrollo

El desarrollo del proyecto contempla los siguientes entregables:

Especificación de Requisitos de Software (SRS).
Tablero de seguimiento mediante Scrum y Kanban.
Desarrollo organizado por sprints.
Producto mínimo viable (MVP).
Código fuente disponible en el repositorio.
Mantenimiento, soporte y documentación posterior.


📖 15. Referencias
IEEE. (1998). IEEE recommended practice for software requirements specifications (IEEE Std 830-1998). Institute of Electrical and Electronics Engineers.
ISO/IEC/IEEE. (2018). Systems and software engineering — Life cycle processes — Requirements engineering (ISO/IEC/IEEE 29148:2018). International Organization for Standardization.
Schwaber, K., y Sutherland, J. (2020). La guía de Scrum: La guía definitiva de Scrum, las reglas del juego. Scrum.org.

Gestor Hospitalario — Proyecto académico de Algoritmos y Programación 3.
