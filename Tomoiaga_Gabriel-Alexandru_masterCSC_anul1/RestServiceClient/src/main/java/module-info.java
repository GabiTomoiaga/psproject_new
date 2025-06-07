module com.example.restserviceclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.net.http;
    requires java.desktop;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    // permite accesul reflectiv pentru controlleri FXML
    opens com.example.restserviceclient.view to javafx.fxml;

    // adaugă-l pentru ca SceneLoader să poată accesa clasele din view
    exports com.example.restserviceclient.view;

    // deja exportai pachetul principal
    exports com.example.restserviceclient;
}
