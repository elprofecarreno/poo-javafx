# Paso a Paso: Proyecto 05 - poo-javafx (Optimización con Lombok y Patrón Builder)

Guía paso a paso para refactorizar el **Proyecto 04** hacia el **Proyecto 05**, incorporando el procesamiento de anotaciones de **Lombok** y el **Patrón Creacional Builder** en `User` y `UserDTO`.

> Documentación general y diagramas de arquitectura: [../README.md](../README.md)

---

## Paso a Paso para la Construcción Incremental (desde Proyecto 04)

### Paso 1: Configurar el procesador de anotaciones en `pom.xml`
Asegúrate de configurar `maven-compiler-plugin` (versión 3.13.0) con el bloque `<annotationProcessorPaths>`:

```xml
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
```

### Paso 2: Verificar `module-info.java`
Comprueba que `src/main/java/module-info.java` contemple la directiva estática para Lombok y la apertura para Gson:

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

### Paso 3: Refactorizar la entidad `User.java`
Reemplaza el código repetitivo en `src/main/java/cl/elprofecarreno/app/dao/entity/User.java` con las anotaciones de Lombok:

```java
package cl.elprofecarreno.app.dao.entity;

import com.google.gson.annotations.SerializedName;
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
public class User {
    
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;
}
```

### Paso 4: Refactorizar el DTO `UserDTO.java`
Reemplaza los constructores y métodos en `src/main/java/cl/elprofecarreno/app/controller/dto/UserDTO.java`:

```java
package cl.elprofecarreno.app.controller.dto;

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
public class UserDTO {
    private String username;
}
```

### Paso 5: Actualizar `LoginService.java` con el Patrón Builder
Modifica el método `authenticate` en `src/main/java/cl/elprofecarreno/app/service/LoginService.java` para construir el DTO con el Builder:

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
        return UserDTO.builder()
                .username(user.getUsername())
                .build();
    }
}
```

### Paso 6: Compilar y validar el procesamiento de anotaciones
Ejecuta la fase de compilación de Maven para comprobar que el procesador genera los métodos correctamente:

```bash
mvn clean compile
```

---

## Ejecución del Proyecto

Desde este directorio (`05/poo-javafx`):

```bash
mvn clean javafx:run
```

