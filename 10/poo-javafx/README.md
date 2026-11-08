# Paso a Paso: Proyecto 10 - poo-javafx (Validación Centralizada, Singleton y @SuperBuilder - TableView 5/6)

Guía paso a paso para evolucionar el **Proyecto 09** al **Proyecto 10**, incorporando la capa de validación con el **Patrón Singleton** (`FormValidator`), `ValidationException` y la jerarquía abstracta de `PersonaDTO` con `@SuperBuilder`.

> Documentación general y diagramas de arquitectura: [../README.md](../README.md)

---

## Paso a Paso para la Construcción Incremental (desde Proyecto 09)

### Paso 1: Crear la Excepción de Negocio `ValidationException.java`
Dentro del paquete de controladores, crea un nuevo subpaquete `validator` y define la excepción verificada:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/controller/validator/ValidationException.java`
```java
package cl.elprofecarreno.app.controller.validator;

public class ValidationException extends Exception {
    
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

---

### Paso 2: Crear el Validador Centralizado con Patrón Singleton `FormValidator.java`
Implementa las reglas de validación comunes para controles `TextField` de JavaFX:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/controller/validator/FormValidator.java`
```java
package cl.elprofecarreno.app.controller.validator;

import javafx.scene.control.TextField;

public class FormValidator {
    
    // 1. Instancia estática privada
    private static FormValidator instancia;

    // 2. Constructor privado para evitar la instanciación externa
    private FormValidator() {
    }

    // 3. Método público estático para obtener la instancia única
    public static FormValidator getInstancia() {
        if (instancia == null) {
            instancia = new FormValidator();
        }
        return instancia;
    }

    public void isEmpty(TextField textField, String fieldName) throws ValidationException {
        if (textField.getText() == null || textField.getText().trim().isEmpty()) {
            throw new ValidationException("El campo " + fieldName + " no puede estar vacío");
        }
    }

    public void isMaxSize(TextField textField, String fieldName, int maxSize) throws ValidationException {
        if (textField.getText() != null && textField.getText().trim().length() > maxSize) {
            throw new ValidationException("El campo " + fieldName + " no puede exceder " + maxSize + " caracteres");
        }
    }

    public void isEmptyOrMaxSize(TextField textField, String fieldName, int maxSize) throws ValidationException {
        isEmpty(textField, fieldName);
        isMaxSize(textField, fieldName, maxSize);
    }
}
```

---

### Paso 3: Refactorizar `PersonaDTO.java` como Clase Abstracta con `@SuperBuilder`
Convierte la clase en abstracta e incorpora `@SuperBuilder` para soportar herencia en el builder:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/controller/dto/PersonaDTO.java`
```java
package cl.elprofecarreno.app.controller.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class PersonaDTO {
    
    private String runDv;
    private String nombreCompleto;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Date fechaNacimiento;
    private String direccionCompleta;
    private String calle;
    private String numero;
    private String comuna;
    private String region;
    private boolean activo;
}
```

---

### Paso 4: Actualizar `AlumnoDTO.java` y `ProfesorDTO.java` con `@SuperBuilder`
Permite que ambas subclases hereden el mecanismo constructor de la superclase abstracta:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/controller/dto/AlumnoDTO.java`
```java
package cl.elprofecarreno.app.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
@SuperBuilder 
public class AlumnoDTO extends PersonaDTO {
    
    private String carrera;
    private String semestre;
}
```

- **Archivo**: `src/main/java/cl/elprofecarreno/app/controller/dto/ProfesorDTO.java`
```java
package cl.elprofecarreno.app.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
@SuperBuilder 
public class ProfesorDTO extends PersonaDTO implements IRemunerable {
    
    private String especialidad;
    private String jornada;
    private long sueldoBruto;

