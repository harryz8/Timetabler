package zs1.timetabler;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.w3c.dom.Text;

public class AddTeacherController {
    @FXML
    TextField firstName;
    @FXML
    TextField surname;
    @FXML
    void submit() {
        //add record
        SessionFactory sf = DatabaseLink.setup();
        sf.inTransaction(session -> session.persist(new Teacher(firstName.getText(), surname.getText())));
        cancel();
    }
    @FXML void cancel() {
        Stage stage = (Stage) firstName.getScene().getWindow();
        stage.close();
    }
}
