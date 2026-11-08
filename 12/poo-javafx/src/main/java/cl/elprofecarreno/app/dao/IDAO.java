package cl.elprofecarreno.app.dao;

import java.util.List;

import cl.elprofecarreno.app.dao.excepcion.DAOException;


public interface IDAO<T> {
    
    // Obtener listado de registros desde un json o una tabla de la base de datos.
    public List<T> findAll() throws DAOException ;

    // Eliminar registros existentes de un json o una tabla de la base de datos.
    public void delete(List<T> personas) throws DAOException ;

    // Guardar un nuevo registro en un json o una tabla de la base de datos.
    public void save(T persona) throws DAOException ;
}
