# Paso a Paso: Proyecto 08 - poo-javafx (Selección, Eliminación y Persistencia Bidireccional - TableView 3/6)

Guía paso a paso para integrar la selección de filas (`SelectionModel`), el mapeo inverso (`toEntity`) y la eliminación persistente sobre `data.json` en el **Proyecto 08** a partir del **Proyecto 07**.

> Documentación general y diagramas de arquitectura: [../README.md](../README.md)

---

## Paso a Paso para la Construcción Incremental (desde Proyecto 07)

### Paso 1: Extender `PersonaDTO.java` para admitir mapeo inverso
Edita `src/main/java/cl/elprofecarreno/app/controller/dto/PersonaDTO.java`. Agrega los atributos atómicos individuales y corrige la propiedad `direccionCompleta`:

```java
package cl.elprofecarreno.app.controller.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;       

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDTO {
    
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

}
```

> [!NOTE]
> Conservar los campos `nombres`, `apellidoPaterno`, `apellidoMaterno`, `calle` y `numero` dentro del DTO permite ejecutar una reconstrucción fidedigna de las entidades `Persona` y `Direccion` sin perder atomicidad al transformar de regreso al dominio.

### Paso 2: Actualizar la vista `home.fxml` con botón y etiqueta
Modifica `src/main/resources/views/home.fxml` para reemplazar el botón genérico por `btnEliminar` con su acción `onAction="#eliminar"` e incorporar `lblMessage`:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
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
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="98.0" minWidth="10.0" prefWidth="13.0" />
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="458.0" minWidth="10.0" prefWidth="458.0" />
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="162.0" minWidth="0.0" prefWidth="26.0" />
            <ColumnConstraints hgrow="SOMETIMES" maxWidth="196.0" minWidth="10.0" prefWidth="60.0" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="105.0" minWidth="10.0" prefWidth="28.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
          <RowConstraints maxHeight="247.0" minHeight="10.0" prefHeight="247.0" vgrow="SOMETIMES" />
          <RowConstraints maxHeight="94.0" minHeight="0.0" prefHeight="0.0" vgrow="SOMETIMES" />
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
            <Button fx:id="btnEliminar" mnemonicParsing="false" onAction="#eliminar" text="Eliminar" GridPane.columnIndex="1" GridPane.rowIndex="2" />
            <Label fx:id="lblMessage" disable="true" prefHeight="16.0" prefWidth="449.0" GridPane.columnIndex="1" GridPane.rowIndex="3" />
         </children>
      </GridPane>
   </children>
</VBox>
```

### Paso 3: Implementar la persistencia de eliminación en `PersonaDAO.java`
Edita `src/main/java/cl/elprofecarreno/app/dao/PersonaDAO.java` e incorpora el método `eliminar(List<Persona> personas)`:

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
        }catch(Exception e){
            throw new DAOException("ERROR al leer data.json", e);
        }
    }

    public void eliminar(List<Persona> personas) throws DAOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        try(Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))){
            Type listType = new TypeToken<ArrayList<Persona>>(){}.getType();
            List<Persona> data = gson.fromJson(reader, listType);
            data.removeAll(personas);
            try (java.io.Writer writer = new java.io.FileWriter(getClass().getResource(FILE).getFile())) {
                gson.toJson(data, writer);
            }
        }catch(Exception e){
            throw new DAOException("ERROR al leer data.json", e);
        }
    }
}
```

### Paso 4: Implementar desaplanamiento y eliminación en `PersonaService.java`
Edita `src/main/java/cl/elprofecarreno/app/service/PersonaService.java` para agregar `eliminar()`, `toEntity()` y actualizar `toDto()`:

