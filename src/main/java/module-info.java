module com.example.tipcalculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tipcalculator to javafx.fxml;
    exports com.example.tipcalculator;
}