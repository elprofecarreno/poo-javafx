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
