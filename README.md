# POO Fundamentos

Este repositorio contiene una serie de ejercicios prácticos de Programación Orientada a Objetos en Java con JavaFX y Maven, organizados de forma incremental por carpetas del 01 en adelante. Cada proyecto avanza progresivamente en conceptos fundamentales de POO, arquitectura de software, separación de responsabilidades, diseño de interfaces gráficas, manejo de excepciones y persistencia de datos.

## Herramientas e Instalación

### Videos de instalación:
- [Instalación JDK + Maven en Windows](https://youtu.be/8gooYj4gQ-Q)
- [Gluon Scene Builder - Instalación en Windows !!!](https://youtu.be/69Bo7d2agUY)
- [Instalación JDK + Maven en Linux](https://youtu.be/X0TGKU0gBRc)
- [Gluon Scene Builder - Instalación en Linux !!!](https://youtu.be/QW46As8wLOc)

### Repositorios instaladores:
- [Visual Code Instalador en Linux !!!](https://github.com/elprofecarreno/visual-code-linux-install)

---

## Índice de proyectos

- [01 - JavaFX (POO)](01/README.md)  
  Proyecto inicial y verificación del entorno de desarrollo JavaFX con Maven. Implementa la carga básica de vistas FXML y la navegación entre pantallas (`primary` y `secondary`) con controladores desacoplados.  
  Video: [JavaFX (POO)](https://youtu.be/LNzMEThVvqE)

- [02 - JavaFX Login 1/3 - POO](02/README.md)  
  Construcción del formulario de inicio de sesión (Login) utilizando FXML y un controlador asociado (`LoginController`). Captura credenciales ingresadas por el usuario mediante componentes `TextField` y gestiona el evento de acceso imprimiendo los datos por consola.  
  Video: [JavaFX Login 1/3 - POO](https://youtu.be/pcXH2h_e_fw)

- [03 - JavaFX Login 2/3 - POO](03/README.md)  
  Refactorización del formulario de Login y separación de responsabilidades. Se organizan los controladores en su propio paquete (`controller`), se centralizan los archivos de vista en `resources/views/`, se añade `PasswordField` para enmascarar contraseñas y se configura el sistema modular de Java (`module-info.java`) con directivas `opens ... to javafx.fxml`.  
  Video: [JavaFX Login 2/3 - POO](https://youtu.be/KoVUImFKGJk)

- [04 - JavaFX Login 3/3 - POO](04/README.md)  
  Evolución hacia una arquitectura por capas (Presentación -> Negocio -> Acceso a Datos). Implementa autenticación contra un almacén de datos en archivo de texto plano JSON (`authentication.json`) usando **Google Gson**, la biblioteca **Lombok**, el patrón **DTO** (`UserDTO`), la entidad `User`, excepciones personalizadas (`DAOException`) y configuración modular con reflexión.  
  Video: [JavaFX Login 3/3 - POO](https://youtu.be/xijdq06FSpg)

- [05 - JavaFX Corrección Fix Lombok - POO](05/README.md)  
  Corrección, configuración y aprovechamiento integral de la biblioteca **Lombok** y el **patrón Builder**. Se asegura el procesamiento de anotaciones en Maven (`maven-compiler-plugin` con `<annotationProcessorPaths>`) y se refactorizan las clases de modelo (`User` y `UserDTO`) con `@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor` y `@AllArgsConstructor`, eliminando código repetitivo (*boilerplate*) y utilizando el patrón Builder en la capa de negocio (`LoginService`).  
  Video: [JavaFX Corrección Fix Lombok - POO](https://youtu.be/620ut4c-YH0)

- [06 - JavaFX TableView 1/3 - POO](06/README.md)  
  Implementa navegación dinámica entre pantallas reemplazando la raíz de la escena (`scene.setRoot`), incorporando la vista principal `Home` con un componente `TableView`. Introduce relaciones de **composición** en el dominio (`Persona` contiene `Direccion`) y el patrón **DTO** (`PersonaDTO`) para el aplanamiento de datos (*flattening*) listos para presentación, consumiendo una colección con estructuras anidadas en JSON (`data.json`).
  Video: [JavaFX TableView 1/3 - POO](https://youtu.be/fuaMsaJYDHE)


  

---

## Evolución pedagógica de los proyectos

| Proyecto | Enfoque principal | Componentes clave | Persistencia / Datos | Dependencias adicionales |
| :--- | :--- | :--- | :--- | :--- |
| **01** | Entorno y navegación básica | `App`, `PrimaryController`, `SecondaryController` | N/A | JavaFX Controls & FXML |
| **02** | Formulario de Login inicial | `LoginController`, `login.fxml` | En memoria / consola | JavaFX Controls & FXML |
| **03** | Separación y modularidad | Paquete `controller`, carpeta `views/`, `PasswordField`, `module-info.java` | En memoria / consola | JavaFX Controls & FXML |
| **04** | Arquitectura en capas y patrones | Capas `controller`, `service`, `dao`, `UserDTO`, `DAOException` | Archivo plano JSON (`authentication.json`) | Gson 2.14.0, Lombok 1.18.36 |
| **05** | Integración total de Lombok y Patrón Builder | Refactorización de `User` y `UserDTO` con `@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`; instanciación fluida con `UserDTO.builder()` | Archivo plano JSON (`authentication.json`) | Gson 2.14.0, Lombok 1.18.36 (con Annotation Processor en `maven-compiler-plugin`) |
| **06** | Navegación entre vistas, composición de entidades y aplanamiento en DTO | `App` (`setRoot`), `LoginController`, `HomeController`, `Persona`, `Direccion`, `PersonaDTO`, `TableView` en `home.fxml` | Archivos planos JSON: `authentication.json` y `data.json` (colección con objetos anidados) | JavaFX Controls & FXML 21, Gson 2.14.0, Lombok 1.18.36 |

---

## Objetivo general

El repositorio busca desarrollar habilidades sólidas en el diseño y construcción de aplicaciones orientadas a objetos en Java, abordando:

- **Pilares de POO**: Encapsulamiento, abstracción, modularidad, relaciones entre objetos (**asociación y composición**) y separación de responsabilidades.
- **Arquitectura y Patrones de Diseño**: Organización en capas (Presentación, Servicio, DAO), uso de objetos de transferencia de datos (**DTO**) para el desacoplamiento y aplanamiento de datos (*data flattening*), y abstracción de fuentes de datos.
- **Patrones Creacionales y Reducción de Boilerplate**: Implementación del **patrón Builder** y uso de anotaciones de **Lombok** (`@Builder`, `@Getter`, `@Setter`, etc.) para un código más conciso, mantenible y legible.
- **Manejo de Excepciones**: Creación y propagación de excepciones personalizadas para modelar errores de acceso a datos y reglas de negocio.
- **Persistencia y Serialización**: Lectura y deserialización de archivos de texto plano en formato JSON mediante bibliotecas especializadas como Google Gson, gestionando estructuras anidadas y colecciones.
- **Interfaces Gráficas (GUI) y Navegación**: Diseño de vistas declarativas con FXML, contenedores (`GridPane`, `VBox`), componentes de entrada de datos, componentes tabulares (`TableView`) y transición dinámica entre pantallas en JavaFX.
- **Sistema de Módulos de Java (JPMS)**: Configuración adecuada de `module-info.java`, gestionando directivas `requires`, `requires static`, `exports` y `opens` para permitir reflexión en tiempo de ejecución.

---

## Requisitos y Ejecución

### Requisitos
- **Java JDK**: 11 o superior (recomendado JDK 21)
- **Apache Maven**: 3.8+
- **IDE**: IntelliJ IDEA, Visual Studio Code, Eclipse o NetBeans (con soporte para Lombok habilitado)

### Ejecutar cualquier proyecto

Puedes ejecutar cualquiera de los proyectos entrando a su respectiva carpeta:

```bash
cd 06/poo-javafx
mvn clean javafx:run
```

O directamente desde la raíz del repositorio indicando la ruta del `pom.xml`:

```bash
# Ejemplo para ejecutar el proyecto 06
mvn -f 06/poo-javafx/pom.xml clean javafx:run
```

---

## Estructura del repositorio

Cada carpeta contiene su propio proyecto Maven independiente, acompañado de su respectivo `README.md` detallado y diagrama de clases en Mermaid:

```text
poo-javafx/
├── README.md              # Documentación general del repositorio
├── 01/                    # 01 - JavaFX (POO)
│   ├── README.md
│   └── poo-javafx/
├── 02/                    # 02 - JavaFX Login 1/3 - POO
│   ├── README.md
│   └── poo-javafx/
├── 03/                    # 03 - JavaFX Login 2/3 - POO
│   ├── README.md
│   └── poo-javafx/
├── 04/                    # 04 - JavaFX Login 3/3 - POO
│   ├── README.md
│   └── poo-javafx/
├── 05/                    # 05 - JavaFX Corrección Fix Lombok - POO
│   ├── README.md
│   └── poo-javafx/
└── 06/                    # 06 - JavaFX TableView 1/3 - POO
    ├── README.md
    └── poo-javafx/
```
