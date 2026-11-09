package cl.elprofecarreno.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import cl.elprofecarreno.app.dao.ConnectionOracleCloudDB;

/**
 * JavaFX App
 */
public class App extends Application {

    private final static int WIDTH = 300;
    private final static int HEIGHT = 200;

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(getLoadFXML("/views/login"));
        // Configurar la escena inicial con el archivo FXML de login
        stage.setScene(scene);
        // Configurar el tamaño de la ventana inicial
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        // Centrar la ventana en la pantalla antes de mostrarla
        stage.centerOnScreen();
        stage.show();
    }

    public static void setRoot(Parent parent, int width, int height) throws IOException {
        scene.setRoot(parent);
        // Configurar el tamaño de la ventana inicial
        scene.getWindow().setWidth(width);
        scene.getWindow().setHeight(height);
        // Centrar la ventana en la pantalla antes de mostrarla
        scene.getWindow().centerOnScreen();
    }

    public static Parent getLoadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        try {
            ConnectionOracleCloudDB.getInstancia();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // Iniciar la aplicación JavaFX
        launch();
    }

}