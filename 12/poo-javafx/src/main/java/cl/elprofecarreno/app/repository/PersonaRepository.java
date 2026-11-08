package cl.elprofecarreno.app.repository;

import cl.elprofecarreno.app.dao.IDAO;
import cl.elprofecarreno.app.dao.entity.Alumno;
import cl.elprofecarreno.app.dao.entity.Persona;
import cl.elprofecarreno.app.dao.entity.Profesor;
import cl.elprofecarreno.app.dao.excepcion.DAOException;

import java.util.ArrayList;
import java.util.List;

import cl.elprofecarreno.app.dao.AlumnoDAO;
import cl.elprofecarreno.app.dao.ProfesorDAO;

public class PersonaRepository {
    
    private IDAO<Profesor> profesorDAO;
    private IDAO<Alumno> alumnoDAO;

    public PersonaRepository(){
        // Inicializar los DAOs de profesor y alumno
        this.profesorDAO = new ProfesorDAO();
        this.alumnoDAO = new AlumnoDAO();
    }

    public List<Persona> findAll() throws DAOException {
        // Crear una lista para almacenar todas las personas (profesores y alumnos)
        List <Persona> personas = new ArrayList<>();
        // Buscar todos los profesores y alumnos y agregarlos a la lista de personas
        personas.addAll(profesorDAO.findAll());
        personas.addAll(alumnoDAO.findAll());
        // Retornar la lista completa de personas
        return personas;
    }

    public void delete(List<Persona> persona) throws DAOException {
        //Listas para almacenar los profesores y alumnos que se van a eliminar
        List<Profesor> deleteProfesores = new ArrayList<>();
        List<Alumno> deleteAlumnos = new ArrayList<>();
        for (Persona p : persona) {
            // Determinar si la persona es un profesor o un alumno y agregarla a la lista correspondiente
            if (p instanceof Profesor) {
                deleteProfesores.add((Profesor) p);
            } else if (p instanceof Alumno) {
                deleteAlumnos.add((Alumno) p);
            }
        }
        // Eliminar los profesores y alumnos de sus respectivas listas si no están vacías
        if (!deleteProfesores.isEmpty()) {
            profesorDAO.delete(deleteProfesores);
        }
        if (!deleteAlumnos.isEmpty()) {
            alumnoDAO.delete(deleteAlumnos);
        }
    }

    public void save(Persona persona) throws DAOException {
        // Determinar si la persona es un profesor o un alumno y guardarla en el DAO correspondiente
        if (persona instanceof Profesor) {
            profesorDAO.save((Profesor) persona);
        } else if (persona instanceof Alumno) {
            alumnoDAO.save((Alumno) persona);
        }
    }

}
