# Gestor Hospitalario

Aplicación de escritorio en Java Swing para administrar pacientes, habitaciones, ingresos, salidas e historial hospitalario. Proyecto académico de Algoritmos y Programación 3.

## Autores
- José Miguel Barrientos Rivera
- Jorge Chávez

**Institución:** Politécnico Colombiano Jaime Isaza Cadavid  
**Asignatura:** Algoritmos y Programación 3  
**Docente:** Rodolfo Luna Guzmán

## Diseño de interfaz

La interfaz toma como referencia el prototipo **Hospitalize — Panel clínico**. Se adaptaron sus fundamentos visuales al alcance académico de este proyecto:

- Azul clínico `#3468E8`, turquesa `#16B99A`, informativo `#57A5F5`.
- Atención `#E9A83B`, urgente `#E6606F`, fondo suave `#F4F7FC`.
- Tinta `#1D2B49` y texto secundario `#8794AB`.
- Tipografía Inter con fallback del sistema, tarjetas claras, bordes suaves y espaciado consistente.
- Dashboard dinámico con capacidad, habitaciones ocupadas, disponibles, porcentaje de ocupación y distribución por piso.

El prototipo incluye agenda y equipo clínico, pero esas funciones no se agregan porque no hacen parte de los requisitos funcionales actuales.

## Arquitectura

El proyecto separa responsabilidades mediante **Modelo–Vista–Controlador (MVC)** y organiza los componentes visuales con **Atomic Design**.

- **Modelo:** entidades, reglas de negocio y estructuras de datos.
- **Vista:** interfaz de escritorio construida con Java Swing.
- **Controlador:** recibe acciones de la interfaz y coordina el modelo.
- **Átomos:** tokens de color, botones y campos de texto.
- **Moléculas:** campos de formulario y tarjetas de indicadores.
- **Organismos:** navegación lateral.
- **Plantillas:** estructura general de navegación y contenido.
- **Páginas:** dashboard, pacientes, habitaciones e historial, cada una en su propia clase.

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
        ├── DashboardPage.java
        ├── PacientesPage.java
        ├── HabitacionesPage.java
        ├── HistorialPage.java
        └── HospitalPage.java

tests/
└── hospital/
    ├── modelos/
    ├── estructuras/
    └── gestion/
```

## Funcionalidades
- Registro y consulta de pacientes con validación de campos y de identificaciones duplicadas.
- Validación de edad entre 0 y 130 años y triage entre 1 y 5.
- Asignación manual de habitaciones disponibles.
- Consulta de las 30 habitaciones (5 pisos × 6 habitaciones).
- Registro de salidas y liberación de habitaciones.
- Historial de ingresos y salidas con fecha y hora.
- Dashboard con indicadores calculados desde el estado real del modelo.
- Visualización de matriz dispersa y tripletas.

## Estructuras académicas
- Lista simplemente enlazada manual de pacientes.
- Lista simplemente enlazada manual de historial.
- Matriz dispersa 5 × 6 mediante tripletas: 1 representa ocupada y la ausencia de tripleta representa disponible.
- Arreglo de 30 objetos Habitacion.

Las colecciones auxiliares de las tablas Swing se generan a partir de las listas propias; no reemplazan las estructuras de datos exigidas.

## Requisitos
- JDK 17 o superior.
- Apache Maven 3.9 o compatible.
- Visual Studio Code y Extension Pack for Java.

## Ejecutar

Desde la raíz del repositorio:

```bash
mvn clean package
java -cp target/classes hospital.Main
```

También se puede abrir `src/hospital/Main.java` en VS Code y usar **Run Java**.

## Pruebas automatizadas

```bash
mvn test
```

Las pruebas cubren validación de pacientes, lista simplemente enlazada de pacientes, lista de historial, matriz dispersa, registro, asignación, liberación de habitación y prevención de salidas repetidas.

GitHub Actions ejecuta la suite automáticamente con JDK 17 en cada push a `main` y en cada pull request.

## Alcance y limitaciones
Los datos se almacenan en memoria y se pierden al cerrar la aplicación. La asignación de habitaciones es manual. El triage se registra como dato académico y no determina automáticamente la habitación. Este software es educativo y no debe utilizarse para decisiones clínicas reales.
