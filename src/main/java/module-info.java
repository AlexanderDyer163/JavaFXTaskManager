module com.example.tmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.groovy;

    requires org.controlsfx.controls;

    opens com.example.tmanager to javafx.fxml;
    exports com.example.tmanager;
}