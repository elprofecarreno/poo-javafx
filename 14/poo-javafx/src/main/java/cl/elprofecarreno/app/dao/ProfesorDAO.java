package cl.elprofecarreno.app.dao;
import cl.elprofecarreno.app.dao.entity.Profesor;
import cl.elprofecarreno.app.dao.excepcion.DAOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProfesorDAO implements IDAO<Profesor> {

    private static final String FILE = "/profesor.json";

    @Override
    public List<Profesor> findAll() throws DAOException {
        // Construir objeto gson para transformar texto (athentication.json) en objetos java
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        // Linea para leer archivo de texto plano en formato .json
        try(Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))){
            // Creando estructura de tipo Lista de Profesor
            Type listType = new TypeToken<ArrayList<Profesor>>(){}.getType();
            // Transfomando el contenido JSON (texto) en una estructura de tipo Lista de Profesor
            return gson.fromJson(reader, listType);
        }catch(Exception e){
            throw new DAOException("ERROR al leer data.json", e);
        }
    }

    @Override
    public void delete(List<Profesor> profesores) throws DAOException  {
        // Construir objeto gson para transformar texto (athentication.json) en objetos java
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();
        // Linea para leer archivo de texto plano en formato .json
        try(Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))){
            // Creando estructura de tipo Lista de Profesor
            Type listType = new TypeToken<ArrayList<Profesor>>(){}.getType();
            // Transfomando el contenido JSON (texto) en una estructura de tipo Lista de Profesor
            List<Profesor> data = gson.fromJson(reader, listType);
            //Eliminar de la lista obtenida del archivo json (data.json) los elementos seleccionados
            //en el contralodor
            System.out.println("Profesores a eliminar: " + profesores.size());
            System.out.println("Profesores en el archivo antes de eliminar: " + data.size());
            List<Profesor> profesoresAEliminar = new ArrayList<>();
            for(Profesor p : data){
                for(Profesor pE : profesores)
                if(p.getRun() == pE.getRun()){
                    profesoresAEliminar.add(p);
                }
            }
            data.removeAll(profesoresAEliminar);
            System.out.println("Profesores en el archivo después de eliminar: " + data.size());
            // Reescribir archivo con la información actual del listado data
            try (java.io.Writer writer = new java.io.FileWriter(getClass().getResource(FILE).getFile())) {
                gson.toJson(data, writer);
            }
        }catch(Exception e){
            throw new DAOException("ERROR al leer data.json", e);
        }
    }

    @Override
    public void save(Profesor profesor) throws DAOException  {
        // Construir objeto gson para transformar texto (athentication.json) en objetos java
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();;
        // Linea para leer archivo de texto plano en formato .json
        try(Reader reader = new InputStreamReader(getClass().getResourceAsStream(FILE))){
            // Creando estructura de tipo Lista de Profesor
            Type listType = new TypeToken<ArrayList<Profesor>>(){}.getType();
            // Transfomando el contenido JSON (texto) en una estructura de tipo Lista de Profesor
            List<Profesor> data = gson.fromJson(reader, listType);
            for(Profesor p : data){
                if (p.getRun() == profesor.getRun()){
                    throw new DAOException("Profesor con RUN " + profesor.getRun() + " ya existe");
                }
            }
            data.add(profesor
            );
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
