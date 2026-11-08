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

    public List<Persona> findAll()  throws DAOException {
        // Construir objeto gson para transformar texto (athentication.json) en objetos java
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        // Linea para leer archivo de texto plano en formato .json
        try(Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))){
            // Creando estructura de tipo Lista de Persona
            Type listType = new TypeToken<ArrayList<Persona>>(){}.getType();
            // Transfomando el contenido JSON (texto) en una estructura de tipo Lista de Persona
            return gson.fromJson(reader, listType);
        }catch(Exception e){
            throw new DAOException("ERROR al leer data.json", e);
        }
    }

    public void delete(List<Persona> personas) throws DAOException {
        // Construir objeto gson para transformar texto (athentication.json) en objetos java
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();
        // Linea para leer archivo de texto plano en formato .json
        try(Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))){
            // Creando estructura de tipo Lista de Persona
            Type listType = new TypeToken<ArrayList<Persona>>(){}.getType();
            // Transfomando el contenido JSON (texto) en una estructura de tipo Lista de Persona
            List<Persona> data = gson.fromJson(reader, listType);
            //Eliminar de la lista obtenida del archivo json (data.json) los elementos seleccionados
            //en el contralodor
            System.out.println("Personas a eliminar: " + personas.size());
            System.out.println("Personas en el archivo antes de eliminar: " + data.size());
            List<Persona> personasAEliminar = new ArrayList<>();
            for(Persona p : data){
                for(Persona pE : personas)
                if(p.getRun() == pE.getRun()){
                    personasAEliminar.add(p);
                }
            }
            data.removeAll(personasAEliminar);
            System.out.println("Personas en el archivo después de eliminar: " + data.size());
            // Reescribir archivo con la información actual del listado data
            try (java.io.Writer writer = new java.io.FileWriter(getClass().getResource(FILE).getFile())) {
                gson.toJson(data, writer);
            }
        }catch(Exception e){
            throw new DAOException("ERROR al leer data.json", e);
        }
    }

    public void save(Persona persona) throws DAOException {
        // Construir objeto gson para transformar texto (athentication.json) en objetos java
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();;
        // Linea para leer archivo de texto plano en formato .json
        try(Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))){
            // Creando estructura de tipo Lista de Persona
            Type listType = new TypeToken<ArrayList<Persona>>(){}.getType();
            // Transfomando el contenido JSON (texto) en una estructura de tipo Lista de Persona
            List<Persona> data = gson.fromJson(reader, listType);
            for(Persona p : data){
                if (p.getRun() == persona.getRun()){
                    throw new DAOException("Persona con RUN " + persona.getRun() + " ya existe");
                }
            }
            data.add(persona);
            // Reescribir archivo con la información actual del listado data
            try (java.io.Writer writer = new java.io.FileWriter(getClass().getResource(FILE).getFile())) {
                gson.toJson(data, writer);
            }
        } catch (DAOException e){
            throw e;
        }catch(Exception e){
            throw new DAOException("ERROR al leer data.json", e);
        }
    }

}
