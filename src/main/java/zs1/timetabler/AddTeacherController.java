package zs1.timetabler;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.w3c.dom.Text;

public class AddTeacherController {
    @FXML
    TextField firstName;
    @FXML
    TextField surname;
    int toAdd;

    public void loadInCurrentValues(int toAdd) {
        this.toAdd = toAdd;
        firstName.setText("First name");
        surname.setText("Surname");
        if (toAdd != -1) {
            try (Session session = DatabaseLink.setup().openSession()) {
                System.out.println(toAdd);
                String hql = "FROM zs1.timetabler.Teacher st WHERE st.teacher_id = :sid";
                Query query = session.createQuery(hql);
                query.setParameter("sid", toAdd);
                Teacher theStudent = (Teacher) query.getSingleResult();
                if (theStudent.getFirst_name() != null) {
                    firstName.setText(theStudent.getFirst_name());
                }
                if (theStudent.getSurname() != null) {
                    surname.setText(theStudent.getSurname());
                }
            }
        }
        firstName.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                firstName.clear();
            }
        });
        surname.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                surname.clear();
            }
        });
    }

    @FXML
    void submit() {
        //add record
        if (toAdd != -1) {
            try (Session session = DatabaseLink.setup().openSession()) {
                System.out.println(toAdd);
                String hql = "FROM zs1.timetabler.Teacher st WHERE st.teacher_id = :sid";
                Query query = session.createQuery(hql);
                query.setParameter("sid", toAdd);
                Teacher toAddTeacher = (Teacher) query.getSingleResult();
                SessionFactory sf = DatabaseLink.setup();
                toAddTeacher.setFirst_name(firstName.getText());
                toAddTeacher.setSurname(surname.getText());
                sf.inTransaction(session1 -> session1.update(toAddTeacher));
            }
        }
        else {
            Teacher newTeacher = new Teacher();
            SessionFactory sf = DatabaseLink.setup();
            newTeacher.setFirst_name(firstName.getText());
            newTeacher.setSurname(surname.getText());
            sf.inTransaction(session1 -> session1.persist(newTeacher));
        }
        close();
    }

    @FXML void cancel() {
//        try (Session session = DatabaseLink.setup().openSession()) {
//            System.out.println(toAdd);
//            String hql = "FROM zs1.timetabler.Teacher st WHERE st.teacher_id = :sid";
//            Query query = session.createQuery(hql);
//            query.setParameter("sid", toAdd);
//            Teacher toAddTeacher = (Teacher) query.getSingleResult();
//            SessionFactory sf = DatabaseLink.setup();
//            sf.inTransaction(session1 -> session1.delete(toAddTeacher));
//            close();
//        }
        close();
    }

    private void close() {
        Stage stage = (Stage) firstName.getScene().getWindow();
        stage.close();
    }
}
