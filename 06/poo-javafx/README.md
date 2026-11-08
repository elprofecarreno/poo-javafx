# Paso a Paso: Proyecto 06 - poo-javafx (Navegación y Composición de Dominio - TableView 1/6)

Guía paso a paso para evolucionar el **Proyecto 05** al **Proyecto 06**, implementando la transición a la vista `Home` (`home.fxml`), la composición de dominio (`Persona` posee `Direccion`) y el aplanamiento de datos en `PersonaDTO`.

> Documentación general y diagramas de arquitectura: [../README.md](../README.md)

---

## Paso a Paso para la Construcción Incremental (desde Proyecto 05)

### Paso 1: Modificar `App.java` para permitir navegación pública
Haz que los métodos `setRoot` y `getLoadFXML` sean `public static` en `src/main/java/cl/elprofecarreno/app/App.java`:

```java
package cl.elprofecarreno.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(getLoadFXML("/views/login"));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(Parent parent) throws IOException {
        scene.setRoot(parent);
    }

    public static Parent getLoadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
```

### Paso 2: Crear las entidades de dominio con relación de composición
1. Crea `src/main/java/cl/elprofecarreno/app/dao/entity/Direccion.java`:
   ```java
   package cl.elprofecarreno.app.dao.entity;

   import lombok.AllArgsConstructor;
   import lombok.Builder;
   import lombok.Getter;
   import lombok.NoArgsConstructor;
   import lombok.Setter;

   @Getter
   @Setter
   @Builder
   @NoArgsConstructor
   @AllArgsConstructor
   public class Direccion {
       private String calle;
       private String numero;
       private String comuna;
       private String region;
   }
   ```

2. Crea `src/main/java/cl/elprofecarreno/app/dao/entity/Persona.java` estableciendo la composición con `Direccion`:
   ```java
   package cl.elprofecarreno.app.dao.entity;

   import java.util.Date;
   import lombok.AllArgsConstructor;
   import lombok.Builder;
   import lombok.Getter;
   import lombok.NoArgsConstructor;
   import lombok.Setter;

   @Getter
   @Setter
   @Builder
   @NoArgsConstructor
   @AllArgsConstructor
   public class Persona {
       private int run;
       private String dv;
       private String nombres;
       private String apellidoPaterno;
       private String apellidoMaterno;
       private Direccion direccion;
       private Date fechaNacimiento;
   }
   ```

### Paso 3: Crear el DTO aplanado `PersonaDTO.java`
Crea `src/main/java/cl/elprofecarreno/app/controller/dto/PersonaDTO.java`:

```java
package cl.elprofecarreno.app.controller.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {
    private String runDv;
    private String nombreCompleto;
    private Date fechaNacimiento;
    private String direccionCompleto;
    private String comuna;
    private String region;
}
```

### Paso 4: Crear esqueletos de `PersonaDAO` y `PersonaService`
1. `src/main/java/cl/elprofecarreno/app/dao/PersonaDAO.java`:
   ```java
   package cl.elprofecarreno.app.dao;

   public class PersonaDAO {
   }
   ```
2. `src/main/java/cl/elprofecarreno/app/service/PersonaService.java`:
   ```java
   package cl.elprofecarreno.app.service;

   public class PersonaService {
   }
   ```

### Paso 5: Crear el archivo de datos `data.json`
Crea `src/main/resources/data.json` con la colección de personas y sus direcciones anidadas:

```json
{
  "personas": [
    {
      "run" : "12345678",
      "dv": "9",
      "nombres" : "Juan",
      "apellidoPaterno" : "Perez",
      "apellidoMaterno" : "Gonzalez",
      "fechaNacimiento" : "1990-01-01",
      "direccion" : {
        "calle": "Calle Falsa 123",
        "numero": "123",
        "comuna": "Santiago",
        "region": "Metropolitana"
      }
    },
    {
      "run" : "87654321",
      "dv": "K",
      "nombres" : "Maria",
      "apellidoPaterno" : "Lopez",
      "apellidoMaterno" : "Rodriguez",
      "fechaNacimiento" : "1995-05-15",
      "direccion" : {
        "calle": "Avenida Siempre Viva 742",
        "numero": "742",
        "comuna": "Providencia",
        "region": "Metropolitana"
      }
    }
  ]
}
```

### Paso 6: Crear la vista principal `home.fxml`
Crea `src/main/resources/views/home.fxml` con el diseño de la tabla:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.TableColumn?>
<?import javafx.scene.control.TableView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<VBox maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" 
      prefHeight="400.0" prefWidth="600.0" 
      xmlns="http://javafx.com/javafx/21" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane prefHeight="460.0" prefWidth="600.0">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="143.0" minWidth="10.0" prefWidth="25.0" />
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="489.0" minWidth="10.0" prefWidth="489.0" />
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="223.0" minWidth="0.0" prefWidth="0.0" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="126.0" minWidth="10.0" prefWidth="67.0" />
        </columnConstraints>
        <rowConstraints>
            <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
          <RowConstraints maxHeight="294.0" minHeight="10.0" prefHeight="294.0" vgrow="SOMETIMES" />
          <RowConstraints maxHeight="126.0" minHeight="10.0" prefHeight="47.0" vgrow="SOMETIMES" />
            <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <TableView prefHeight="200.0" prefWidth="200.0" GridPane.columnIndex="1" GridPane.rowIndex="1">
              <columns>
                <TableColumn prefWidth="75.0" text="RUN" />
                <TableColumn prefWidth="75.0" text="NOMBRE" />
                  <TableColumn prefWidth="75.0" text="DIRECCION" />
                  <TableColumn prefWidth="75.0" text="COMUNA" />
                  <TableColumn prefWidth="75.0" text="REGION" />
              </columns>
            </TableView>
            <Button mnemonicParsing="false" text="Button" GridPane.columnIndex="3" GridPane.rowIndex="2" />
         </children>
      </GridPane>
   </children>
</VBox>
```

### Paso 7: Crear el controlador `HomeController.java`
Crea `src/main/java/cl/elprofecarreno/app/controller/HomeController.java`:

```java
package cl.elprofecarreno.app.controller;

public class HomeController {
}
```

### Paso 8: Actualizar `LoginController.java` para transicionar de vista
En `src/main/java/cl/elprofecarreno/app/controller/LoginController.java`, tras validar el login, agrega la invocación de navegación:

```java
package cl.elprofecarreno.app.controller;

import java.io.IOException;

import cl.elprofecarreno.app.App;
import cl.elprofecarreno.app.controller.dto.UserDTO;
import cl.elprofecarreno.app.service.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    private LoginService loginService;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private void login() throws IOException {
        loginService = new LoginService();
        try {
            UserDTO user = loginService.authenticate(txtUsuario.getText(), txtContrasenia.getText());
            System.out.println("LOGIN CORRECTO");
            System.out.println(user.getUsername());
            App.setRoot(App.getLoadFXML("/views/home"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
```

---

## Ejecución del Proyecto

Desde este directorio (`06/poo-javafx`):

```bash
mvn clean javafx:run
```

