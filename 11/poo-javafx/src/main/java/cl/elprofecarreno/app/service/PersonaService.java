package cl.elprofecarreno.app.service;

import cl.elprofecarreno.app.dao.PersonaDAO;
import cl.elprofecarreno.app.dao.excepcion.DAOException;
import cl.elprofecarreno.app.controller.dto.AlumnoDTO;
import cl.elprofecarreno.app.controller.dto.PersonaDTO;
import java.util.List;
import java.util.ArrayList;

import cl.elprofecarreno.app.dao.entity.Direccion;
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
        return AlumnoDTO.builder()
                .runDv(persona.getRun() + "-" + persona.getDv())
                .nombreCompleto(persona.getNombres() + " " +persona.getApellidoPaterno() + " " + persona.getApellidoMaterno())
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
        // Creando lista de Persona Entity
        List<Persona> personasEntity = new ArrayList<>();
        // Transformando cada PersonaDTO en Persona Entity
        for(PersonaDTO personaDTO : personas){
            // Agregando Persona Entity a la lista
            personasEntity.add(toEntity(personaDTO));
        }
        // Llamando al método eliminar del DAO
        personaDAO.delete(personasEntity);
    }

    public Persona toEntity(PersonaDTO personaDTO){
        // Encargado de transformar un PersonaDTO en Persona Entity
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

    public void save(PersonaDTO personaDTO) throws DAOException{
        personaDAO.save(toEntity(personaDTO));
    }
}
