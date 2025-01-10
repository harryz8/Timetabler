package zs1.timetabler;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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

    @FXML
    void submit() {
        //add record
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
            close();
        }
    }

    @FXML void cancel() {
        try (Session session = DatabaseLink.setup().openSession()) {
            System.out.println(toAdd);
            String hql = "FROM zs1.timetabler.Teacher st WHERE st.teacher_id = :sid";
            Query query = session.createQuery(hql);
            query.setParameter("sid", toAdd);
            Teacher toAddTeacher = (Teacher) query.getSingleResult();
            SessionFactory sf = DatabaseLink.setup();
            sf.inTransaction(session1 -> session1.delete(toAddTeacher));
            close();
        }
    }

    private void close() {
        Stage stage = (Stage) firstName.getScene().getWindow();
        stage.close();
    }
}
