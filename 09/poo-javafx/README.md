# Paso a Paso: Proyecto 09 - poo-javafx (Formulario Maestro, Herencia en DTOs e Interfaces - TableView 4/6)

Guía paso a paso para construir las funcionalidades del **Proyecto 09** sobre la base del **Proyecto 08**, incorporando la interfaz `IRemunerable`, herencia en DTOs (`AlumnoDTO`, `ProfesorDTO`), redimensionamiento dinámico del `Stage` y paneles colapsables `TitledPane` en `home.fxml`.

> Documentación general y diagramas de arquitectura: [../README.md](../README.md)

---

## Paso a Paso para la Construcción Incremental (desde Proyecto 08)

### Paso 1: Definir la interfaz de negocio `IRemunerable.java`
Crea el archivo `src/main/java/cl/elprofecarreno/app/controller/dto/IRemunerable.java`. Define la constante para el descuento previsional e impuestos y el método contractual de cálculo:

```java
package cl.elprofecarreno.app.controller.dto;

public interface IRemunerable {
    
    double PORCENTAJE_DESCUENTOS = 0.17;
    double calcularSueldoLiquido();
}
```

> [!TIP]
> En Java, los atributos declarados dentro de una interfaz son implícitamente `public static final`, lo que permite centralizar constantes de cálculo de negocio compartidas sin recurrir a clases de utilidades estáticas redundantes.

### Paso 2: Actualizar `PersonaDTO.java` incorporando el atributo de estado `activo`
Edita `src/main/java/cl/elprofecarreno/app/controller/dto/PersonaDTO.java` y agrega el atributo booleano `activo`:

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
    private boolean activo;

}
```

### Paso 3: Crear el DTO especializado `AlumnoDTO.java` aplicando Herencia
Crea el archivo `src/main/java/cl/elprofecarreno/app/controller/dto/AlumnoDTO.java`. Esta clase extiende de `PersonaDTO` e incorpora los atributos académicos:

```java
package cl.elprofecarreno.app.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
public class AlumnoDTO extends PersonaDTO {
    
    private String carrera;
    private String semestre;
}
```

### Paso 4: Crear el DTO especializado `ProfesorDTO.java` con Herencia e Implementación de Interfaz
Crea el archivo `src/main/java/cl/elprofecarreno/app/controller/dto/ProfesorDTO.java`. Hereda los atributos personales de `PersonaDTO`, implementa `IRemunerable` y sobreescribe `calcularSueldoLiquido()` aplicando el descuento:

```java
package cl.elprofecarreno.app.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
public class ProfesorDTO extends PersonaDTO implements IRemunerable {
    
    private String especialidad;
    private String jornada;
    private long sueldoBruto;

    @Override
    public double calcularSueldoLiquido() {
        return this.sueldoBruto * (1 - PORCENTAJE_DESCUENTOS);
    }

}
```

### Paso 5: Modificar `App.java` para soportar redimensionamiento y centrado dinámico de ventanas
Edita `src/main/java/cl/elprofecarreno/app/App.java`. Define constantes de tamaño inicial y sobrecarga `setRoot` para ajustar el tamaño del `Window` y recentrarlo en pantalla:

```java
package cl.elprofecarreno.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private final static int WIDTH = 300;
    private final static int HEIGHT = 200;

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(getLoadFXML("/views/login"));
        stage.setScene(scene);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.centerOnScreen();
        stage.show();
    }

    public static void setRoot(Parent parent, int width, int height) throws IOException {
        scene.setRoot(parent);
        scene.getWindow().setWidth(width);
        scene.getWindow().setHeight(height);
        scene.getWindow().centerOnScreen();
    }

    public static Parent getLoadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
```

### Paso 6: Actualizar `LoginController.java` para transicionar enviando las dimensiones del Home
Edita `src/main/java/cl/elprofecarreno/app/controller/LoginController.java`. Al autenticar exitosamente, invoca la sobrecarga de `setRoot` pasando `HomeController.WIDTH` y `HomeController.HEIGHT`:

```java
package cl.elprofecarreno.app.controller;

