# Paso a Paso: Proyecto 11 - poo-javafx (Validación de RUT Módulo 11, Ciclo CRUD Completo y Pretty JSON - TableView 6/6)

Guía paso a paso para evolucionar el **Proyecto 10** al **Proyecto 11**, incorporando el algoritmo matemático de **Módulo 11** en `FormValidator`, la persistencia con `save` y `delete` en `PersonaDAO` usando `setPrettyPrinting()` y la recarga reactiva del `TableView`.

> Documentación general y diagramas de arquitectura: [../README.md](../README.md)

---

## Paso a Paso para la Construcción Incremental (desde Proyecto 10)

### Paso 1: Extender `FormValidator.java` con el Algoritmo de Módulo 11
Agrega la expresión regular de formato de RUT chileno y el método `isRutValid`:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/controller/validator/FormValidator.java`
```java
package cl.elprofecarreno.app.controller.validator;

import java.util.regex.Pattern;
import javafx.scene.control.TextField;

public class FormValidator {
    
    private static FormValidator instancia;

    // Patrón Regex para RUT: con o sin puntos y con guion opcional
    private static final Pattern RUT_PATTERN = Pattern.compile("^(\\d{1,3}(?:\\.\\d{3}){2}|\\d{7,8})-?[\\dkK]$");

    private FormValidator() {
    }

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

    public void isRutValid(TextField textField) throws ValidationException {
        isEmpty(textField, "RUT");
        String rutInput = textField.getText();
        String rutLimpiar = rutInput.trim().toUpperCase();

        // 1. Validar formato con expresión regular
        if (!RUT_PATTERN.matcher(rutLimpiar).matches()) {
            throw new ValidationException("El campo RUT no tiene un formato válido");
        }

        try {
            // 2. Limpiar puntos y guion
            String rutSinPuntosNiGuion = rutLimpiar.replace(".", "").replace("-", "");
            String cuerpo = rutSinPuntosNiGuion.substring(0, rutSinPuntosNiGuion.length() - 1);
            char dvIngresado = rutSinPuntosNiGuion.charAt(rutSinPuntosNiGuion.length() - 1);

            // 3. Calcular dígito verificador por Módulo 11
            int rutAux = Integer.parseInt(cuerpo);
            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            char dvEsperado = (char) (s != 0 ? s + 47 : 75); // ASCII 75 es 'K'

            if (dvIngresado != dvEsperado) {
                throw new ValidationException("Dígito verificador del RUT no es válido");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("El campo RUT no tiene un formato válido");
        }
    }
}
```

---

### Paso 2: Implementar `save` y Mejorar `delete` en `PersonaDAO.java`
Incorpora comprobación de duplicados, comparación estricta por RUN y serialización con `setPrettyPrinting()`:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/dao/PersonaDAO.java`
```java
    public void delete(List<Persona> personas) throws DAOException {
        // Gson configurado con indentación legible (Pretty Printing)
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))) {
            Type listType = new TypeToken<ArrayList<Persona>>(){}.getType();
            List<Persona> data = gson.fromJson(reader, listType);

            // Búsqueda explícita por RUN
            List<Persona> personasAEliminar = new ArrayList<>();
            for (Persona p : data) {
                for (Persona pE : personas) {
                    if (p.getRun() == pE.getRun()) {
                        personasAEliminar.add(p);
                    }
                }
            }
            data.removeAll(personasAEliminar);

            // Reescribir archivo data.json con formato indentado
            try (java.io.Writer writer = new java.io.FileWriter(getClass().getResource(FILE).getFile())) {
                gson.toJson(data, writer);
            }
        } catch (Exception e) {
            throw new DAOException("ERROR al leer data.json", e);
        }
    }

    public void save(Persona persona) throws DAOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))) {
            Type listType = new TypeToken<ArrayList<Persona>>(){}.getType();
            List<Persona> data = gson.fromJson(reader, listType);

            // Verificación de unicidad de llave primaria (RUN)
            for (Persona p : data) {
                if (p.getRun() == persona.getRun()) {
                    throw new DAOException("Persona con RUN " + persona.getRun() + " ya existe");
                }
            }
            data.add(persona);

            // Persistencia física en disco
            try (java.io.Writer writer = new java.io.FileWriter(getClass().getResource(FILE).getFile())) {
                gson.toJson(data, writer);
            }
        } catch (DAOException e) {
            throw e;
        } catch (Exception e) {
            throw new DAOException("ERROR al leer data.json", e);
        }
    }
```

---

### Paso 3: Agregar `save` en `PersonaService.java`
Conecta el método del servicio con la transformación a entidad y la llamada al DAO:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/service/PersonaService.java`
```java
    public void eliminar(List<PersonaDTO> personas) throws DAOException {
        List<Persona> personasEntity = new ArrayList<>();
        for (PersonaDTO personaDTO : personas) {
            personasEntity.add(toEntity(personaDTO));
        }
        // Invoca el nuevo método delete del DAO
        personaDAO.delete(personasEntity);
    }

