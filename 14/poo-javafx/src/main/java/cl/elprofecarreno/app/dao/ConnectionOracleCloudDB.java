package cl.elprofecarreno.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionOracleCloudDB {
    
    private static ConnectionOracleCloudDB instancia;
    private final static String URL = "jdbc:oracle:thin:@oracledb_high?TNS_ADMIN=/home/ccarreno/Downloads/Wallet_oracledb/";
    private final static String USER = "admin";
    private final static String PASSWORD = "Oracle.DB.2026";
    private Connection connection;

    private ConnectionOracleCloudDB() throws SQLException {
        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connection established successfully.");
    }

    public static ConnectionOracleCloudDB getInstancia() throws SQLException {
        if (instancia == null) {
            instancia = new ConnectionOracleCloudDB();
        }
        return instancia;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
