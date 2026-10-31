package cl.elprofecarreno.app;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        // Cambiar a la escena secundaria
        App.setRoot("secondary");
    }
}