    @Override
    public double calcularSueldoLiquido() {
        return sueldoBruto * (1 - PORCENTAJE_DESCUENTOS);
    }
}
```

---

### Paso 5: Actualizar `PersonaService.java` para Retornar Subclase Concreta
Dado que `PersonaDTO` ahora es abstracta, el método `toDto` debe devolver una instancia concreta (por ejemplo, `AlumnoDTO`):

- **Archivo**: `src/main/java/cl/elprofecarreno/app/service/PersonaService.java`
```java
    public PersonaDTO toDto(Persona persona){
        return AlumnoDTO.builder()
                .runDv(persona.getRun() + "-" + persona.getDv())
                .nombreCompleto(persona.getNombres() + " " + persona.getApellidoPaterno() + " " + persona.getApellidoMaterno())
                .fechaNacimiento(persona.getFechaNacimiento())
                .direccionCompleta(persona.getDireccion().getCalle() + " " + persona.getDireccion().getNumero())
                .comuna(persona.getDireccion().getComuna())
                .region(persona.getDireccion().getRegion())
                .nombres(persona.getNombres())
                .apellidoPaterno(persona.getApellidoPaterno())
                .apellidoMaterno(persona.getApellidoMaterno())
                .calle(persona.getDireccion().getCalle())
                .numero(persona.getDireccion().getNumero())
                .build();
    }
```

---

### Paso 6: Corregir `home.fxml` y Vincular `txtNumeroCalle`
En el archivo de interfaz de usuario `src/main/resources/views/home.fxml`:
1. Asegúrate de que el campo de texto correspondiente a "AP. MATERNO" posea el identificador único `fx:id="txtApellidoMaterno"` (reemplazando la duplicidad previa de `txtApellidoPaterno`).
2. Verifica que el campo de "Nº CALLE" posea `fx:id="txtNumeroCalle"`.

---

### Paso 7: Implementar la Validación en `HomeController.java`
Inyecta el nuevo campo de número de calle e implementa la validación dentro del método `@FXML public void guardar()`:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/controller/HomeController.java`
```java
    @FXML 
    private TextField txtNumeroCalle;

    @FXML
    public void guardar(){
        try {
            // Validaciones de presencia y longitud máxima en campos comunes
            FormValidator.getInstancia().isEmptyOrMaxSize(txtRun, "RUN", 15);
            FormValidator.getInstancia().isEmptyOrMaxSize(txtNombres, "Nombres", 100);
            FormValidator.getInstancia().isEmptyOrMaxSize(txtApellidoPaterno, "Apellido Paterno", 100);
            FormValidator.getInstancia().isEmptyOrMaxSize(txtApellidoMaterno, "Apellido Materno", 100);
            FormValidator.getInstancia().isEmptyOrMaxSize(txtCalle, "Calle", 100);
            FormValidator.getInstancia().isEmptyOrMaxSize(txtNumeroCalle, "Número de Calle", 20);

            String run = txtRun.getText();
            String nombres = txtNombres.getText();
            String apellidoPaterno = txtApellidoPaterno.getText();
            String apellidoMaterno = txtApellidoMaterno.getText();
            String calle = txtCalle.getText();
            String numeroCalle = txtNumeroCalle.getText();
            String tipo = cmbTipo.getValue();

            PersonaDTO persona;

            // Validación condicional según el rol seleccionado
            if ("Alumno".equals(tipo)) {
                FormValidator.getInstancia().isEmptyOrMaxSize(txtCarrera, "Carrera", 100);
                FormValidator.getInstancia().isEmptyOrMaxSize(txtSemestre, "Semestre", 20);
                String carrera = txtCarrera.getText();
                String semestre = txtSemestre.getText();
                persona = AlumnoDTO.builder()
                        .nombres(nombres)
                        .apellidoPaterno(apellidoPaterno)
                        .apellidoMaterno(apellidoMaterno)
                        .calle(calle)
                        .numero(numeroCalle)
                        .carrera(carrera)
                        .semestre(semestre)
                        .build();
            } else if ("Profesor".equals(tipo)) {
                FormValidator.getInstancia().isEmptyOrMaxSize(txtEspecialidad, "Especialidad", 100);
                String especialidad = txtEspecialidad.getText();
                persona = ProfesorDTO.builder()
                        .nombres(nombres)
                        .apellidoPaterno(apellidoPaterno)
                        .apellidoMaterno(apellidoMaterno)
                        .calle(calle)
                        .numero(numeroCalle)
                        .especialidad(especialidad)
                        .build();
            }

            System.out.println("Objeto creado");
            
        } catch (ValidationException e) {
            System.err.println("Error de validación: " + e.getMessage());
            lblMessage.setText("Error de validación: " + e.getMessage());
        }
    }
```

---

## Ejecución del Proyecto

Desde este directorio (`10/poo-javafx`):

```bash
mvn clean compile javafx:run
```