import java.io.IOException;

import cl.elprofecarreno.app.App;
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
            App.setRoot(App.getLoadFXML("/views/home"), HomeController.WIDTH, HomeController.HEIGHT);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
```

### Paso 7: Rediseñar `home.fxml` con el Formulario Maestro y Paneles `TitledPane`
Edita `src/main/resources/views/home.fxml` estructurando un `VBox` de `1080x800` con un `GridPane` que separa el panel de formularios del `TableView`:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.CheckBox?>
<?import javafx.scene.control.ComboBox?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TableColumn?>
<?import javafx.scene.control.TableView?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.control.TitledPane?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<VBox maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" 
      prefHeight="800.0" prefWidth="1080.0" 
      xmlns="http://javafx.com/javafx/21" xmlns:fx="http://javafx.com/fxml/1" 
      fx:controller="cl.elprofecarreno.app.controller.HomeController">
   <children>
      <GridPane prefHeight="806.0" prefWidth="1051.0">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="303.0" minWidth="10.0" prefWidth="303.0" />
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="938.0" minWidth="10.0" prefWidth="691.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints maxHeight="223.0" minHeight="0.0" prefHeight="0.0" vgrow="SOMETIMES" />
          <RowConstraints maxHeight="685.0" minHeight="10.0" prefHeight="516.0" vgrow="SOMETIMES" />
          <RowConstraints maxHeight="308.0" minHeight="0.0" prefHeight="216.0" vgrow="SOMETIMES" />
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <!-- Tabla de Personas -->
            <TableView fx:id="tblPersonas" prefHeight="512.0" prefWidth="720.0" GridPane.columnIndex="1" GridPane.rowIndex="1">
              <columns>
                <TableColumn fx:id="colRun" prefWidth="75.0" text="RUN" />
                <TableColumn fx:id="colNombre" prefWidth="114.0" text="NOMBRE" />
                <TableColumn fx:id="colDireccion" prefWidth="115.0" text="DIRECCION" />
                <TableColumn fx:id="colComuna" prefWidth="160.0" text="COMUNA" />
                <TableColumn fx:id="colRegion" prefWidth="254.0" text="REGION" />
              </columns>
            </TableView>
            <Label fx:id="lblMessage" disable="true" prefHeight="16.0" prefWidth="449.0" GridPane.columnIndex="1" GridPane.rowIndex="3" />
            
            <!-- Paneles Izquierdos: Datos Genéricos y Datos Alumno -->
            <Pane prefHeight="200.0" prefWidth="200.0" GridPane.rowIndex="1">
               <children>
                  <TitledPane fx:id="pnlDatosGenericos" animated="false" prefHeight="366.0" prefWidth="293.0" text="Datos Genericos">
                    <content>
                      <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="328.0" prefWidth="291.0">
                           <children>
                              <Label fx:id="lblRun" layoutX="14.0" layoutY="22.0" text="RUN" />
                              <TextField fx:id="txtRun" layoutX="111.0" layoutY="18.0" />
                              <Label fx:id="lblNombres" layoutX="13.0" layoutY="53.0" text="NOMBRES" />
                              <TextField fx:id="txtNombres" layoutX="110.0" layoutY="49.0" />
                              <Label fx:id="lblApellidoPaterno" layoutX="13.0" layoutY="85.0" text="AP. PATERNO" />
                              <TextField fx:id="txtApellidoPaterno" layoutX="110.0" layoutY="81.0" />
                              <Label fx:id="lblApellidoMaterno" layoutX="13.0" layoutY="118.0" text="AP. MATERNO" />
                              <TextField fx:id="txtApellidoMaterno" layoutX="110.0" layoutY="114.0" />
                              <Label fx:id="lblCalle" layoutX="13.0" layoutY="152.0" text="CALLE" />
                              <TextField fx:id="txtCalle" layoutX="110.0" layoutY="148.0" />
                              <Label fx:id="lblNumeroCalle" layoutX="13.0" layoutY="185.0" text="Nº CALLE" />
                              <TextField fx:id="txtNumeroCalle" layoutX="110.0" layoutY="181.0" />
                              <Label fx:id="lblComuna" layoutX="12.0" layoutY="215.0" text="COMUNA" />
                              <ComboBox fx:id="cmbComuna" layoutX="110.0" layoutY="211.0" prefHeight="24.0" prefWidth="170.0" />
                              <Label fx:id="lblRegion" layoutX="12.0" layoutY="249.0" text="REGION" />
                              <ComboBox fx:id="cmbRegion" layoutX="110.0" layoutY="245.0" prefHeight="24.0" prefWidth="170.0" />
                              <CheckBox fx:id="chkActivo" layoutX="113.0" layoutY="309.0" mnemonicParsing="false" text="Activo" />
                              <Label fx:id="lblTipo" layoutX="12.0" layoutY="283.0" text="TIPO" />
                              <ComboBox fx:id="cmbTipo" layoutX="110.0" layoutY="279.0" prefHeight="24.0" prefWidth="170.0" />
                           </children>
                        </AnchorPane>
                    </content>
                  </TitledPane>
                  <TitledPane fx:id="pnlDatosAlumno" animated="false" disable="true" layoutY="369.0" prefHeight="122.0" prefWidth="293.0" text="Datos Alumno">
                    <content>
                      <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="91.0" prefWidth="291.0">
                           <children>
                              <Label fx:id="lblCarrera" layoutX="14.0" layoutY="23.0" text="CARRERA" />
                              <TextField fx:id="txtCarrera" layoutX="106.0" layoutY="19.0" />
                              <Label fx:id="lblSemestre" layoutX="14.0" layoutY="55.0" text="SEMESTRE" />
                              <TextField fx:id="txtSemestre" layoutX="106.0" layoutY="51.0" />
                           </children>
                      </AnchorPane>
                    </content>
                  </TitledPane>
               </children>
            </Pane>
            
            <!-- Botón Eliminar debajo de la tabla -->
            <Pane prefHeight="200.0" prefWidth="200.0" GridPane.columnIndex="1" GridPane.rowIndex="2">
               <children>
                  <Button fx:id="btnEliminar" layoutX="6.0" layoutY="14.0" mnemonicParsing="false" onAction="#eliminar" text="Eliminar" />
               </children>
            </Pane>

            <!-- Panel Datos Profesor y Botón Guardar -->
            <Pane prefHeight="200.0" prefWidth="200.0" GridPane.rowIndex="2">
               <children>
                  <TitledPane fx:id="pnlDatosProfesor" animated="false" disable="true" layoutX="7.0" layoutY="10.0" prefHeight="103.0" prefWidth="286.0" text="Datos Profesor">
                    <content>
                      <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="76.0" prefWidth="284.0">
                           <children>
                              <Label fx:id="lblEspecialidad" layoutX="6.0" layoutY="14.0" text="ESPECIALIDAD" />
                              <TextField fx:id="txtEspecialidad" layoutX="106.0" layoutY="10.0" />
                              <Label fx:id="lblJornada" layoutX="6.0" layoutY="42.0" text="JORNADA" />
                              <ComboBox fx:id="cmbJornada" layoutX="106.0" layoutY="38.0" prefHeight="24.0" prefWidth="172.0" />
                           </children>
                        </AnchorPane>
                    </content>
                  </TitledPane>
                  <Button fx:id="btnGuardar" layoutX="227.0" layoutY="124.0" mnemonicParsing="false" onAction="#guardar" text="Guardar" />
               </children>
            </Pane>
         </children>
      </GridPane>
   </children>
</VBox>
```

