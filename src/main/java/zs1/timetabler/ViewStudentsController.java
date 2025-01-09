package zs1.timetabler;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class ViewStudentsController {
    @FXML ListView<Student> studentsListView;
    @FXML Button deleteBt;

    @FXML public void initialize() {
        studentsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                deleteBt.setDisable(false);
            }
        });
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql = "FROM zs1.timetabler.Student std";
            Query query = session.createQuery(hql);
            List<Student> students = query.getResultList();
            studentsListView.getItems().addAll(students);
        }
    }

    @FXML public void close() {
        Stage stage = (Stage) studentsListView.getScene().getWindow();
        stage.close();
    }

    @FXML public void deleteSelected() {
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql2 = "FROM zs1.timetabler.ClassStudent cs WHERE cs.student=:stdnt";
            Query query2 = session.createQuery(hql2);
            query2.setParameter("stdnt", studentsListView.getSelectionModel().getSelectedItem());
            ClassStudent toDelete = (ClassStudent) query2.getSingleResult();
            DatabaseLink.setup().inTransaction(session1 -> session1.delete(toDelete));
            DatabaseLink.setup().inTransaction(session2 -> session2.delete(studentsListView.getSelectionModel().getSelectedItem()));
            studentsListView.getItems().remove(studentsListView.getSelectionModel().getSelectedItem());
            deleteBt.setDisable(true);
        }
    }
}
