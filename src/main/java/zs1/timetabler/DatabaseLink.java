package zs1.timetabler;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import static org.hibernate.cfg.JdbcSettings.*;

public class DatabaseLink {
    /**
     * the session factory currently open
     */
    private static SessionFactory sessionFactory;

    /**
     * Starts the session factory or returns the active one
     * @return a sessionFactory
     */
    public static SessionFactory setup() {
        if (sessionFactory == null) {
            try {
                java.lang.Class.forName("org.postgresql.Driver");
                sessionFactory = new Configuration()
                        .addAnnotatedClass(Class.class)
                        .addAnnotatedClass(ClassStudent.class)
                        .addAnnotatedClass(Student.class)
                        .addAnnotatedClass(Teacher.class)
                        .setProperty(JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5435/timetabler")
                        .setProperty(JAKARTA_JDBC_USER, "zs1")
                        .setProperty(JAKARTA_JDBC_PASSWORD, "password")
                        .setProperty(SHOW_SQL, "false")
                        .setProperty(LOG_JDBC_WARNINGS, "false")
                        .buildSessionFactory();

                sessionFactory.getSchemaManager().exportMappedObjects(true);
            } catch (ClassNotFoundException e) {
                System.out.println("Error: " + e.toString());
            }
            catch (org.hibernate.service.spi.ServiceException e) {
                System.out.println("Could not connect to the postgreSQL port. Please check it is running and try again.");
                System.exit(1);
            }
        }
        return sessionFactory;
    }
}
