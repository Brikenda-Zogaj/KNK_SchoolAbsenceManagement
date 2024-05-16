module org.example.projekti {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;
    requires javafx.base;


    opens app;
    opens org.example.knk_schoolabsencemanagement_gr25 to javafx.fxml;

    opens controller to javafx.fxml;
    exports org.example.knk_schoolabsencemanagement_gr25;
    exports app to javafx.graphics;
    exports controller;
}