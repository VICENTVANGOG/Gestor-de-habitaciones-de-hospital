# Gestor Hospitalario

Aplicación de escritorio en Java Swing para administrar pacientes, habitaciones, ingresos, salidas e historial hospitalario. Proyecto académico de Algoritmos y Programación 3.

## Autores
- José Miguel Barrientos Rivera
- Jorge Chávez

**Institución:** Politécnico Colombiano Jaime Isaza Cadavid  
**Asignatura:** Algoritmos y Programación 3  
**Docente:** Rodolfo Luna Guzmán

## Arquitectura

El proyecto separa responsabilidades mediante **Modelo–Vista–Controlador (MVC)** y organiza los componentes visuales con **Atomic Design**.

- **Modelo:** entidades, reglas de negocio y estructuras de datos.
- **Vista:** interfaz de escritorio construida con Java Swing.
- **Controlador:** recibe acciones de la interfaz y coordina el modelo.
- **Atomic Design:** átomos, moléculas, organismos, plantillas y páginas reutilizables.

## Estructura del proyecto

```text
src/hospital/
├── Main.java
├── controlador/
│   └── HospitalController.java
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
├── gestion/
│   └── GestorHospital.java
└── vista/
    ├── atomos/
    ├── moleculas/
    ├── organismos/
    ├── plantillas/
    └── paginas/
```

## Funcionalidades
- Registro y consulta de pacientes con validación de identificaciones duplicadas.
- Asignación manual de habitaciones disponibles.
- Consulta de las 30 habitaciones (5 pisos × 6 habitaciones).
- Registro de salidas y liberación de habitaciones.
- Historial de ingresos y salidas con fecha y hora.
- Dashboard con indicadores de ocupación.
- Visualización tabular de pacientes, habitaciones e historial.

## Estructuras académicas
- Lista simplemente enlazada manual de pacientes.
- Lista simplemente enlazada manual de historial.
- Matriz dispersa 5 × 6 mediante tripletas: 1 representa ocupada y la ausencia de tripleta representa disponible.
- Arreglo de 30 objetos Habitacion.

Las colecciones auxiliares de la interfaz se generan a partir de las listas propias; no reemplazan las estructuras de datos exigidas.

## Requisitos
- JDK 17 o superior.
- Visual Studio Code.
- Extension Pack for Java.
- No requiere librerías externas ni base de datos.

## Ejecutar desde Visual Studio Code
1. Clonar el repositorio.
2. Abrir la carpeta en VS Code.
3. Instalar Extension Pack for Java.
4. Abrir `src/hospital/Main.java`.
5. Ejecutar **Run Java**.

También puede compilarse desde la raíz:

```bash
javac -d bin src/hospital/modelos/*.java src/hospital/estructuras/*.java src/hospital/gestion/*.java src/hospital/controlador/*.java src/hospital/vista/atomos/*.java src/hospital/vista/moleculas/*.java src/hospital/vista/organismos/*.java src/hospital/vista/plantillas/*.java src/hospital/vista/paginas/*.java src/hospital/Main.java
java -cp bin hospital.Main
```

## Alcance y limitaciones
Los datos se almacenan en memoria y se pierden al cerrar la aplicación. La asignación de habitaciones es manual. El triage se registra como dato académico y no determina automáticamente la habitación. Este software es educativo y no debe utilizarse para decisiones clínicas reales.

## Pruebas de aceptación
1. Registrar un paciente con identificación `1001`.
2. Asignarle la habitación `8`.
3. Verificar que la habitación aparezca ocupada.
4. Dar salida al paciente.
5. Verificar que la habitación quede disponible y aparezcan los movimientos de ingreso y salida en el historial.
6. Intentar dar salida nuevamente y comprobar que el sistema rechace la operación.

---
Proyecto académico — Gestor Hospitalario.
