package cl.elprofecarreno.app.service;
import cl.elprofecarreno.app.controller.dto.UserDTO;
import cl.elprofecarreno.app.dao.UserDAO;
import cl.elprofecarreno.app.dao.entity.User;
import cl.elprofecarreno.app.dao.excepcion.DAOException;

public class LoginService {
    
    private UserDAO userDAO;

    public LoginService() {
        this.userDAO = new UserDAO();
    }

    public UserDTO authenticate(String username, String password) throws DAOException {
        // Validar usuario y contraseña con almacen de datos (authentication.json)
        // En caso de obtener error se propaga a la capa superior
        User user = userDAO.authenticate(username, password);
        // retorna objeto UserDTO para ello se transforma el objeto User (Entity)
        return UserDTO.builder()
                      .username(user.getUsername())
                      .build();
    }
}
