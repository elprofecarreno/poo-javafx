# Proyecto 03 - poo-javafx

Este proyecto corresponde a la estructuración y separación de responsabilidades en una aplicación JavaFX con Maven. El objetivo es organizar el código fuente separando los controladores en su propio paquete, centralizar las vistas FXML en una carpeta de recursos dedicada (`views`), implementar el uso de `PasswordField` para campos de contraseña y configurar el sistema de módulos de Java (`module-info.java`) con los permisos requeridos por JavaFX.

## ¿Qué hace?

La clase principal `App` inicia la aplicación JavaFX cargando la vista `/views/login.fxml`. La interfaz presenta un formulario de inicio de sesión centrado mediante un contenedor `GridPane` dentro de un `VBox`, con campos para el usuario (`TextField`) y la contraseña oculta (`PasswordField`), además de un botón de acceso.

El controlador `LoginController` (ubicado en el paquete `cl.elprofecarreno.app.controller`) gestiona el evento del botón `btnLogin` mediante el método `login()`, capturando los textos ingresados y mostrándolos en la consola del sistema.

## Diagrama de clases

```mermaid
classDiagram
    class App {
        -scene: Scene$
        +start(stage: Stage) void
        +main(args: String[])$ void
        ~setRoot(parent: Parent)$ void
        -getLoadFXML(fxml: String)$ Parent
    }

    class LoginController {
        -txtUsuario: TextField
        -txtContrasenia: TextField
        -login() void
    }

    class LoginView {
        <<FXML>>
        +lblUsuario: Label
        +lblContrasenia: Label
        +txtUsuario: TextField
        +txtContrasenia: PasswordField
        +btnLogin: Button
    }

    App --> LoginView : carga /views/login.fxml
    LoginView --> LoginController : fx:controller
    LoginController ..> App : controlador asociado
```

## Estructura de clases y relaciones

- **`App`**: Es la clase principal que extiende de `Application`. En el método `start(Stage stage)` inicializa la escena a partir de `/views/login.fxml` mediante el método auxiliar `getLoadFXML()`.
- **`LoginController`**: Pertenece al paquete `cl.elprofecarreno.app.controller`. Contiene los atributos vinculados mediante `@FXML`:
  - `txtUsuario`: Control de texto convencional para el nombre de usuario.
  - `txtContrasenia`: Control para la contraseña (enlazado al `PasswordField` de la vista).
  - `login()`: Método que se ejecuta al presionar el botón `LOGIN` para leer e imprimir en consola las credenciales ingresadas.
- **`module-info.java`**: Define el módulo `cl.elprofecarreno.app`. Requiere `javafx.controls` y `javafx.fxml`. Además de abrir el paquete base, abre explícitamente `cl.elprofecarreno.app.controller to javafx.fxml`, lo cual es indispensable para que el motor de JavaFX pueda instanciar e inyectar componentes en el controlador mediante reflexión.
- **`login.fxml`**: Se localiza de manera ordenada en la ruta `src/main/resources/views/login.fxml`, separando los archivos FXML de la estructura de paquetes de clases Java.

## Requisitos

- Java JDK 11 o superior (compatible con JDK 21)
- Maven
- JavaFX
- IDE compatible con Java (IntelliJ IDEA, VS Code, NetBeans, etc.)

## Ejecutar el proyecto

Desde la carpeta del proyecto, ejecuta:

```bash
cd poo-javafx
mvn clean javafx:run
```

O desde la raíz del repositorio:

```bash
mvn -f 03/poo-javafx/pom.xml clean javafx:run
```

## Resultado esperado

Al ejecutar la aplicación, se abrirá una ventana de 400x300 con el formulario de login estructurado en una cuadrícula. Al ingresar los datos (con la contraseña enmascarada) y presionar el botón `LOGIN`, se imprimirán los valores en la consola:

```text
USUARIO: <usuario ingresado>
PASSWORD: <contraseña ingresada>
```

## Estructura del proyecto

```text
poo-javafx/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   ├── module-info.java
        │   └── cl/elprofecarreno/app/
        │       ├── App.java
        │       └── controller/
        │           └── LoginController.java
        └── resources/
            └── views/
                └── login.fxml
```

## Objetivo del ejercicio

Este ejercicio pretende comprender cómo:

- Organizar proyectos JavaFX en paquetes independientes según su responsabilidad (`controller`, vistas).
- Centralizar y organizar archivos de interfaz FXML dentro del directorio `resources/views/`.
- Configurar adecuadamente `module-info.java` al trabajar con subpaquetes (`opens ... to javafx.fxml`).
- Utilizar `PasswordField` en FXML para ocultar la contraseña ingresada por el usuario.
- Cargar vistas FXML utilizando rutas absolutas al classpath (`/views/...`).
