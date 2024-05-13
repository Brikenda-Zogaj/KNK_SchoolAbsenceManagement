module org.example.knk_schoolabsencemanagement_gr25 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.knk_schoolabsencemanagement_gr25 to javafx.fxml;
    exports org.example.knk_schoolabsencemanagement_gr25;
}