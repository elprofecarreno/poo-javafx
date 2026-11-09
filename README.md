# POO Fundamentos con JavaFX, Maven y Arquitectura por Capas

Este repositorio contiene una serie de proyectos prácticos e incrementales de **Programación Orientada a Objetos en Java con JavaFX, Maven y el Sistema de Módulos de Java (JPMS)**, organizados secuencialmente desde la carpeta `01` hasta la `12`.

Cada proyecto avanza progresivamente sobre la base del anterior, abordando los fundamentos de POO, la separación de responsabilidades, la arquitectura por capas (**Presentación -> Servicio -> Repositorio -> DAO**), los patrones de diseño (**DTO, DAO, Repository, Builder, Singleton**), la programación genérica (**Java Generics con `IDAO<T>`**), el enlace de datos reactivo (**Data Binding** en `TableView`), la persistencia en archivos planos JSON segregados (**Google Gson** con **Pretty Printing**), la reducción de código repetitivo (**Project Lombok** con `@SuperBuilder`), los principios avanzados de POO (**Herencia, Clases Abstractas, Polimorfismo e Interfaces** tanto en DTOs como en Entidades de Dominio), la construcción de formularios maestros interactivos con paneles dinámicos (**TitledPane, ComboBox, CheckBox**), el manejo formal de excepciones verificadas (**ValidationException, DAOException**) y algoritmos avanzados de validación nacional (**RUT con Módulo 11 y Expresiones Regulares**).

---

## Herramientas e Instalación

