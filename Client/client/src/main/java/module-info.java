module ca.cmpt213.asn5 {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens ca.cmpt213.asn5 to javafx.fxml;
    exports ca.cmpt213.asn5;
}
