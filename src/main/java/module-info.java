module org.example.projekti {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;
    requires javafx.base;
    requires mysql.connector.j;

    opens app to javafx.fxml;
    opens controller to javafx.fxml;
    opens Classes to javafx.fxml;
    opens database to javafx.fxml;

    exports app;
    exports Classes;
    exports controller;
    exports database;
}
