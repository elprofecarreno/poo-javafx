# Paso a Paso: Proyecto 01 - poo-javafx (Entorno Base y Navegación)

Guía paso a paso para construir el **Proyecto 01** desde cero utilizando **JavaFX**, **Maven** y el **Sistema de Módulos de Java (JPMS)**.

> Documentación general y diagramas de arquitectura: [../README.md](../README.md)

---

## Paso a Paso para la Construcción del Proyecto

### Paso 1: Generación del proyecto con Maven Archetype
Ejecuta el siguiente comando en la terminal para crear la estructura base modular con soporte JavaFX:

```bash
mvn archetype:generate \
  -DarchetypeGroupId=org.openjfx \
  -DarchetypeArtifactId=javafx-archetype-fxml \
  -DarchetypeVersion=0.0.6 \
  -DgroupId=cl.elprofecarreno \
  -DartifactId=poo-javafx \
  -Dversion=1.0-SNAPSHOT \
  -Dpackage=cl.elprofecarreno.app \
  -Djavafx-version=21 \
  -Djavafx-maven-plugin-version=0.0.8 \
  -DinteractiveMode=false
```

### Paso 2: Configuración de `pom.xml`
Asegúrate de que las dependencias de JavaFX y el plugin de compilación estén correctamente definidos en `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>21</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.0</version>
            <configuration>
                <release>11</release>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-maven-plugin</artifactId>
            <version>0.0.8</version>
            <configuration>
                <mainClass>cl.elprofecarreno.app.App</mainClass>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### Paso 3: Configuración del descriptor modular (`module-info.java`)
Crea el archivo `src/main/java/module-info.java` para permitir que el cargador de JavaFX acceda por reflexión a las clases del paquete:

```java
module cl.elprofecarreno.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens cl.elprofecarreno.app to javafx.fxml;
    exports cl.elprofecarreno.app;
}
```

### Paso 4: Creación de la clase principal `App.java`
Implementa la lógica del ciclo de vida y navegación dinámica en `src/main/java/cl/elprofecarreno/app/App.java`:

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
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
```

### Paso 5: Creación de las vistas FXML
1. `src/main/resources/cl/elprofecarreno/app/primary.fxml`:
   - Contenedor `VBox` con texto `"Primary View"` y un botón enlazado a `#switchToSecondary`.
2. `src/main/resources/cl/elprofecarreno/app/secondary.fxml`:
   - Contenedor `VBox` con texto `"Secondary View"` y un botón enlazado a `#switchToPrimary`.

### Paso 6: Creación de los controladores
1. `src/main/java/cl/elprofecarreno/app/PrimaryController.java`:
   ```java
   package cl.elprofecarreno.app;
   import java.io.IOException;
   import javafx.fxml.FXML;

   public class PrimaryController {
       @FXML
       private void switchToSecondary() throws IOException {
           App.setRoot("secondary");
       }
   }
   ```
2. `src/main/java/cl/elprofecarreno/app/SecondaryController.java`:
   ```java
   package cl.elprofecarreno.app;
   import java.io.IOException;
   import javafx.fxml.FXML;

   public class SecondaryController {
       @FXML
       private void switchToPrimary() throws IOException {
           App.setRoot("primary");
       }
   }
   ```

---

## Ejecución del Proyecto

Desde este directorio (`01/poo-javafx`):

```bash
mvn clean javafx:run
```

