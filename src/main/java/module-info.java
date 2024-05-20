module org.example.projekti {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;
    requires javafx.base;


    opens app to javafx.fxml;
    exports app;
    exports controller;
    opens controller;
}