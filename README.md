# 🏥 Gestor Hospitalario

Sistema de consola en Java para administrar pacientes, habitaciones, ingresos, salidas e historial hospitalario. Proyecto académico de Algoritmos y Programación 3.

## Autores
- José Miguel Barrientos Rivera
- Jorge Chávez

**Institución:** Politécnico Colombiano Jaime Isaza Cadavid  
**Asignatura:** Algoritmos y Programación 3  
**Docente:** Rodolfo Luna Guzmán

## Objetivo
Gestionar 30 habitaciones (5 pisos × 6 habitaciones), pacientes y movimientos hospitalarios aplicando POO y estructuras de datos implementadas manualmente.

## Funcionalidades
1. Registrar pacientes y validar identificaciones duplicadas.
2. Asignar manualmente habitaciones disponibles.
3. Consultar habitaciones disponibles y ocupadas.
4. Registrar salidas y liberar habitaciones.
5. Consultar pacientes y buscar por identificación.
6. Consultar historial por habitación y completo, con fecha y hora.
7. Visualizar matriz dispersa y tripletas de ocupación.

## Estructuras de datos
- **Lista simplemente enlazada de pacientes:** conserva los pacientes durante la ejecución.
- **Lista simplemente enlazada de historial:** registra ingresos y salidas.
- **Matriz dispersa 5 × 6:** cada tripleta representa una habitación ocupada; la ausencia de tripleta significa disponible.
- **Arreglo de habitaciones:** contiene los 30 objetos Habitacion.

## Arquitectura
```text
src/
└── hospital/
    ├── Main.java
    ├── modelos/
    │   ├── Paciente.java
    │   ├── Habitacion.java
    │   └── RegistroHistorial.java
    ├── estructuras/
    │   ├── Tripleta.java
    │   ├── MatrizDispersa.java
    │   ├── NodoPaciente.java
    │   ├── ListaPacientes.java
    │   ├── NodoHistorial.java
    │   └── ListaHistorial.java
    └── gestion/
        └── GestorHospital.java
```

## Requisitos
- JDK 17 o superior (se utilizan expresiones switch modernas).
- Visual Studio Code.
- Extensiones **Extension Pack for Java**.
- No requiere librerías externas ni base de datos.

## Ejecutar en Visual Studio Code
1. Clonar o descargar este repositorio.
2. Abrir la carpeta del proyecto en VS Code.
3. Instalar Extension Pack for Java si aún no está instalado.
4. Abrir `src/hospital/Main.java`.
5. Ejecutar con **Run Java** sobre el método `main`.

También se puede compilar desde la raíz del repositorio:

```bash
javac -d bin src/hospital/modelos/*.java src/hospital/estructuras/*.java src/hospital/gestion/*.java src/hospital/Main.java
java -cp bin hospital.Main
```

## Menú
- 1. Registrar paciente
- 2. Asignar habitación
- 3. Ver habitaciones disponibles
- 4. Ver habitaciones ocupadas
- 5. Dar salida a un paciente
- 6. Ver todos los pacientes
- 7. Ver historial de una habitación
- 8. Ver matriz dispersa (tripletas)
- 9. Buscar paciente por identificación
- 10. Ver historial completo
- 0. Salir

## Validaciones
- No permite identificaciones duplicadas.
- No permite asignar habitaciones ocupadas.
- No permite asignar más de una habitación al mismo paciente simultáneamente.
- No permite dar salida si el paciente no tiene hospitalización activa.
- Valida el rango de habitaciones (1 a 30), edad y triage (1 a 5).

## Alcance y limitaciones
Los datos se almacenan en memoria y se pierden al cerrar el programa. La asignación es manual y el triage se registra como dato académico; no determina automáticamente la habitación. Este proyecto es educativo y no debe utilizarse para decisiones clínicas reales.

## Prueba de aceptación sugerida
1. Registrar paciente con identificación `1001`.
2. Asignarle habitación `8`.
3. Consultar habitaciones ocupadas y matriz: debe aparecer ocupada la posición correspondiente.
4. Dar salida a `1001`.
5. Consultar el historial de la habitación 8: deben aparecer ingreso y salida.
6. Intentar dar salida nuevamente: el sistema debe rechazar la operación.

## Referencias
- IEEE. (1998). *IEEE Recommended Practice for Software Requirements Specifications* (IEEE Std 830-1998).
- ISO/IEC/IEEE. (2018). *Systems and software engineering — Life cycle processes — Requirements engineering* (ISO/IEC/IEEE 29148:2018).
- Schwaber, K., & Sutherland, J. (2020). *La guía de Scrum*.

---
Proyecto académico — Gestor Hospitalario.
