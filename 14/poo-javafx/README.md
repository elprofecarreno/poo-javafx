# Paso a Paso: Proyecto 14 - poo-javafx (Conexión a Oracle Cloud Database con JDBC y Patrón Singleton)

Guía paso a paso para evolucionar el **Proyecto 12** al **Proyecto 14**, conectando la aplicación JavaFX con la base de datos autónoma **Oracle Cloud Database** creada en la [Clase 13](../../13/README.md) mediante `ojdbc11`, `oraclepki`, Oracle Wallet (`TNS_ADMIN`) y el **Patrón Singleton** en `ConnectionOracleCloudDB`.

> Documentación general y diagramas de arquitectura: [../README.md](../README.md)  
> Guía de creación de la base de datos y descarga del Wallet en Oracle Cloud: [../../13/README.md](../../13/README.md)

---

## Paso a Paso para la Construcción Incremental (desde Proyecto 12)

### Paso 1: Agregar las Dependencias `ojdbc11` y `oraclepki` en `pom.xml`
Abre el archivo `pom.xml` y añade las dos dependencias oficiales de Oracle Database (`23.26.4.0.0`) dentro de la sección `<dependencies>`:

- **Archivo**: `pom.xml`
```xml
        <!-- Driver JDBC Thin oficial de Oracle para Java 11+ -->
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc11</artifactId>
            <version>23.26.4.0.0</version>
            <scope>compile</scope>
        </dependency>
        <!-- Proveedor de seguridad Oracle PKI para leer cwallet.sso (mTLS) -->
        <dependency>
            <groupId>com.oracle.database.security</groupId>
            <artifactId>oraclepki</artifactId>
            <version>23.26.4.0.0</version>
            <scope>compile</scope>
        </dependency>
```

---

### Paso 2: Declarar el Módulo `java.sql` en `module-info.java`
Modifica `src/main/java/module-info.java` agregando `requires java.sql;` al inicio del módulo para habilitar las clases del paquete `java.sql` (`Connection`, `DriverManager`, `SQLException`):

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

### Paso 3: Crear la Clase Singleton `ConnectionOracleCloudDB.java`
Crea la clase `ConnectionOracleCloudDB` dentro del paquete `cl.elprofecarreno.app.dao`. Configura la constante `URL` con el alias `oracledb_high` y el parámetro `TNS_ADMIN` apuntando a la carpeta donde descomprimiste tu archivo `Wallet_oracledb.zip`:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/dao/ConnectionOracleCloudDB.java`
```java
package cl.elprofecarreno.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionOracleCloudDB {
    
    private static ConnectionOracleCloudDB instancia;
    private final static String URL = "jdbc:oracle:thin:@oracledb_high?TNS_ADMIN=/home/ccarreno/Downloads/Wallet_oracledb/";
    private final static String USER = "admin";
    private final static String PASSWORD = "Oracle.DB.2026";
    private Connection connection;

    private ConnectionOracleCloudDB() throws SQLException {
        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connection established successfully.");
    }

    public static ConnectionOracleCloudDB getInstancia() throws SQLException {
        if (instancia == null) {
            instancia = new ConnectionOracleCloudDB();
        }
        return instancia;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
```

> [!IMPORTANT]
> Asegúrate de actualizar la ruta `/home/ccarreno/Downloads/Wallet_oracledb/` por la ruta exacta de tu computador donde descomprimiste la billetera de Oracle Cloud en la Clase 13.

---

### Paso 4: Inicializar la Conexión en `App.java` antes de Lanzar la Interfaz
Actualiza el método `main(String[] args)` en `src/main/java/cl/elprofecarreno/app/App.java` para verificar la conexión antes de ejecutar `launch()`:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/App.java`
```java
    public static void main(String[] args) {
        try {
            ConnectionOracleCloudDB.getInstancia();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // Iniciar la aplicación JavaFX
        launch();
    }
```

---

## Ejecución del Proyecto

Desde este directorio (`14/poo-javafx`):

```bash
mvn clean compile javafx:run
```

Si la configuración del Wallet y las credenciales son correctas, verás en consola:

```text
Connection established successfully.
```

