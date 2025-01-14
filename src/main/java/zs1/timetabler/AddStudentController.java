package zs1.timetabler;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
        firstName.setText("First name");
        surname.setText("Surname");
        year.setText("Year group");
        if (toAdd != -1) {
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
        year.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                year.clear();
            }
        });
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
        if (toAdd != -1) {
            try (Session session = DatabaseLink.setup().openSession()) {
                String hql = "FROM zs1.timetabler.Student st WHERE st.student_id = :sid";
                Query query = session.createQuery(hql);
                query.setParameter("sid", toAdd);
                Student toAddStudent = (Student) query.getSingleResult();
                SessionFactory sf = DatabaseLink.setup();
                toAddStudent.setFirst_name(firstName.getText());
                toAddStudent.setSurname(surname.getText());
                toAddStudent.setYear(yearInt);
                sf.inTransaction(session1 -> session1.update(toAddStudent));
            }
        }
        else {
            Student newStudent = new Student();
            SessionFactory sf = DatabaseLink.setup();
            newStudent.setFirst_name(firstName.getText());
            newStudent.setSurname(surname.getText());
            newStudent.setYear(yearInt);
            sf.inTransaction(session1 -> session1.persist(newStudent));
        }
        close();
    }

    private void close() {
        Stage stage = (Stage) firstName.getScene().getWindow();
        stage.close();
    }

    @FXML public void cancel() {
//        try (Session session = DatabaseLink.setup().openSession()) {
//            System.out.println(toAdd);
//            String hql = "FROM zs1.timetabler.Student st WHERE st.student_id = :sid";
//            Query query = session.createQuery(hql);
//            query.setParameter("sid", toAdd);
//            Student toAddStudent = (Student) query.getSingleResult();
//            SessionFactory sf = DatabaseLink.setup();
//            sf.inTransaction(session1 -> session1.delete(toAddStudent));
//            close();
//        }
        close();
    }
}
