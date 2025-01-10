package zs1.timetabler;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class AddStudentController {
    @FXML
    TextField firstName;
    @FXML
    TextField surname;
    @FXML
    TextField year;
    int toAdd;

    public void loadInCurrentValues(int toAdd) {
        this.toAdd = toAdd;
        try (Session session = DatabaseLink.setup().openSession()) {
            System.out.println(toAdd);
            String hql = "FROM zs1.timetabler.Student st WHERE st.student_id = :sid";
            Query query = session.createQuery(hql);
            query.setParameter("sid", toAdd);
            Student theStudent = (Student) query.getSingleResult();
            if (theStudent.getFirst_name() != null) {
                firstName.setText(theStudent.getFirst_name());
            }
            if (theStudent.getSurname() != null) {
                surname.setText(theStudent.getSurname());
            }
            if (theStudent.getYear() != 0) {
                year.setText(String.valueOf(theStudent.getYear()));
            }
        }
    }

    @FXML void submit() {
        //validate year
        boolean isDigits = true;
        for (Character each : year.getText().toCharArray()) {
            if (!Character.isDigit(each)) {
                isDigits = false;
                break;
            }
        }
        if (!isDigits) {
            return;
        }
        int yearInt = Integer.parseInt(year.getText());
        //add record
        try (Session session = DatabaseLink.setup().openSession()) {
            System.out.println(toAdd);
            String hql = "FROM zs1.timetabler.Student st WHERE st.student_id = :sid";
            Query query = session.createQuery(hql);
            query.setParameter("sid", toAdd);
            Student toAddStudent = (Student) query.getSingleResult();
            SessionFactory sf = DatabaseLink.setup();
            toAddStudent.setFirst_name(firstName.getText());
            toAddStudent.setSurname(surname.getText());
            toAddStudent.setYear(yearInt);
            sf.inTransaction(session1 -> session1.update(toAddStudent));
            close();
        }
    }

    private void close() {
        Stage stage = (Stage) firstName.getScene().getWindow();
        stage.close();
    }

    @FXML public void cancel() {
        try (Session session = DatabaseLink.setup().openSession()) {
            System.out.println(toAdd);
            String hql = "FROM zs1.timetabler.Student st WHERE st.student_id = :sid";
            Query query = session.createQuery(hql);
            query.setParameter("sid", toAdd);
            Student toAddStudent = (Student) query.getSingleResult();
            SessionFactory sf = DatabaseLink.setup();
            sf.inTransaction(session1 -> session1.delete(toAddStudent));
            close();
        }
    }
}
