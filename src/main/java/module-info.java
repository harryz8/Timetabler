module zs1.timetabler {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires org.postgresql.jdbc;
    requires jakarta.persistence;

    opens zs1.timetabler to javafx.fxml, org.hibernate.orm.core;
    exports zs1.timetabler;
}