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

    opens com.example.restserviceclient to javafx.fxml;
    exports com.example.restserviceclient;
}