    public void save(PersonaDTO personaDTO) throws DAOException {
        personaDAO.save(toEntity(personaDTO));
    }
```

---

### Paso 4: Implementar la Lógica Completa de `guardar()` y `eliminar()` en `HomeController.java`
Vincula la validación del RUT, el mapeo completo de campos, la persistencia y la sincronización reactiva de la grilla:

- **Archivo**: `src/main/java/cl/elprofecarreno/app/controller/HomeController.java`
```java
    @FXML
    public void guardar(){
        try {
            // 1. Validaciones de presencia, longitud y cálculo de RUT
            FormValidator.getInstancia().isEmptyOrMaxSize(txtRun, "RUN", 15);
            FormValidator.getInstancia().isRutValid(txtRun);
            FormValidator.getInstancia().isEmptyOrMaxSize(txtNombres, "Nombres", 100);
            FormValidator.getInstancia().isEmptyOrMaxSize(txtApellidoPaterno, "Apellido Paterno", 100);
            FormValidator.getInstancia().isEmptyOrMaxSize(txtApellidoMaterno, "Apellido Materno", 100);
            FormValidator.getInstancia().isEmptyOrMaxSize(txtCalle, "Calle", 100);
            FormValidator.getInstancia().isEmptyOrMaxSize(txtNumeroCalle, "Número de Calle", 20);

            // 2. Extracción de valores
            String runDv = txtRun.getText();
            String nombres = txtNombres.getText();
            String apellidoPaterno = txtApellidoPaterno.getText();
            String apellidoMaterno = txtApellidoMaterno.getText();
            String nombreCompleto = nombres + " " + apellidoPaterno + " " + apellidoMaterno;
            String calle = txtCalle.getText();
            String numeroCalle = txtNumeroCalle.getText();
            String tipo = cmbTipo.getValue();
            String comuna = cmbComuna.getValue();
            String region = cmbRegion.getValue();

            PersonaDTO persona;

            // 3. Validación y construcción condicional según rol
            if ("Alumno".equals(tipo)) {
                FormValidator.getInstancia().isEmptyOrMaxSize(txtCarrera, "Carrera", 100);
                FormValidator.getInstancia().isEmptyOrMaxSize(txtSemestre, "Semestre", 20);
                String carrera = txtCarrera.getText();
                String semestre = txtSemestre.getText();
                persona = AlumnoDTO.builder()
                        .runDv(runDv)
                        .nombreCompleto(nombreCompleto)
                        .nombres(nombres)
                        .apellidoPaterno(apellidoPaterno)
                        .apellidoMaterno(apellidoMaterno)
                        .calle(calle)
                        .numero(numeroCalle)
                        .carrera(carrera)
                        .semestre(semestre)
                        .comuna(comuna)
                        .region(region)
                        .direccionCompleta(calle + " " + numeroCalle)
                        .build();
            } else if ("Profesor".equals(tipo)) {
                FormValidator.getInstancia().isEmptyOrMaxSize(txtEspecialidad, "Especialidad", 100);
                String especialidad = txtEspecialidad.getText();
                persona = ProfesorDTO.builder()
                        .runDv(runDv)
                        .nombreCompleto(nombreCompleto)
                        .nombres(nombres)
                        .apellidoPaterno(apellidoPaterno)
                        .apellidoMaterno(apellidoMaterno)
                        .calle(calle)
                        .numero(numeroCalle)
                        .comuna(comuna)
                        .region(region)
                        .especialidad(especialidad)
                        .direccionCompleta(calle + " " + numeroCalle)
                        .build();
            } else {
                throw new ValidationException("Tipo de persona no válido");
            }

            // 4. Persistir registro a través del servicio
            personaService.save(persona);

            // 5. Refrescar estado de la tabla consultando los datos frescos
            personasObservable.clear();
            personasObservable.addAll(personaService.getPersonas());
            tblPersonas.refresh();
            lblMessage.setText("Persona guardada correctamente.");

        } catch (DAOException e) {
            System.err.println("Error de validación: " + e.getMessage());
            lblMessage.setText("Error de validación: " + e.getMessage());
        } catch (ValidationException e) {
            System.err.println("Error de validación: " + e.getMessage());
            lblMessage.setText("Error de validación: " + e.getMessage());
        }
    }

    @FXML 
    public void eliminar(){
        ObservableList<PersonaDTO> personasSeleccionadas = tblPersonas.getSelectionModel().getSelectedItems();
        if (personasSeleccionadas.isEmpty()) {
            lblMessage.setText("No se ha seleccionado ninguna persona.");
        } else {
            try {
                // Eliminar en la capa de persistencia
                personaService.eliminar(personasSeleccionadas);
                // Sincronizar grilla recargando desde la fuente de verdad
                personasObservable.clear();
                personasObservable.addAll(personaService.getPersonas());
                tblPersonas.refresh();
            } catch (Exception e) {
                lblMessage.setText("ERROR al eliminar persona seleccionada");
            }
        }
    }
```

---

## Ejecución del Proyecto

Desde este directorio (`11/poo-javafx`):

```bash
mvn clean compile javafx:run
```