```java
package cl.elprofecarreno.app.service;

import cl.elprofecarreno.app.dao.PersonaDAO;
import cl.elprofecarreno.app.dao.excepcion.DAOException;
import cl.elprofecarreno.app.controller.dto.PersonaDTO;
import java.util.List;
import java.util.ArrayList;

import cl.elprofecarreno.app.dao.entity.Direccion;
import cl.elprofecarreno.app.dao.entity.Persona;

public class PersonaService {

    private PersonaDAO personaDAO;

    public PersonaService() {
        this.personaDAO = new PersonaDAO();
    }

    public List<PersonaDTO> getPersonas(){
        List<PersonaDTO> personas = new ArrayList<>();
        try{
            List<Persona> personasEntity = personaDAO.findAll();
            for(Persona persona : personasEntity){
                personas.add(toDto(persona));
            }
        }catch(DAOException e){
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
                .nombres(persona.getNombres())
                .apellidoPaterno(persona.getApellidoPaterno())
                .apellidoMaterno(persona.getApellidoMaterno())
                .calle(persona.getDireccion().getCalle())
                .numero(persona.getDireccion().getNumero())
                .direccionCompleta(persona.getDireccion().getCalle() + " " + persona.getDireccion().getNumero())
                .build();
    }

    public void eliminar(List<PersonaDTO> personas) throws DAOException{
        List<Persona> personasEntity = new ArrayList<>();
        for(PersonaDTO personaDTO : personas){
            personasEntity.add(toEntity(personaDTO));
        }
        personaDAO.eliminar(personasEntity);
    }

    public Persona toEntity(PersonaDTO personaDTO){
        String[] run = personaDTO.getRunDv().split("-");
        Persona persona = Persona.builder()
                .run(Integer.valueOf(run[0]))
                .dv(run[1])
                .nombres(personaDTO.getNombres())
                .apellidoPaterno(personaDTO.getApellidoPaterno())
                .apellidoMaterno(personaDTO.getApellidoMaterno())
                .fechaNacimiento(personaDTO.getFechaNacimiento())
                .direccion(
                    Direccion.builder()
                        .calle(personaDTO.getCalle())
                        .numero(personaDTO.getNumero())
                        .comuna(personaDTO.getComuna())
                        .region(personaDTO.getRegion())
                        .build()
                )
                .build();
        return persona;
    }
}
```

### Paso 5: Implementar la gestión de selección y refresco en `HomeController.java`
Edita `src/main/java/cl/elprofecarreno/app/controller/HomeController.java`:

```java
package cl.elprofecarreno.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

import cl.elprofecarreno.app.controller.dto.PersonaDTO;
import cl.elprofecarreno.app.service.PersonaService;
import javafx.scene.control.Label;
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
    private Label lblMessage;

    @FXML
    public void initialize(){
        personaService = new PersonaService();
        List<PersonaDTO> personas = new ArrayList<>();

        colRun.setCellValueFactory(new PropertyValueFactory<>("runDv"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccionCompleta"));
        colComuna.setCellValueFactory(new PropertyValueFactory<>("comuna"));
        colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
        
        try{
            personas = personaService.getPersonas();
        }catch(Exception e){
            System.err.println("Error al obtener las personas: " + e.getMessage());
        }
        personasObservable = FXCollections.observableArrayList(personas);
        tblPersonas.setItems(personasObservable);
    }

    @FXML 
    public void eliminar(){
        lblMessage.setText("");
        List<PersonaDTO> personasSeleccionadas = tblPersonas.getSelectionModel().getSelectedItems();
        if(personasSeleccionadas.isEmpty()){
            System.err.println("No se ha seleccionado ninguna persona.");
            lblMessage.setText("No se ha seleccionado ninguna persona.");
        }else {
            try{
                personaService.eliminar(personasSeleccionadas);
                personasObservable.removeAll(personasSeleccionadas);
                tblPersonas.refresh();
            }catch(Exception e){
                System.err.println("Error al eliminar las personas: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
```

---

## Ejecución del Proyecto

Desde este directorio (`08/poo-javafx`):

```bash
mvn clean javafx:run
```

