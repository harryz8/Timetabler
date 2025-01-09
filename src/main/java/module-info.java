module zs1.timetabler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.persistence;
    requires jdk.jfr;
    requires java.rmi;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires jakarta.transaction;
    requires jakarta.cdi;
    requires org.jboss.logging;
    requires com.fasterxml.classmate;
    requires jakarta.xml.bind;
    requires org.hibernate.commons.annotations;
    requires org.postgresql.jdbc;
    requires net.bytebuddy;
    requires java.desktop;

    opens zs1.timetabler to javafx.fxml, org.hibernate.orm.core;
    exports zs1.timetabler;
}