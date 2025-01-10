package zs1.timetabler;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class ViewStudentsInClassController {
    @FXML ListView<Student> studentsListView;
    @FXML ChoiceBox<Class> classFilter;
    @FXML Button deleteBt;

    @FXML public void initialize() {
        studentsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                deleteBt.setDisable(false);
            }
        });
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql = "FROM zs1.timetabler.Class cl";
            Query query = session.createQuery(hql);
            List<Class> classes = query.getResultList();
            classFilter.getItems().addAll(classes);
        }
    }

    @FXML public void close() {
        Stage stage = (Stage) studentsListView.getScene().getWindow();
        stage.close();
    }

    @FXML public void deleteSelected() {
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql2 = "FROM zs1.timetabler.ClassStudent cs WHERE cs.student=:stdnt AND cs.classL=:cls";
            Query query2 = session.createQuery(hql2);
            query2.setParameter("stdnt", studentsListView.getSelectionModel().getSelectedItem());
            query2.setParameter("cls", classFilter.getSelectionModel().getSelectedItem());
            ClassStudent toDelete = (ClassStudent) query2.getResultList().get(0);
            DatabaseLink.setup().inTransaction(session2 -> session2.delete(toDelete));
            loadStudents();
            deleteBt.setDisable(true);
        }
    }

    @FXML public void loadStudents() {
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql = "SELECT cs.student FROM zs1.timetabler.ClassStudent cs WHERE cs.classL = :cls";
            Query query = session.createQuery(hql);
            query.setParameter("cls", classFilter.getSelectionModel().getSelectedItem());
            List<Student> students = query.getResultList();
            studentsListView.getItems().removeAll(studentsListView.getItems());
            studentsListView.getItems().addAll(students);
        }
    }
}
