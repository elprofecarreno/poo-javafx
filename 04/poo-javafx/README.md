# Paso a Paso: Proyecto 04 - poo-javafx (Arquitectura en Capas, DAO, DTO y Gson - Login 3/3)

Guía paso a paso para construir el **Proyecto 04** a partir del **Proyecto 03**, incorporando la arquitectura formal en 3 capas (`controller`, `service`, `dao`), `UserDTO`, `User`, `DAOException` y persistencia JSON con **Google Gson**.

> Documentación general y diagramas de arquitectura: [../README.md](../README.md)

---

## Paso a Paso para la Construcción Incremental (desde Proyecto 03)

### Paso 1: Agregar dependencias y procesador en `pom.xml`
Incorpora **Google Gson** y **Lombok** en `pom.xml`, configurando el procesador de anotaciones en el compilador:

```xml
<dependencies>
    <!-- JavaFX -->
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

    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.36</version>
        <scope>provided</scope>
    </dependency>

    <!-- Google Gson -->
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.14.0</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.13.0</version>
            <configuration>
                <release>11</release>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.projectlombok</groupId>
                        <artifactId>lombok</artifactId>
                        <version>1.18.36</version>
                    </path>
                </annotationProcessorPaths>
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

### Paso 2: Configurar permisos en `module-info.java`
Abre la entidad a Gson y declara las nuevas dependencias en `src/main/java/module-info.java`:

```java
module cl.elprofecarreno.app {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.google.gson;
    requires static lombok;

    opens cl.elprofecarreno.app to javafx.fxml;
    opens cl.elprofecarreno.app.controller to javafx.fxml;
    opens cl.elprofecarreno.app.dao.entity to com.google.gson;
    exports cl.elprofecarreno.app;
}
```

### Paso 3: Crear el archivo de persistencia `authentication.json`
Crea `src/main/resources/authentication.json`:

```json
{
    "username": "admin",
    "password": "qwerty"
}
```

### Paso 4: Crear la excepción personalizada `DAOException.java`
Crea `src/main/java/cl/elprofecarreno/app/dao/excepcion/DAOException.java`:

```java
package cl.elprofecarreno.app.dao.excepcion;

public class DAOException extends Exception {
    public DAOException(String message) {
        super(message);
    }
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### Paso 5: Crear la entidad de persistencia `User.java`
Crea `src/main/java/cl/elprofecarreno/app/dao/entity/User.java`:

```java
package cl.elprofecarreno.app.dao.entity;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}
```

### Paso 6: Crear el DTO `UserDTO.java`
Crea `src/main/java/cl/elprofecarreno/app/controller/dto/UserDTO.java`:

```java
package cl.elprofecarreno.app.controller.dto;

public class UserDTO {
    private String username;

    public UserDTO(String username) {
        this.username = username;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
```

### Paso 7: Implementar la capa de datos `UserDAO.java`
Crea `src/main/java/cl/elprofecarreno/app/dao/UserDAO.java`:

```java
package cl.elprofecarreno.app.dao;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import com.google.gson.Gson;
import cl.elprofecarreno.app.dao.entity.User;
import cl.elprofecarreno.app.dao.excepcion.DAOException;

public class UserDAO {

    public User authenticate(String username, String password) throws DAOException {
        User user = readUser(username, password);

        if(user == null || !user.getUsername().equals(username) || !user.getPassword().equals(password)) {
            throw new DAOException("ERROR: login fallido. Usuario o contraseña incorrectos");
        }
        return user;
    }

    public User readUser(String username, String password) throws DAOException {
        URL url = getClass().getResource("/authentication.json");
        if(url == null){
            throw new DAOException("ERROR: login fallido. Archivo authentication.json");
        }

        try {
            Path path = Path.of(url.toURI());
            String json = Files.readString(path, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            return gson.fromJson(json, User.class);
        } catch (Exception e) {
            throw new DAOException("ERROR: login fallido. No se pudo leer el archivo authentication.json", e);
        }
    }
}
```

### Paso 8: Implementar la capa de negocio `LoginService.java`
Crea `src/main/java/cl/elprofecarreno/app/service/LoginService.java`:

```java
package cl.elprofecarreno.app.service;

import cl.elprofecarreno.app.controller.dto.UserDTO;
import cl.elprofecarreno.app.dao.UserDAO;
import cl.elprofecarreno.app.dao.entity.User;
import cl.elprofecarreno.app.dao.excepcion.DAOException;

public class LoginService {
    
    private UserDAO userDAO;

    public LoginService() {
        this.userDAO = new UserDAO();
    }

    public UserDTO authenticate(String username, String password) throws DAOException {
        User user = userDAO.authenticate(username, password);
        return new UserDTO(user.getUsername());
    }
}
```

### Paso 9: Actualizar el controlador `LoginController.java`
Modifica `src/main/java/cl/elprofecarreno/app/controller/LoginController.java` para delegar en `LoginService` dentro de un bloque `try-catch`:

```java
package cl.elprofecarreno.app.controller;

import java.io.IOException;
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
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
```

---

## Ejecución del Proyecto

Desde este directorio (`04/poo-javafx`):

```bash
mvn clean javafx:run
```

