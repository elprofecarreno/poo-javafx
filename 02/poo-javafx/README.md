# Paso a Paso: Proyecto 02 - poo-javafx (Formulario de Login 1/3)

Guía paso a paso para transformar el **Proyecto 01** en el **Proyecto 02**, construyendo el formulario inicial de inicio de sesión (`login.fxml`) y su controlador (`LoginController`).

> Documentación general y diagramas de arquitectura: [../README.md](../README.md)

---

## Paso a Paso para la Construcción Incremental (desde Proyecto 01)

### Paso 1: Crear la vista FXML del Formulario (`login.fxml`)
Crea el archivo `src/main/resources/cl/elprofecarreno/app/login.fxml`. Estructura un `VBox` que contenga un `GridPane` con etiquetas y campos:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.PasswordField?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<VBox maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" 
      prefHeight="300.0" prefWidth="400.0" 
      xmlns="http://javafx.com/javafx/21" xmlns:fx="http://javafx.com/fxml/1" 
      fx:controller="cl.elprofecarreno.app.LoginController">
   <children>
      <GridPane prefHeight="417.0" prefWidth="600.0">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="143.0" minWidth="10.0" prefWidth="71.0" />
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="245.0" minWidth="10.0" prefWidth="235.0" />
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="223.0" minWidth="10.0" prefWidth="213.0" />
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="126.0" minWidth="10.0" prefWidth="70.0" />
        </columnConstraints>
        <rowConstraints>
            <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
            <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
            <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
            <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <Label fx:id="lblUsuario" text="USUARIO" GridPane.columnIndex="1" GridPane.rowIndex="1" />
            <Label fx:id="lblContrasenia" text="CONTRASEÑA" GridPane.columnIndex="1" GridPane.rowIndex="2" />
            <TextField fx:id="txtUsuario" GridPane.columnIndex="2" GridPane.rowIndex="1" />
            <PasswordField fx:id="txtContrasenia" GridPane.columnIndex="2" GridPane.rowIndex="2" />
            <Button fx:id="btnLogin" mnemonicParsing="false" onAction="#login" text="LOGIN" GridPane.columnIndex="2" GridPane.rowIndex="3" />
         </children>
      </GridPane>
   </children>
</VBox>
```

### Paso 2: Crear la clase controladora `LoginController.java`
Crea `src/main/java/cl/elprofecarreno/app/LoginController.java` para vincular los `fx:id` y gestionar el evento del botón:

```java
package cl.elprofecarreno.app;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private void login() throws IOException {
        System.out.println("USUARIO: " + txtUsuario.getText());
        System.out.println("PASSWORD: " + txtContrasenia.getText());
    }
}
```

### Paso 3: Modificar `App.java` para iniciar con la vista `login`
En `src/main/java/cl/elprofecarreno/app/App.java`, ajusta el método `start` para cargar `"login"`:

```java
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
```

### Paso 4: Validar el descriptor modular (`module-info.java`)
Verifica que `src/main/java/module-info.java` mantenga abierta la reflexión para el paquete base:

```java
module cl.elprofecarreno.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens cl.elprofecarreno.app to javafx.fxml;
    exports cl.elprofecarreno.app;
}
```

---

## Ejecución del Proyecto

Desde este directorio (`02/poo-javafx`):

```bash
mvn clean javafx:run
```

