module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.xml.bind;

    opens org.example to javafx.fxml;
    opens org.example.Entity to javafx.base;
    opens org.example.View to javafx.fxml, java.xml.bind;
    opens org.example.Controllers to java.xml.bind;

    exports org.example;
    exports org.example.View;
}

