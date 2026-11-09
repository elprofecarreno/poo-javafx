package cl.elprofecarreno.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionOracleOnPrimesDB {
   
    private static ConnectionOracleOnPrimesDB instancia;
    private final static String URL = "jdbc:oracle:thin:@localhost:1522:xe";
    private final static String USER = "system";
    private final static String PASSWORD = "system";
    private Connection connection;

    private ConnectionOracleOnPrimesDB() throws SQLException {
        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connection established successfully.");
    }

    public static ConnectionOracleOnPrimesDB getInstancia() throws SQLException {
        if (instancia == null) {
            instancia = new ConnectionOracleOnPrimesDB();
        }
        return instancia;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
