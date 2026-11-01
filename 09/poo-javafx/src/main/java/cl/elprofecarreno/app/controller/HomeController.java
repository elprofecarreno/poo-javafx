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

    // Mapeo tabla de personas con fxml
    // Inicializa el componente con fxml (fx : id = tblPersonas)
    @FXML 
    private TableView<PersonaDTO> tblPersonas;

    // Mapeo columna "Run" de la tabla de personas con fxml
    // Inicializa el componente con fxml (fx : id = colRun)
    @FXML
    private TableColumn<PersonaDTO, String> colRun;

    // Mapeo columna "Nombre" de la tabla de personas con fxml
    // Inicializa el componente con fxml (fx : id = colNombre)
    @FXML
    private TableColumn<PersonaDTO, String> colNombre;

    // Mapeo columna "Direccion" de la tabla de personas con fxml
    // Inicializa el componente con fxml (fx : id = colDireccion)
    @FXML
    private TableColumn<PersonaDTO, String> colDireccion;

    // Mapeo columna "Comuna" de la tabla de personas con fxml
    // Inicializa el componente con fxml (fx : id = colComuna)
    @FXML
    private TableColumn<PersonaDTO, String> colComuna;

    // Mapeo columna "Region" de la tabla de personas con fxml
    // Inicializa el componente con fxml (fx : id = colRegion)
    @FXML
    private TableColumn<PersonaDTO, String> colRegion;

    private ObservableList<PersonaDTO> personasObservable;
    
    private PersonaService personaService;

    // Mapeo campo de texto "Run" con fxml
    // Inicializa el componente con fxml (fx : id = txtRun)
    @FXML 
    private TextField txtRun;
    
    // Mapeo campo de texto "Nombres" con fxml
    // Inicializa el componente con fxml (fx : id = txtNombres)
    @FXML 
    private TextField txtNombres;

    // Mapeo campo de texto "Apellido Paterno" con fxml
    // Inicializa el componente con fxml (fx : id = txtApellidoPaterno)
    @FXML 
    private TextField txtApellidoPaterno;

    // Mapeo campo de texto "Apellido Materno" con fxml
    // Inicializa el componente con fxml (fx : id = txtApellidoMaterno)
    @FXML 
    private TextField txtApellidoMaterno;

    // Mapeo campo de texto "Calle" con fxml
    // Inicializa el componente con fxml (fx : id = txtCalle)
    @FXML 
    private TextField txtCalle;

    // Mapeo combo box "Comuna" con fxml
    // Inicializa el componente con fxml (fx : id = cmbComuna)
    @FXML 
    private ComboBox<String> cmbComuna;

    // Mapeo combo box "Region" con fxml
    // Inicializa el componente con fxml (fx : id = cmbRegion)
    @FXML 
    private ComboBox<String> cmbRegion;

    // Mapeo combo box "Tipo" con fxml
    // Inicializa el componente con fxml (fx : id = cmbTipo)
    @FXML 
    private ComboBox<String> cmbTipo;

    // Mapeo campo de texto "Carrera" con fxml
    // Inicializa el componente con fxml (fx : id = txtCarrera)
    @FXML 
    private TextField txtCarrera;

    @FXML 
    private TextField txtSemestre;

    // Mapeo campo de texto "Especialidad" con fxml
    // Inicializa el componente con fxml (fx : id = txtEspecialidad)
    @FXML 
    private TextField txtEspecialidad;

    // Mapeo combo box "Jornada" con fxml
    // Inicializa el componente con fxml (fx : id = cmbJornada)
    @FXML 
    private ComboBox<String> cmbJornada;

    // Mapeo panel de datos del alumno con fxml
    // Inicializa el componente con fxml (fx : id = pnlDatosAlumno)
    @FXML
    private TitledPane pnlDatosAlumno;

    // Mapeo panel de datos del profesor con fxml
    // Inicializa el componente con fxml (fx : id = pnlDatosProfesor)
    @FXML
    private TitledPane pnlDatosProfesor;

    // Mapeo etiqueta de mensaje con fxml
    // Inicializa el componente con fxml (fx : id = lblMessage)
    @FXML 
    private Label lblMessage;

    @FXML
    public void initialize(){
        // Inicializar el servicio de personas
        personaService = new PersonaService();
        // Inicializar la lista de personas observable
        List<PersonaDTO> personas = new ArrayList<>();

        // Configurar las columnas de la tabla con las propiedades de PersonaDTO (Mapeo columnas de la tabla)
        colRun.setCellValueFactory(new PropertyValueFactory<>("runDv"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccionCompleta"));
        colComuna.setCellValueFactory(new PropertyValueFactory<>("comuna"));
        colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
        
        try{
            // Obtener la lista de personas desde el servicio
            personas = personaService.getPersonas();
        }catch(Exception e){
            // Mostrar mensaje de error en la consola
            System.err.println("Error al obtener las personas: " + e.getMessage());
        }
        // Crear la lista observable a partir de la lista de personas obtenida
        personasObservable = FXCollections.observableArrayList(personas);
        // Asignar la lista observable a la tabla de personas
        tblPersonas.setItems(personasObservable);

        // Configurar lista de comunas en el combo box
        ObservableList<String> comunas = FXCollections.observableArrayList("Maipú", "Cerrillos", "Pedro Aguirre Cerda", "Santiago",
            "Pudahuel", "Padre Hurtado"
        );
        // Asignar la lista de comunas al combo box
        cmbComuna.setItems(comunas);
        // Seleccionar la primera comuna por defecto
        cmbComuna.getSelectionModel().selectFirst();

        // Configurar lista de jornadas en el combo box
        ObservableList<String> jornadas = FXCollections.observableArrayList("Diurna", "Vespertina");
        // Asignar la lista de jornadas al combo box
        cmbJornada.setItems(jornadas);
        // Seleccionar la primera jornada por defecto
        cmbJornada.getSelectionModel().selectFirst();

        // Configurar lista de regiones en el combo box
        ObservableList<String> regiones = FXCollections.observableArrayList("Metropolitana", "Valparaíso", "Biobío", "Araucanía");
        // Asignar la lista de regiones al combo box
        cmbRegion.setItems(regiones);
        // Seleccionar la primera región por defecto
        cmbRegion.getSelectionModel().selectFirst();

        // Configurar lista de tipos en el combo box
        ObservableList<String> tipos = FXCollections.observableArrayList("Alumno", "Profesor");
        // Asignar la lista de tipos al combo box
        cmbTipo.setItems(tipos);
        // Seleccionar el primer tipo por defecto
        cmbTipo.getSelectionModel().selectFirst();
        // Habilitar panel de datos del alumno por defecto
        pnlDatosAlumno.setDisable(false);
        // Configurar acción al cambiar el tipo de persona
        cmbTipo.setOnAction(event ->{
            String selection = cmbTipo.getValue();
            // Habilitar o deshabilitar paneles según el tipo seleccionado
            if("Alumno".equals(selection)){
                pnlDatosAlumno.setDisable(false);
                pnlDatosProfesor.setDisable(true);
            }else if("Profesor".equals(selection)){
                pnlDatosAlumno.setDisable(true);
                pnlDatosProfesor.setDisable(false);
            }else{
                pnlDatosAlumno.setDisable(true);
                pnlDatosProfesor.setDisable(true);
            }
        });
    }

    @FXML
    public void guardar(){
        System.out.println(txtRun.getText());
        System.out.println(txtNombres.getText());

    }

    @FXML 
    public void eliminar(){
        lblMessage.setText("");
        // Obtener elementos seleccionados en la tabla
        List<PersonaDTO> personasSeleccionadas = tblPersonas.getSelectionModel().getSelectedItems();
        // Validando que se haya seleccionado elementos de la tabla para borrar 
        if(personasSeleccionadas.isEmpty()){
            System.err.println("No se ha seleccionado ninguna persona.");
            // Mostrar mensaje de error en la etiqueta
            lblMessage.setText("No se ha seleccionado ninguna persona.");
        }else {
            try{
                // Eliminar personas seleccionadas del servicio y actualizar la tabla (data.json)
                personaService.eliminar(personasSeleccionadas);
                // Eliminar personas seleccionadas de la tabla observable
                personasObservable.removeAll(personasSeleccionadas);
                // Refrescar la tabla para reflejar los cambios
                tblPersonas.refresh();
            }catch(Exception e){
                System.err.println("Error al eliminar las personas: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
