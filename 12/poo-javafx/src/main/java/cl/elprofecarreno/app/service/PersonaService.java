package cl.elprofecarreno.app.service;

import cl.elprofecarreno.app.dao.PersonaDAO;
import cl.elprofecarreno.app.dao.excepcion.DAOException;
import cl.elprofecarreno.app.repository.PersonaRepository;
import cl.elprofecarreno.app.controller.dto.AlumnoDTO;
import cl.elprofecarreno.app.controller.dto.PersonaDTO;
import cl.elprofecarreno.app.controller.dto.ProfesorDTO;

import java.util.List;
import java.util.ArrayList;

import cl.elprofecarreno.app.dao.entity.Alumno;
import cl.elprofecarreno.app.dao.entity.Direccion;
import cl.elprofecarreno.app.dao.entity.Persona;
import cl.elprofecarreno.app.dao.entity.Profesor;

public class PersonaService {

    private PersonaRepository personaRepository;
    
    public PersonaService() {
        // Construir objeto PersonaRepository
        personaRepository = new PersonaRepository();
    }

    public List<PersonaDTO> getPersonas(){
        // Crear lista vacía de personaDTO
        List<PersonaDTO> personas = new ArrayList<>();

        try{
            // Buscar personas entities en data.json
            List<Persona> personasEntity = personaRepository.findAll();
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
        personaRepository.delete(personasEntity);
    }

    public Persona toEntity(PersonaDTO personaDTO){
        // Encargado de transformar un PersonaDTO en Persona Entity
        String[] run = personaDTO.getRunDv().split("-");
        Persona persona = null;
        if(personaDTO instanceof AlumnoDTO){
            AlumnoDTO alumnoDTO = (AlumnoDTO) personaDTO;
            persona = Alumno.builder()
                        .run(Integer.valueOf(run[0]))
                            .dv(run[1])
                            .nombres(alumnoDTO.getNombres())
                            .apellidoPaterno(alumnoDTO.getApellidoPaterno())
                            .apellidoMaterno(alumnoDTO.getApellidoMaterno())
                            .fechaNacimiento(alumnoDTO.getFechaNacimiento())
                            .direccion(
                                Direccion.builder()
                                    .calle(alumnoDTO.getCalle())
                                    .numero(alumnoDTO.getNumero())
                                    .comuna(alumnoDTO.getComuna())
                                    .region(alumnoDTO.getRegion())
                                    .build()
                            )
                            .semestre(alumnoDTO.getSemestre())
                            .carrera(alumnoDTO.getCarrera())
                            .build();
        }else if(personaDTO instanceof ProfesorDTO){
            ProfesorDTO profesorDTO = (ProfesorDTO) personaDTO;
            persona = Profesor.builder()
                        .run(Integer.valueOf(run[0]))
                            .dv(run[1])
                            .nombres(profesorDTO.getNombres())
                            .apellidoPaterno(profesorDTO.getApellidoPaterno())
                            .apellidoMaterno(profesorDTO.getApellidoMaterno())
                            .fechaNacimiento(profesorDTO.getFechaNacimiento())
                            .direccion(
                                Direccion.builder()
                                    .calle(profesorDTO.getCalle())
                                    .numero(profesorDTO.getNumero())
                                    .comuna(profesorDTO.getComuna())
                                    .region(profesorDTO.getRegion())
                                    .build()
                            )
                            .jornada(profesorDTO.getJornada())
                            .sueldoBruto(profesorDTO.getSueldoBruto())
                            .build();
        }
        return persona;
    }

    public void save(PersonaDTO personaDTO) throws DAOException{
        personaRepository.save(toEntity(personaDTO));
    }
}
