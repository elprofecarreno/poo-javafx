module cl.elprofecarreno.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens cl.elprofecarreno.app to javafx.fxml;
    opens cl.elprofecarreno.app.controller to javafx.fxml;

    exports cl.elprofecarreno.app;
}
