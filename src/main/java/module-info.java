module org.example.animetp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.animetp to javafx.fxml;
    exports org.example.animetp;
}