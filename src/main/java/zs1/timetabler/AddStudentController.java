package zs1.timetabler;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

public class AddStudentController {
    @FXML
    TextField firstName;
    @FXML
    TextField surname;
    @FXML
    TextField year;
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
        SessionFactory sf = DatabaseLink.setup();
        sf.inTransaction(session -> session.persist(new Student(firstName.getText(), surname.getText(), yearInt)));
        cancel();
    }
    @FXML void cancel() {
        Stage stage = (Stage) firstName.getScene().getWindow();
        stage.close();
    }
}
