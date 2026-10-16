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
        User user = readUser(username, password);

        if(user == null || user.getUsername().equals(user) || !user.getPassword().equals(password)) {
            throw new DAOException("ERROR: login fallido. Usuario o contraseña incorrectos");
        }

        if(!user.getUsername().equals(username) || !user.getPassword().equals(password)){
            throw new DAOException("ERROR: login fallido. Usuario o contraseña incorrectos");
        }

        return user;
    }

    public User readUser(String username, String password) throws DAOException {

        URL url = getClass().getResource("/authentication.json");
        if(url == null){
            throw new DAOException("ERROR: login fallido. Archivo authentication.json");
        }

        try{
            Path path = Path.of(url.toURI());
            String json = Files.readString(path, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            User user = gson.fromJson(json, User.class);
            return user;
        } catch (Exception e) {
            throw new DAOException("ERROR: login fallido. No se pudo leer el archivo authentication.json", e);
        }
    }
}
