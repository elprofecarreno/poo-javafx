package cl.elprofecarreno.app;

import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        // Cambiar a la escena primaria
        App.setRoot("primary");
    }
}