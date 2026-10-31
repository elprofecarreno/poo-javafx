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
        // Inicializar el servicio de personas
        personaService = new PersonaService();
        // Inicializar la lista de personas observable
        List<PersonaDTO> personas = new ArrayList<>();

        // Configurar las columnas de la tabla con las propiedades de PersonaDTO (Mapeo columnas de la tabla)
        colRun.setCellValueFactory(new PropertyValueFactory<>("runDv"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccionCompleto"));
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
        tblPersonas.setItems(personasObservable);
    }

}
