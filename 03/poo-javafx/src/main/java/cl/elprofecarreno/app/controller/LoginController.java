package cl.elprofecarreno.app.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private void login() throws IOException {
        System.out.println("USUARIO: " + txtUsuario.getText());
        System.out.println("PASSWORD: " + txtContrasenia.getText());
    }
}