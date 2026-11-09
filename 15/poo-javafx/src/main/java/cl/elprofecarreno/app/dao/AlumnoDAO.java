package cl.elprofecarreno.app.dao;
import cl.elprofecarreno.app.dao.entity.Alumno;
import cl.elprofecarreno.app.dao.excepcion.DAOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AlumnoDAO implements IDAO<Alumno> {
    
    private static final String FILE = "/alumno.json";

    @Override
    public List<Alumno> findAll() throws DAOException {
        // Construir objeto gson para transformar texto (athentication.json) en objetos java
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        // Linea para leer archivo de texto plano en formato .json
        try(Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))){
            // Creando estructura de tipo Lista de Alumno
            Type listType = new TypeToken<ArrayList<Alumno>>(){}.getType();
            // Transfomando el contenido JSON (texto) en una estructura de tipo Lista de Alumno
            return gson.fromJson(reader, listType);
        }catch(Exception e){
            throw new DAOException("ERROR al leer data.json", e);
        }
    }

    @Override
    public void delete(List<Alumno> alumnos) throws DAOException {
        // Construir objeto gson para transformar texto (athentication.json) en objetos java
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();
        // Linea para leer archivo de texto plano en formato .json
        try(Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))){
            // Creando estructura de tipo Lista de Alumno
            Type listType = new TypeToken<ArrayList<Alumno>>(){}.getType();
            // Transfomando el contenido JSON (texto) en una estructura de tipo Lista de Alumno
            List<Alumno> data = gson.fromJson(reader, listType);
            //Eliminar de la lista obtenida del archivo json (data.json) los elementos seleccionados
            //en el contralodor
            System.out.println("Alumnos a eliminar: " + alumnos.size());
            System.out.println("Alumnos en el archivo antes de eliminar: " + data.size());
            List<Alumno> alumnosAEliminar = new ArrayList<>();
            for(Alumno p : data){
                for(Alumno pE : alumnos)
                if(p.getRun() == pE.getRun()){
                    alumnosAEliminar.add(p);
                }
            }
            data.removeAll(alumnosAEliminar);
            System.out.println("Alumnos en el archivo después de eliminar: " + data.size());
            // Reescribir archivo con la información actual del listado data
            try (java.io.Writer writer = new java.io.FileWriter(getClass().getResource(FILE).getFile())) {
                gson.toJson(data, writer);
            }
        }catch(Exception e){
            throw new DAOException("ERROR al leer data.json", e);
        }
    }

    @Override
    public void save(Alumno alumno) throws DAOException {
        // Construir objeto gson para transformar texto (athentication.json) en objetos java
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();;
        // Linea para leer archivo de texto plano en formato .json
        try(Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))){
            // Creando estructura de tipo Lista de Alumno
            Type listType = new TypeToken<ArrayList<Alumno>>(){}.getType();
            // Transfomando el contenido JSON (texto) en una estructura de tipo Lista de Alumno
            List<Alumno> data = gson.fromJson(reader, listType);
            for(Alumno p : data){
                if (p.getRun() == alumno.getRun()){
                    throw new DAOException("Alumno con RUN " + alumno.getRun() + " ya existe");
                }
            }
            data.add(alumno);
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
