module com.example.nasaimagesearch {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.net.http;
    requires com.google.gson;

    opens com.example.nasaimagesearch to javafx.fxml;
    opens com.example.nasaimagesearch.models to com.google.gson;
    exports com.example.nasaimagesearch;
}