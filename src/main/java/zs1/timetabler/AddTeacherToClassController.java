package zs1.timetabler;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class AddTeacherToClassController {
    @FXML
    ChoiceBox<Teacher> teacherChoice;
    @FXML
    ChoiceBox<Class> classChoice;
    @FXML void initialize() {
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql = "FROM zs1.timetabler.Teacher tch";
            Query query = session.createQuery(hql);
            List<Teacher> teachers = query.getResultList();
            teacherChoice.getItems().addAll(teachers);
            String hql2 = "FROM zs1.timetabler.Class cls";
            Query qur2 = session.createQuery(hql2);
            List<Class> classes = qur2.getResultList();
            classChoice.getItems().addAll(classes);
        }
    }
    @FXML void submit() {
        Class theClass = classChoice.getSelectionModel().getSelectedItem();
        theClass.setTeacher(teacherChoice.getSelectionModel().getSelectedItem());
        //add record
        SessionFactory sf = DatabaseLink.setup();
        sf.inTransaction(session -> session.update(theClass));
        cancel();
    }
    @FXML void cancel() {
        Stage stage = (Stage) classChoice.getScene().getWindow();
        stage.close();
    }
}
