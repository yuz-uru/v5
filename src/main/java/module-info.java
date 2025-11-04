module org.asuka.v5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens org.asuka.v5 to javafx.fxml;
    exports org.asuka.v5.frame to javafx.graphics;
}