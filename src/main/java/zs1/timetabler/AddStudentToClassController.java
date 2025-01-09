package zs1.timetabler;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AddStudentToClassController {
    @FXML
    ChoiceBox<Student> studentChoice;
    @FXML
    ChoiceBox<Class> classChoice;
    @FXML void initialize() {
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql = "FROM zs1.timetabler.Student std";
            Query query = session.createQuery(hql);
            List<zs1.timetabler.Student> students = query.getResultList();
            studentChoice.getItems().addAll(students);
            String hql2 = "FROM zs1.timetabler.Class cls";
            Query qur2 = session.createQuery(hql2);
            List<Class> classes = qur2.getResultList();
            classChoice.getItems().addAll(classes);
        }
    }
    @FXML void submit() {
        //add record
        SessionFactory sf = DatabaseLink.setup();
        sf.inTransaction(session -> session.persist(new ClassStudent(classChoice.getSelectionModel().getSelectedItem(), studentChoice.getSelectionModel().getSelectedItem())));
        cancel();
    }
    @FXML void cancel() {
        Stage stage = (Stage) studentChoice.getScene().getWindow();
        stage.close();
    }
}