### Videos de instalación:
- [Instalación JDK + Maven en Windows](https://youtu.be/8gooYj4gQ-Q)
- [Gluon Scene Builder - Instalación en Windows !!!](https://youtu.be/69Bo7d2agUY)
- [Instalación JDK + Maven en Linux](https://youtu.be/X0TGKU0gBRc)
- [Gluon Scene Builder - Instalación en Linux !!!](https://youtu.be/QW46As8wLOc)

### Repositorios instaladores:
- [Visual Code Instalador en Linux !!!](https://github.com/elprofecarreno/visual-code-linux-install)

---

## Hoja de Ruta de Evolución Incremental

El siguiente diagrama ilustra cómo cada hito construye y refina las capacidades del sistema:

```mermaid
flowchart TD
    P01["01 - Entorno Base\n• Verificación JDK/Maven/JavaFX\n• Navegación primary/secondary\n• FXMLLoader & setRoot"]
    P02["02 - Login Inicial (1/3)\n• Formulario GridPane FXML\n• TextField & PasswordField\n• Captura de eventos & consola"]
    P03["03 - Modularidad y Limpieza (2/3)\n• Subpaquete controller\n• Vistas en /views/\n• Configuración JPMS opens"]
    P04["04 - Arquitectura en Capas (3/3)\n• Capas Controller -> Service -> DAO\n• DTO (UserDTO) & DAOException\n• Persistencia JSON con Gson"]
    P05["05 - Optimización con Lombok\n• Reducción de Boilerplate\n• Patrón Creacional Builder\n• Procesador de Anotaciones Maven"]
    P06["06 - Navegación y Dominio (1/3)\n• Transición de escena a Home\n• Composición: Persona posee Direccion\n• Aplanamiento DTO (PersonaDTO)"]
    P07["07 - TableView Completo (2/3)\n• Data Binding PropertyValueFactory\n• Deserialización con TypeToken\n• Carga reactiva de personas en tabla"]
    P08["08 - Eliminación y Persistencia (3/3)\n• Selección con SelectionModel\n• Mapeo bidireccional DTO <-> Entidad\n• Persistencia JSON con FileWriter"]
    P09["09 - Formulario Maestro y Herencia (4/4)\n• Herencia DTO (Alumno/Profesor)\n• Interfaz IRemunerable y Polimorfismo\n• UI dinámica con TitledPane y ComboBox\n• Redimensionamiento y centrado de Stage"]
    P10["10 - Validación Centralizada y Singleton\n• Patrón Singleton en FormValidator\n• Excepción verificada ValidationException\n• PersonaDTO abstracta y @SuperBuilder\n• Validación condicional en guardar()"]
    P11["11 - Módulo 11 y CRUD Completo\n• Validación de RUT con Módulo 11 y Regex\n• Ciclo CRUD completo en UI y persistencia\n• Control de duplicados en DAO\n• Serialización Pretty JSON con Gson"]
    P12["12 - Patrón Repository e IDAO<T>\n• Interfaz genérica IDAO<T> (Generics)\n• Persona abstracta, Alumno y Profesor\n• PersonaRepository y ruteo polimórfico\n• Almacenamiento segregado alumno/profesor.json"]

    P01 --> P02
    P02 --> P03
    P03 --> P04
    P04 --> P05
    P05 --> P06
    P06 --> P07
    P07 --> P08
    P08 --> P09
    P09 --> P10
    P10 --> P11
    P11 --> P12
```

---

## Índice de Proyectos y Documentación Detallada

Cada carpeta contiene su propio proyecto ejecutable, un `README.md` exhaustivo (con diagramas de componentes, diagramas de clases, diagramas de secuencia/flujo, capturas de pantalla y análisis de diferencias respecto al hito previo) y un `README.md` dedicado exclusivamente al **Paso a Paso** dentro de cada carpeta de proyecto (`XX/poo-javafx/README.md`):

- [**01 - JavaFX (POO)**](01/README.md) · [**Paso a Paso**](01/poo-javafx/README.md)  
  Proyecto inicial y verificación del entorno de desarrollo JavaFX con Maven. Implementa la carga básica de vistas FXML y la conmutación entre pantallas (`primary` y `secondary`) con controladores desacoplados.  
  *Video:* [JavaFX (POO)](https://youtu.be/LNzMEThVvqE)

- [**02 - JavaFX Login 1/3 - POO**](02/README.md) · [**Paso a Paso**](02/poo-javafx/README.md)  
  Construcción del formulario de inicio de sesión (Login) utilizando FXML y su controlador (`LoginController`). Captura credenciales ingresadas por el usuario mediante componentes `TextField` y gestiona el evento de acceso imprimiendo los datos por consola.  
  *Video:* [JavaFX Login 1/3 - POO](https://youtu.be/pcXH2h_e_fw)

- [**03 - JavaFX Login 2/3 - POO**](03/README.md) · [**Paso a Paso**](03/poo-javafx/README.md)  
  Refactorización y separación de responsabilidades. Se organizan los controladores en su propio paquete (`controller`), se centralizan los archivos de vista en `resources/views/`, se añade `PasswordField` para enmascarar contraseñas y se configura el sistema modular de Java (`module-info.java`) con directivas `opens ... to javafx.fxml`.  
  *Video:* [JavaFX Login 2/3 - POO](https://youtu.be/KoVUImFKGJk)

- [**04 - JavaFX Login 3/3 - POO**](04/README.md) · [**Paso a Paso**](04/poo-javafx/README.md)  
  Evolución hacia una arquitectura formal en 3 capas (Presentación -> Negocio -> Acceso a Datos). Implementa autenticación contra un almacén en archivo de texto plano JSON (`authentication.json`) usando **Google Gson**, la biblioteca **Lombok**, el patrón **DTO** (`UserDTO`), la entidad `User`, excepciones personalizadas (`DAOException`) y configuración modular con reflexión.  
  *Video:* [JavaFX Login 3/3 - POO](https://youtu.be/xijdq06FSpg)

- [**05 - JavaFX Corrección Fix Lombok - POO**](05/README.md) · [**Paso a Paso**](05/poo-javafx/README.md)  
  Aprovechamiento integral de **Lombok** y el **patrón Builder**. Se asegura el procesamiento de anotaciones en Maven (`maven-compiler-plugin` con `<annotationProcessorPaths>`) y se refactorizan las clases de modelo (`User` y `UserDTO`) con `@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor` y `@AllArgsConstructor`, eliminando código repetitivo (*boilerplate*) y utilizando el patrón Builder en la capa de negocio (`LoginService`).  
  *Video:* [JavaFX Corrección Fix Lombok - POO](https://youtu.be/620ut4c-YH0)

- [**06 - JavaFX TableView 1/6 - POO**](06/README.md) · [**Paso a Paso**](06/poo-javafx/README.md)  
  Implementa navegación dinámica entre pantallas reemplazando la raíz de la escena (`scene.setRoot`), incorporando la vista principal `Home` con un componente `TableView`. Introduce relaciones de **composición** en el dominio (`Persona` contiene `Direccion`) y el patrón **DTO** (`PersonaDTO`) para el aplanamiento de datos (*flattening*) listos para presentación, consumiendo una colección con estructuras anidadas en JSON (`data.json`).  
  *Video:* [JavaFX TableView 1/6 - POO](https://youtu.be/fuaMsaJYDHE)

- [**07 - JavaFX TableView 2/6 - POO**](07/README.md) · [**Paso a Paso**](07/poo-javafx/README.md)  
  Implementación integral del enlace de datos (*Data Binding*) sobre `TableView` mediante **`PropertyValueFactory`** y **`ObservableList`**. Desarrolla la lectura de colecciones JSON complejas en `PersonaDAO` mediante **`TypeToken`** y formatos de fecha en Gson, la transformación fluida a DTOs en `PersonaService` con Lombok Builder y la configuración de seguridad reflexiva en `module-info.java` abriendo los DTOs a `javafx.base`.  
  *Video:* [JavaFX TableView 2/6 - POO](https://youtu.be/5dQlqytwymo)

- [**08 - JavaFX TableView 3/6 - POO**](08/README.md) · [**Paso a Paso**](08/poo-javafx/README.md)  
  Culminación de la funcionalidad del `TableView` integrando interactividad de selección (`SelectionModel`), validación defensiva en UI, eliminación en memoria (`ObservableList`) y persistencia física reescribiendo el archivo plano `data.json` con **Google Gson** y `FileWriter`. Implementa el **mapeo bidireccional** (`toEntity` con Builder) para reconstruir entidades a partir de DTOs enriquecidos.  
  *Video:* [JavaFX TableView 3/6 - POO](https://youtu.be/765pQZmPnu0)

- [**09 - JavaFX TableView 4/6 - POO**](09/README.md) · [**Paso a Paso**](09/poo-javafx/README.md)  
  Evolución hacia un formulario maestro interactivo y aplicación avanzada de POO mediante **Herencia** e **Interfaces**. Modela especializaciones de dominio extendiendo `PersonaDTO` en `AlumnoDTO` y `ProfesorDTO`, e implementa la interfaz `IRemunerable` para el cálculo de sueldo líquido con polimorfismo. En la interfaz gráfica JavaFX, implementa paneles colapsables `TitledPane`, controles `ComboBox`, `CheckBox`, activación dinámica de formularios según el tipo de persona seleccionado y control dinámico del tamaño y centrado del `Stage` (`setRoot` con dimensiones y `centerOnScreen`).  
  *Video:* [JavaFX TableView 4/6 - POO](https://youtu.be/PQJw53OmQ8Q)

- [**10 - JavaFX TableView 5/6 - POO**](10/README.md) · [**Paso a Paso**](10/poo-javafx/README.md)  
  Creación de una capa desacoplada de validación de UI con el **Patrón Creacional Singleton** (`FormValidator`) y la excepción comprobada `ValidationException`. Refactorización orientada a objetos de `PersonaDTO` como **clase abstracta**, incorporando Lombok **`@SuperBuilder`** en toda la jerarquía de herencia (`AlumnoDTO`, `ProfesorDTO`) para resolver constructores fluidos en clases derivadas. Implementa validaciones de presencia y longitud máxima en el controlador con retroalimentación inmediata en pantalla.  
  *Video:* [JavaFX TableView 5/6 - POO](https://youtu.be/765pQZmPnu0)

- [**11 - JavaFX TableView 6/6 - POO**](11/README.md) · [**Paso a Paso**](11/poo-javafx/README.md)  
  Cierre del **ciclo CRUD completo** (Crear, Leer, Modificar/Refrescar, Eliminar). Integra el algoritmo de **validación de RUT chileno (Módulo 11)** mediante expresiones regulares y cálculo aritmético del dígito verificador. Extiende la capa DAO (`PersonaDAO`) con `save` y control de duplicidad por RUN, optimiza la serialización con **`setPrettyPrinting()`** en Gson para almacenamiento JSON indentado, refactoriza la eliminación (`delete`) con coincidencia de identidad y asegura la recarga reactiva de la grilla directamente desde el archivo persistido.  
  *Video:* [JavaFX TableView 6/6 - POO](https://youtu.be/fVsA8jRUMnM)

- [**12 - JavaFX TableView - Refactor DAO**](12/README.md) · [**Paso a Paso**](12/poo-javafx/README.md)  
  Evolución arquitectónica de la capa de persistencia y dominio mediante el **Patrón Repository** (`PersonaRepository`), contratos genéricos con la interfaz **`IDAO<T>`** y **herencia en entidades de dominio** convirtiendo `Persona` en clase abstracta con **`@SuperBuilder`** y creando las sub-entidades concretas **`Alumno`** y **`Profesor`**. Segrega el almacenamiento físico en archivos JSON independientes (`alumno.json` y `profesor.json`) administrados por `AlumnoDAO` y `ProfesorDAO`, mientras `PersonaRepository` unifica las consultas (`findAll`) y enruta polimórficamente las operaciones de escritura (`save`) y borrado (`delete`) mediante evaluación `instanceof`.
  *Video:* [JavaFX TableView - Refactor DAO](https://youtu.be/GxmGi0AIyMw)

  - [**13 - JavaFX - Oracle Cloud Database**]
  *Video:* [JavaFX - Oracle Cloud Database](https://youtu.be/eQZgz-wNO74)

  - [**14 - JavaFX - Oracle Database Connection**]
  *Video:* [JavaFX - Oracle Cloud Database](https://youtu.be/XLmGSSM-YbE)

---

## Evolución Pedagógica de los Proyectos

| Proyecto | Enfoque Principal | Componentes Clave | Persistencia / Datos | Dependencias Adicionales |
| :--- | :--- | :--- | :--- | :--- |
| **01** | Entorno y navegación básica | `App`, `PrimaryController`, `SecondaryController` | N/A | JavaFX Controls & FXML |
| **02** | Formulario de Login inicial | `LoginController`, `login.fxml` | En memoria / consola | JavaFX Controls & FXML |
| **03** | Separación y modularidad | Paquete `controller`, carpeta `views/`, `PasswordField`, `module-info.java` | En memoria / consola | JavaFX Controls & FXML |
| **04** | Arquitectura en capas y patrones | Capas `controller`, `service`, `dao`, `UserDTO`, `DAOException` | Archivo plano JSON (`authentication.json`) | Gson 2.14.0, Lombok 1.18.36 |
| **05** | Integración total de Lombok y Patrón Builder | Refactorización de `User` y `UserDTO` con anotaciones Lombok; instanciación fluida con `UserDTO.builder()` | Archivo plano JSON (`authentication.json`) | Gson 2.14.0, Lombok 1.18.36 (con Annotation Processor en `maven-compiler-plugin`) |
| **06** | Navegación entre vistas, composición de entidades y aplanamiento en DTO | `App` (`setRoot`), `LoginController`, `HomeController`, `Persona`, `Direccion`, `PersonaDTO`, `TableView` en `home.fxml` | Archivos planos JSON: `authentication.json` y `data.json` (estructuras anidadas) | JavaFX Controls & FXML 21, Gson 2.14.0, Lombok 1.18.36 |
| **07** | Enlace de datos reactivo (Data Binding), colecciones genéricas y JPMS avanzado | `HomeController` (`PropertyValueFactory`, `ObservableList`), `PersonaDAO` (`TypeToken`), `PersonaService` (`toDto` con Builder) | Archivo plano JSON: `data.json` (array raíz de personas) | JavaFX Controls & FXML 21, JavaFX Base, Gson 2.14.0, Lombok 1.18.36 |
| **08** | Interacción, selección de filas, eliminación reactiva y persistencia bidireccional | `HomeController` (`getSelectionModel`, `eliminar`), `PersonaService` (`toEntity`), `PersonaDAO` (`eliminar` con `FileWriter`), `PersonaDTO` | Archivo plano JSON: `data.json` (lectura y sobreescritura) | JavaFX Controls & FXML 21, JavaFX Base, Gson 2.14.0, Lombok 1.18.36 |
| **09** | Formulario maestro, herencia en DTOs, interfaces y controles dinámicos de UI | `HomeController` (paneles dinámicos `TitledPane`, combos, guardado), `AlumnoDTO`, `ProfesorDTO`, `IRemunerable`, `App` (`setRoot` redimensionable con `centerOnScreen`) | Archivo plano JSON: `data.json` (lectura y sobreescritura) | JavaFX Controls & FXML 21, JavaFX Base, Gson 2.14.0, Lombok 1.18.36 |
| **10** | Validación centralizada, patrón Singleton, jerarquía abstracta con `@SuperBuilder` | `FormValidator` (Singleton), `ValidationException`, `PersonaDTO` (abstracta), `AlumnoDTO` y `ProfesorDTO` con `@SuperBuilder`, validación en `guardar()` | Archivo plano JSON: `data.json` (lectura y sobreescritura) | JavaFX Controls & FXML 21, JavaFX Base, Gson 2.14.0, Lombok 1.18.36 |
| **11** | Validación algorítmica de RUT (Módulo 11), ciclo CRUD completo y Pretty JSON | `FormValidator` (`isRutValid` con Regex y Módulo 11), `PersonaDAO` (`save` con unicidad, `delete`, `setPrettyPrinting`), `PersonaService` (`save`), sincronización reactiva | Archivo plano JSON: `data.json` (CRUD integral con formato Pretty Printing) | JavaFX Controls & FXML 21, JavaFX Base, Gson 2.14.0, Lombok 1.18.36 |
| **12** | Patrón Repository, interfaz genérica `IDAO<T>`, herencia en entidades y persistencia JSON segregada | `PersonaRepository`, `IDAO<T>`, `AlumnoDAO`, `ProfesorDAO`, `Persona` (abstracta con `@SuperBuilder`), `Alumno`, `Profesor`, `PersonaService` (`toEntity` polimórfico) | Archivos planos JSON segregados: `alumno.json` y `profesor.json` (Pretty Printing) | JavaFX Controls & FXML 21, JavaFX Base, Gson 2.14.0, Lombok 1.18.36 |

---

## Arquitectura General por Capas

A partir del Proyecto 04 y culminando en el Proyecto 12, la arquitectura del software sigue una separación estricta en capas (Presentación -> Negocio -> Repositorio -> Acceso a Datos):

```mermaid
flowchart LR
    subgraph Capa_Presentacion["Capa de Presentación (UI & Validator)"]
        Vistas["Vistas FXML\n(/views/*.fxml)"]
        Controladores["Controladores JavaFX\n(LoginController, HomeController)"]
        Validador["Módulo Validador\n(FormValidator Singleton,\nValidationException, Módulo 11)"]
        DTOs["DTOs de Transferencia\n(PersonaDTO abstracta, UserDTO,\nAlumnoDTO, ProfesorDTO con @SuperBuilder)"]
    end

    subgraph Capa_Negocio["Capa de Negocio (Service)"]
        Servicios["Servicios\n(LoginService, PersonaService)\n• Reglas de negocio y liquidaciones\n• Mapeo polimórfico DTO <-> Entity\n• Orquestación CRUD"]
    end

    subgraph Capa_Repositorio["Capa de Repositorio (Repository)"]
        Repositorio["Repositorio\n(PersonaRepository)\n• Agregación polimórfica (findAll)\n• Ruteo dinámico por instanceof (save/delete)"]
    end

    subgraph Capa_Datos["Capa de Acceso a Datos (DAO)"]
        ContratoDAO["Contrato Genérico\n(IDAO<T>)"]
        DAOs["DAOs Especializados\n(AlumnoDAO, ProfesorDAO, UserDAO)\n• Lectura/deserialización Gson\n• Escritura Pretty Printing\n• Control de duplicidad"]
        Entidades["Entidades de Dominio\n(Persona abstracta, Alumno, Profesor,\nDireccion, User con @SuperBuilder)"]
        Excepciones["Excepciones Tipadas\n(DAOException)"]
    end

    subgraph Almacenamiento["Almacenamiento de Datos"]
        JSONFiles[("Archivos Planos JSON\nauthentication.json\nalumno.json / profesor.json\ndata.json (Pretty Printing)")]
    end

    Vistas <--> Controladores
    Controladores --> Validador
    Controladores --> DTOs
    Controladores --> Servicios
    Servicios --> Repositorio
    Repositorio --> ContratoDAO
    DAOs -.-> ContratoDAO
    DAOs <--> JSONFiles
    DAOs --> Entidades
    Entidades -.-> Repositorio
    Servicios --> DTOs
```

---

## Objetivo General del Repositorio

El repositorio busca desarrollar competencias profesionales sólidas en el diseño e ingeniería de software orientado a objetos en Java:

- **Pilares de POO**: Encapsulamiento, abstracción, herencia, polimorfismo, modularidad, relaciones entre objetos (**asociación y composición**) y separación de responsabilidades tanto en la capa de presentación como en la de dominio.
- **Arquitectura y Patrones de Diseño**: Organización multicapa (Presentación, Servicio, Repositorio, DAO), uso de objetos de transferencia de datos (**DTO**) para el desacoplamiento y aplanamiento de datos (*data flattening*), abstracción genérica de fuentes de datos (**`IDAO<T>`**), orquestación polimórfica mediante el **Patrón Repository** (`PersonaRepository`) y patrón creacional **Singleton** para servicios universales sin estado.
- **Patrones Creacionales y Reducción de Boilerplate**: Implementación del **patrón Builder** y uso de anotaciones avanzadas de **Lombok** (`@SuperBuilder`, `@Builder`, `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`) para soportar herencia en constructores tanto de DTOs como de Entidades (`Persona` -> `Alumno` / `Profesor`) y mantener un código limpio y mantenible.
- **Manejo de Excepciones**: Creación y propagación de excepciones comprobadas personalizadas (`ValidationException`, `DAOException`) para modelar y aislar errores técnicos y de validación de negocio.
- **Persistencia y Serialización Segregada**: Lectura, inserción, eliminación y serialización de archivos de texto plano en formato JSON (`alumno.json`, `profesor.json`, `authentication.json`) mediante **Google Gson**, gestionando tipos genéricos (`TypeToken`), formatos de fecha, estructuras anidadas y formato legible (**Pretty Printing**).
- **Interfaces Gráficas (GUI) y Enlace de Datos**: Diseño de vistas declarativas con FXML, contenedores semánticos (`TitledPane`, `GridPane`, `VBox`), controles dinámicos (`ComboBox`, `CheckBox`), enlace de propiedades en tablas (`TableView`, `TableColumn`, `PropertyValueFactory`), recarga reactiva de datos y transición dinámica de escenarios con redimensionamiento y centrado adaptativo.
- **Algoritmos y Validación de Negocio**: Implementación de lógica de verificación nacional mediante expresiones regulares (Regex) y cálculo aritmético de verificación por **Módulo 11**.
- **Sistema de Módulos de Java (JPMS)**: Configuración rigurosa de `module-info.java`, gestionando directivas `requires`, `requires static`, `exports` y `opens ... to ...` para permitir reflexión controlada en tiempo de ejecución.

---

## Requisitos y Ejecución

### Requisitos
- **Java JDK**: 11 o superior (recomendado JDK 21).
- **Apache Maven**: 3.8+.
- **IDE**: IntelliJ IDEA, Visual Studio Code, Eclipse o NetBeans (con soporte para Lombok habilitado).

### Ejecutar cualquier proyecto

Puedes ejecutar cualquiera de los proyectos entrando a su respectiva carpeta:

```bash
# Ejemplo para ejecutar el proyecto 12
cd 12/poo-javafx
mvn clean javafx:run
```

O directamente desde la raíz del repositorio indicando la ruta del archivo `pom.xml`:

```bash
# Ejecutar Proyecto 01
mvn -f 01/poo-javafx/pom.xml clean javafx:run

# Ejecutar Proyecto 04
mvn -f 04/poo-javafx/pom.xml clean javafx:run

# Ejecutar Proyecto 07
mvn -f 07/poo-javafx/pom.xml clean javafx:run

# Ejecutar Proyecto 08
mvn -f 08/poo-javafx/pom.xml clean javafx:run

# Ejecutar Proyecto 09
mvn -f 09/poo-javafx/pom.xml clean javafx:run

# Ejecutar Proyecto 10
mvn -f 10/poo-javafx/pom.xml clean javafx:run

# Ejecutar Proyecto 11
mvn -f 11/poo-javafx/pom.xml clean javafx:run

# Ejecutar Proyecto 12
mvn -f 12/poo-javafx/pom.xml clean javafx:run
```

---

## Estructura del Repositorio

```text
poo-javafx/
├── README.md              # Documentación general y arquitectura del repositorio
├── 01/                    # 01 - JavaFX Base y Navegación
│   ├── 01.png
│   ├── README.md          # Documentación arquitectónica y diagramas del Proyecto 01
│   └── poo-javafx/
│       └── README.md      # Guía paso a paso del Proyecto 01
├── 02/                    # 02 - JavaFX Login 1/3 (Formulario y Eventos)
│   ├── 02.png
│   ├── README.md          # Documentación arquitectónica y diagramas del Proyecto 02
│   └── poo-javafx/
│       └── README.md      # Guía paso a paso del Proyecto 02
├── 03/                    # 03 - JavaFX Login 2/3 (Subpaquetes y Modularidad)
│   ├── 03.png
│   ├── README.md          # Documentación arquitectónica y diagramas del Proyecto 03
│   └── poo-javafx/
│       └── README.md      # Guía paso a paso del Proyecto 03
├── 04/                    # 04 - JavaFX Login 3/3 (Arquitectura en Capas, DAO, DTO y Gson)
│   ├── 04.png
│   ├── README.md          # Documentación arquitectónica y diagramas del Proyecto 04
│   └── poo-javafx/
│       └── README.md      # Guía paso a paso del Proyecto 04
├── 05/                    # 05 - JavaFX Fix Lombok (Patrón Builder y Procesador de Anotaciones)
│   ├── 05.png
│   ├── README.md          # Documentación arquitectónica y diagramas del Proyecto 05
│   └── poo-javafx/
│       └── README.md      # Guía paso a paso del Proyecto 05
├── 06/                    # 06 - JavaFX TableView 1/6 (Navegación y Composición de Dominio)
│   ├── 06.png
│   ├── 06_02.png
│   ├── README.md          # Documentación arquitectónica y diagramas del Proyecto 06
│   └── poo-javafx/
│       └── README.md      # Guía paso a paso del Proyecto 06
├── 07/                    # 07 - JavaFX TableView 2/6 (Data Binding y Colecciones Gson)
│   ├── 07.png
│   ├── 07_02.png
│   ├── README.md          # Documentación arquitectónica y diagramas del Proyecto 07
│   └── poo-javafx/
│       └── README.md      # Guía paso a paso del Proyecto 07
├── 08/                    # 08 - JavaFX TableView 3/6 (Selección, Eliminación y Persistencia Bidireccional)
│   ├── 08.png
│   ├── 08_02.png
│   ├── README.md          # Documentación arquitectónica y diagramas del Proyecto 08
│   └── poo-javafx/
│       └── README.md      # Guía paso a paso del Proyecto 08
├── 09/                    # 09 - JavaFX TableView 4/6 (Formulario Maestro, Herencia en DTOs, Interfaces y Controles Dinámicos)
│   ├── 09.png
│   ├── 09_02.png
│   ├── README.md          # Documentación arquitectónica y diagramas del Proyecto 09
│   └── poo-javafx/
│       └── README.md      # Guía paso a paso del Proyecto 09
├── 10/                    # 10 - Validación Centralizada, Patrón Singleton y Jerarquía Abstracta con @SuperBuilder
│   ├── 10.png
│   ├── 10_02.png
│   ├── README.md          # Documentación arquitectónica y diagramas del Proyecto 10
│   └── poo-javafx/
│       └── README.md      # Guía paso a paso del Proyecto 10
├── 11/                    # 11 - Validación de RUT (Módulo 11), Ciclo CRUD Completo y Persistencia Pretty JSON
│   ├── 11.png
│   ├── 11_02.png
│   ├── README.md          # Documentación arquitectónica y diagramas del Proyecto 11
│   └── poo-javafx/
│       └── README.md      # Guía paso a paso del Proyecto 11
└── 12/                    # 12 - Patrón Repository, Interfaz Genérica IDAO<T>, Herencia en Entidades y Persistencia Segregada
    ├── 12.png
    ├── 12_02.png
    ├── README.md          # Documentación arquitectónica y diagramas del Proyecto 12
    └── poo-javafx/
        └── README.md      # Guía paso a paso del Proyecto 12
```
