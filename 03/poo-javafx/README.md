# Paso a Paso: Proyecto 03 - poo-javafx (Modularidad y Subpaquetes Login 2/3)

Guía paso a paso para aplicar la refactorización arquitectónica del **Proyecto 03** sobre la base del **Proyecto 02**, organizando controladores en el paquete `controller`, centralizando vistas en `/views/` y configurando `module-info.java`.

> Documentación general y diagramas de arquitectura: [../README.md](../README.md)

---

## Paso a Paso para la Construcción Incremental (desde Proyecto 02)

### Paso 1: Reorganizar la estructura de paquetes Java
1. Crea la carpeta `src/main/java/cl/elprofecarreno/app/controller/`.
2. Mueve la clase `LoginController.java` dentro de dicha carpeta.
3. Actualiza la declaración del paquete en `LoginController.java`:
   ```java
   package cl.elprofecarreno.app.controller;
   ```
4. Elimina del paquete raíz los archivos residuales `PrimaryController.java` y `SecondaryController.java`.

### Paso 2: Reorganizar el directorio de recursos de vistas FXML
1. Crea el directorio `src/main/resources/views/`.
2. Mueve `login.fxml` hacia `src/main/resources/views/login.fxml`.
3. Elimina el árbol de carpetas obsoleto `src/main/resources/cl/elprofecarreno/app/`.
4. En `src/main/resources/views/login.fxml`, actualiza el atributo `fx:controller` apuntando al nuevo subpaquete:
   ```xml
   <VBox ... fx:controller="cl.elprofecarreno.app.controller.LoginController">
   ```

### Paso 3: Actualizar `App.java` con carga absoluta
Modifica `src/main/java/cl/elprofecarreno/app/App.java`:

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

    static void setRoot(Parent parent) throws IOException {
        scene.setRoot(parent);
    }

    private static Parent getLoadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
```

### Paso 4: Configurar los permisos reflexivos en `module-info.java`
Actualiza `src/main/java/module-info.java` abriendo explícitamente el paquete `controller`:

```java
module cl.elprofecarreno.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens cl.elprofecarreno.app to javafx.fxml;
    opens cl.elprofecarreno.app.controller to javafx.fxml;
    exports cl.elprofecarreno.app;
}
```

> [!IMPORTANT]
> Si olvidas la línea `opens cl.elprofecarreno.app.controller to javafx.fxml;`, JavaFX arrojará una excepción `IllegalAccessException` al intentar inyectar el controlador en tiempo de ejecución.

---

## Ejecución del Proyecto

Desde este directorio (`03/poo-javafx`):

```bash
mvn clean javafx:run
```

