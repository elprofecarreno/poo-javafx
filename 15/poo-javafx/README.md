# Paso a Paso: Proyecto 15 - poo-javafx (Conexión a Oracle Database On-Premise / Local XE con JDBC y Patrón Singleton)

Guía paso a paso para evolucionar el **Proyecto 14** al **Proyecto 15**, implementando la conexión hacia una base de datos **Oracle Database On-Premise (Local / Docker / XE)** mediante `ConnectionOracleOnPrimesDB` con el **Patrón Singleton** y el driver `ojdbc11`.

> Documentación general y diagramas de arquitectura: [../README.md](../README.md)

---

## Paso a Paso para la Construcción Incremental (desde Proyecto 14)

### Paso 1: Verificar las Dependencias JDBC en `pom.xml` y `module-info.java`
Asegúrate de contar con el driver `ojdbc11` en `pom.xml` y la directiva `requires java.sql;` en `src/main/java/module-info.java`:

- **Archivo**: `pom.xml`
```xml
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc11</artifactId>
            <version>23.26.4.0.0</version>
            <scope>compile</scope>
        </dependency>
```

- **Archivo**: `src/main/java/module-info.java`
```java
module cl.elprofecarreno.app {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    requires com.google.gson;
    requires static lombok;
    requires javafx.base;

    opens cl.elprofecarreno.app to javafx.fxml;
    opens cl.elprofecarreno.app.controller to javafx.fxml;
    opens cl.elprofecarreno.app.controller.dto to javafx.base;
    opens cl.elprofecarreno.app.dao.entity to com.google.gson;
    exports cl.elprofecarreno.app;
}
```

---

### Paso 2: Crear la Clase Singleton `ConnectionOracleOnPrimesDB.java`
Crea la clase `ConnectionOracleOnPrimesDB` dentro del paquete `cl.elprofecarreno.app.dao` configurando la URL JDBC Thin hacia el servidor local (`jdbc:oracle:thin:@localhost:1522:xe`) con las credenciales `system` / `system`:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/dao/ConnectionOracleOnPrimesDB.java`
```java
package cl.elprofecarreno.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionOracleOnPrimesDB {
   
    private static ConnectionOracleOnPrimesDB instancia;
    private final static String URL = "jdbc:oracle:thin:@localhost:1522:xe";
    private final static String USER = "system";
    private final static String PASSWORD = "system";
    private Connection connection;

    private ConnectionOracleOnPrimesDB() throws SQLException {
        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connection established successfully.");
    }

    public static ConnectionOracleOnPrimesDB getInstancia() throws SQLException {
        if (instancia == null) {
            instancia = new ConnectionOracleOnPrimesDB();
        }
        return instancia;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
```

---

### Paso 3: Conmutar la Conexión Inicial en `App.java`
Actualiza `src/main/java/cl/elprofecarreno/app/App.java` para importar e invocar `ConnectionOracleOnPrimesDB.getInstancia()` en el método `main(String[] args)`:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/App.java`
```java
package cl.elprofecarreno.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import cl.elprofecarreno.app.dao.ConnectionOracleOnPrimesDB;

public class App extends Application {
    // ...
    public static void main(String[] args) {
        try {
            ConnectionOracleOnPrimesDB.getInstancia();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // Iniciar la aplicación JavaFX
        launch();
    }
}
```

---

## Ejecución del Proyecto

Desde este directorio (`15/poo-javafx`):

```bash
mvn clean compile javafx:run
```

Si la instancia local de Oracle Database XE se encuentra activa en `localhost:1522`, verás en consola:

```text
Connection established successfully.
```
