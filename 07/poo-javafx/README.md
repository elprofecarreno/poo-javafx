# Paso a Paso: Proyecto 07 - poo-javafx (Data Binding y Colecciones Gson - TableView 2/6)

Guía paso a paso para completar la implementación del enlace de datos reactivo (*Data Binding*) en el **Proyecto 07** sobre la base del **Proyecto 06**, usando `PropertyValueFactory`, `ObservableList` y `TypeToken` de Gson.

> Documentación general y diagramas de arquitectura: [../README.md](../README.md)

---

## Paso a Paso para la Construcción Incremental (desde Proyecto 06)

### Paso 1: Configurar `module-info.java` para reflexión de propiedades
En `src/main/java/module-info.java`, añade `requires javafx.base;` y abre el paquete DTO hacia dicho módulo:

```java
module cl.elprofecarreno.app {
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

> [!IMPORTANT]
> La directiva `opens cl.elprofecarreno.app.controller.dto to javafx.base;` es indispensable. Si se omite, `PropertyValueFactory` no podrá leer los métodos `getRunDv()`, `getNombreCompleto()`, etc., y la tabla permanecerá vacía sin mostrar errores evidentes.

### Paso 2: Ajustar el formato de `data.json` a un arreglo plano
Edita `src/main/resources/data.json` asegurando que la raíz sea un array `[ ... ]`:

```json
[
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
```

### Paso 3: Implementar la persistencia en `PersonaDAO.java`
Edita `src/main/java/cl/elprofecarreno/app/dao/PersonaDAO.java`:

```java
package cl.elprofecarreno.app.dao;

import cl.elprofecarreno.app.dao.entity.Persona;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import cl.elprofecarreno.app.dao.excepcion.DAOException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PersonaDAO {
    
    private static final String FILE = "/data.json";

    public List<Persona> findAll() throws DAOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        try(Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))){
            Type listType = new TypeToken<ArrayList<Persona>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch(Exception e){
            throw new DAOException("ERROR al leer data.json", e);
        }
    }
}
```

### Paso 4: Implementar la capa de negocio en `PersonaService.java`
Edita `src/main/java/cl/elprofecarreno/app/service/PersonaService.java`:

```java
package cl.elprofecarreno.app.service;

import cl.elprofecarreno.app.dao.PersonaDAO;
import cl.elprofecarreno.app.dao.excepcion.DAOException;
import cl.elprofecarreno.app.controller.dto.PersonaDTO;
import java.util.List;
import java.util.ArrayList;
import cl.elprofecarreno.app.dao.entity.Persona;

public class PersonaService {

    private PersonaDAO personaDAO;

    public PersonaService() {
        this.personaDAO = new PersonaDAO();
    }

    public List<PersonaDTO> getPersonas(){
        List<PersonaDTO> personas = new ArrayList<>();
        try {
            List<Persona> personasEntity = personaDAO.findAll();
            for(Persona persona : personasEntity){
                personas.add(toDto(persona));
            }
        } catch(DAOException e){
            e.printStackTrace();
        }
        return personas;
    }

    public PersonaDTO toDto(Persona persona){
        return PersonaDTO.builder()
                .runDv(persona.getRun() + "-" + persona.getDv())
                .nombreCompleto(persona.getNombres() + " " + persona.getApellidoPaterno() + " " + persona.getApellidoMaterno())
                .fechaNacimiento(persona.getFechaNacimiento())
                .comuna(persona.getDireccion().getComuna())
                .region(persona.getDireccion().getRegion())
                .direccionCompleto(persona.getDireccion().getCalle() + " " + persona.getDireccion().getNumero())
                .build();
    }
}
```

### Paso 5: Asignar `fx:id` y `fx:controller` en `home.fxml`
Actualiza `src/main/resources/views/home.fxml`:

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
      xmlns="http://javafx.com/javafx/21" xmlns:fx="http://javafx.com/fxml/1" 
      fx:controller="cl.elprofecarreno.app.controller.HomeController">
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
            <TableView fx:id="tblPersonas" prefHeight="200.0" prefWidth="200.0" GridPane.columnIndex="1" GridPane.rowIndex="1">
              <columns>
                <TableColumn fx:id="colRun" prefWidth="75.0" text="RUN" />
                <TableColumn fx:id="colNombre" prefWidth="75.0" text="NOMBRE" />
                  <TableColumn fx:id="colDireccion" prefWidth="75.0" text="DIRECCION" />
                  <TableColumn fx:id="colComuna" prefWidth="75.0" text="COMUNA" />
                  <TableColumn fx:id="colRegion" prefWidth="75.0" text="REGION" />
              </columns>
            </TableView>
            <Button mnemonicParsing="false" text="Button" GridPane.columnIndex="3" GridPane.rowIndex="2" />
         </children>
      </GridPane>
   </children>
</VBox>
```

### Paso 6: Implementar el controlador `HomeController.java`
Edita `src/main/java/cl/elprofecarreno/app/controller/HomeController.java`:

```java
package cl.elprofecarreno.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import cl.elprofecarreno.app.controller.dto.PersonaDTO;
import cl.elprofecarreno.app.service.PersonaService;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;   
import java.util.List;

public class HomeController {
    
    @FXML 
    private TableView<PersonaDTO> tblPersonas;

    @FXML
    private TableColumn<PersonaDTO, String> colRun;

    @FXML
    private TableColumn<PersonaDTO, String> colNombre;

    @FXML
    private TableColumn<PersonaDTO, String> colDireccion;

    @FXML
    private TableColumn<PersonaDTO, String> colComuna;

    @FXML
    private TableColumn<PersonaDTO, String> colRegion;

    private ObservableList<PersonaDTO> personasObservable;
    
    private PersonaService personaService;

    @FXML
    public void initialize(){
        personaService = new PersonaService();
        List<PersonaDTO> personas = new ArrayList<>();

        colRun.setCellValueFactory(new PropertyValueFactory<>("runDv"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccionCompleto"));
        colComuna.setCellValueFactory(new PropertyValueFactory<>("comuna"));
        colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
        
        try{
            personas = personaService.getPersonas();
        } catch(Exception e){
            System.err.println("Error al obtener las personas: " + e.getMessage());
        }

        personasObservable = FXCollections.observableArrayList(personas);
        tblPersonas.setItems(personasObservable);
    }
}
```

---

## Ejecución del Proyecto

Desde este directorio (`07/poo-javafx`):

```bash
mvn clean javafx:run
```