### Paso 8: Implementar la reactividad y poblamiento en `HomeController.java`
Edita `src/main/java/cl/elprofecarreno/app/controller/HomeController.java`:

```java
package cl.elprofecarreno.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

import cl.elprofecarreno.app.controller.dto.PersonaDTO;
import cl.elprofecarreno.app.service.PersonaService;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;   
import java.util.List;

public class HomeController {

    public final static int WIDTH = 1080;
    public final static int HEIGHT = 800;

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
    private TextField txtRun;
    @FXML 
    private TextField txtNombres;
    @FXML 
    private TextField txtApellidoPaterno;
    @FXML 
    private TextField txtApellidoMaterno;
    @FXML 
    private TextField txtCalle;
    @FXML 
    private ComboBox<String> cmbComuna;
    @FXML 
    private ComboBox<String> cmbRegion;
    @FXML 
    private ComboBox<String> cmbTipo;
    @FXML 
    private TextField txtCarrera;
    @FXML 
    private TextField txtSemestre;
    @FXML 
    private TextField txtEspecialidad;
    @FXML 
    private ComboBox<String> cmbJornada;
    @FXML
    private TitledPane pnlDatosAlumno;
    @FXML
    private TitledPane pnlDatosProfesor;
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
        
        try {
            personas = personaService.getPersonas();
        } catch(Exception e) {
            System.err.println("Error al obtener las personas: " + e.getMessage());
        }
        personasObservable = FXCollections.observableArrayList(personas);
        tblPersonas.setItems(personasObservable);

        ObservableList<String> comunas = FXCollections.observableArrayList(
            "Maipú", "Cerrillos", "Pedro Aguirre Cerda", "Santiago", "Pudahuel", "Padre Hurtado"
        );
        cmbComuna.setItems(comunas);
        cmbComuna.getSelectionModel().selectFirst();

        ObservableList<String> jornadas = FXCollections.observableArrayList("Diurna", "Vespertina");
        cmbJornada.setItems(jornadas);
        cmbJornada.getSelectionModel().selectFirst();

        ObservableList<String> regiones = FXCollections.observableArrayList(
            "Metropolitana", "Valparaíso", "Biobío", "Araucanía"
        );
        cmbRegion.setItems(regiones);
        cmbRegion.getSelectionModel().selectFirst();

        ObservableList<String> tipos = FXCollections.observableArrayList("Alumno", "Profesor");
        cmbTipo.setItems(tipos);
        cmbTipo.getSelectionModel().selectFirst();
        
        pnlDatosAlumno.setDisable(false);
        pnlDatosProfesor.setDisable(true);

        cmbTipo.setOnAction(event -> {
            String selection = cmbTipo.getValue();
            if ("Alumno".equals(selection)) {
                pnlDatosAlumno.setDisable(false);
                pnlDatosProfesor.setDisable(true);
            } else if ("Profesor".equals(selection)) {
                pnlDatosAlumno.setDisable(true);
                pnlDatosProfesor.setDisable(false);
            } else {
                pnlDatosAlumno.setDisable(true);
                pnlDatosProfesor.setDisable(true);
            }
        });
    }

    @FXML
    public void guardar(){
        System.out.println("RUN ingresado: " + txtRun.getText());
        System.out.println("Nombres ingresados: " + txtNombres.getText());
    }

    @FXML 
    public void eliminar(){
        lblMessage.setText("");
        List<PersonaDTO> personasSeleccionadas = tblPersonas.getSelectionModel().getSelectedItems();
        if (personasSeleccionadas.isEmpty()){
            System.err.println("No se ha seleccionado ninguna persona.");
            lblMessage.setText("No se ha seleccionado ninguna persona.");
        } else {
            try {
                personaService.eliminar(personasSeleccionadas);
                personasObservable.removeAll(personasSeleccionadas);
                tblPersonas.refresh();
            } catch(Exception e) {
                System.err.println("Error al eliminar las personas: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
```

---

## Ejecución del Proyecto

Desde este directorio (`09/poo-javafx`):

```bash
mvn clean javafx:run
```

