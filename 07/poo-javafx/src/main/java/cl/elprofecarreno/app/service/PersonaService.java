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
        // Construir objeto PersonaDAO
        this.personaDAO = new PersonaDAO();
    }

    public List<PersonaDTO> getPersonas(){
        // Crear lista vacía de personaDTO
        List<PersonaDTO> personas = new ArrayList<>();

        try{
            // Buscar personas entities en data.json
            List<Persona> personasEntity = personaDAO.findAll();
            // Transformar todas las personas entities en personas dtos
            for(Persona persona : personasEntity){
                personas.add(toDto(persona));
            }
        }catch(DAOException e){
            e.printStackTrace();
        }
        // Retornar listado de personas dtos
        return personas;

    }

    public PersonaDTO toDto(Persona persona){
        // Crear objeto persona dto a partir de n objeto persona entity
        return PersonaDTO.builder()
                .runDv(persona.getRun() + "-" + persona.getDv())
                .nombreCompleto(persona.getNombres() + " " +persona.getApellidoPaterno() + " " + persona.getApellidoMaterno())
                .fechaNacimiento(persona.getFechaNacimiento())
                .comuna(persona.getDireccion().getComuna())
                .region(persona.getDireccion().getRegion())
                .direccionCompleto(persona.getDireccion().getCalle() + " " + persona.getDireccion().getNumero())
                .build();
    }
}
