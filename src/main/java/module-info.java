module br.com.lisa.libfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;
    requires java.naming;

    opens br.com.lisa.libfx to javafx.fxml;
    exports br.com.lisa.libfx;
}