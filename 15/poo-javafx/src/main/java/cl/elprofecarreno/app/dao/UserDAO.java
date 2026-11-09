package cl.elprofecarreno.app.dao;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;

import cl.elprofecarreno.app.dao.entity.User;
import cl.elprofecarreno.app.dao.excepcion.DAOException;

public class UserDAO {
    

    public User authenticate(String username, String password) throws DAOException {
        // Obtener objeto User desde authentication.json
        User user = readUser(username, password);
        // Validar usuario de no estar correcto se propaga excepción a la capa de service
        if(user == null || user.getUsername().equals(user) || !user.getPassword().equals(password)) {
            throw new DAOException("ERROR: login fallido. Usuario o contraseña incorrectos");
        }

        if(!user.getUsername().equals(username) || !user.getPassword().equals(password)){
            throw new DAOException("ERROR: login fallido. Usuario o contraseña incorrectos");
        }
        // Si todas la validaciones son correctar se retorn el objeto User
        return user;
    }

    public User readUser(String username, String password) throws DAOException {
	// Obtener rut del archivo
        URL url = getClass().getResource("/authentication.json");
        // Validar que la ruta exista
        if(url == null){
            throw new DAOException("ERROR: login fallido. Archivo authentication.json");
        }

        try{
            // Construir ruta del archivo
            Path path = Path.of(url.toURI());
            // Leer archivo para autenticar en formato español latinoamericano
            String json = Files.readString(path, StandardCharsets.UTF_8);
            // Construir objeto gson para transformar texto (athentication.json) en objetos java
            Gson gson = new Gson();
            // Construir objeto java User con la información del usuario y la password
            User user = gson.fromJson(json, User.class);
            // Retornar objeto usuario
            return user;
        } catch (Exception e) {
            throw new DAOException("ERROR: login fallido. No se pudo leer el archivo authentication.json", e);
        }
    }
}
