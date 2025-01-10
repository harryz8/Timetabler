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
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class ViewStudentsController {
    @FXML ListView<Student> studentsListView;
    @FXML ChoiceBox<Class> classFilter;
    @FXML Button deleteBt;
    @FXML Button modifyBt;

    private void loadStudentChoices() {
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql = "FROM zs1.timetabler.Student";
            Query query = session.createQuery(hql);
            List<Student> students = query.getResultList();
            studentsListView.getItems().removeAll(studentsListView.getItems());
            studentsListView.getItems().addAll(students);
        }
    }

    @FXML public void initialize() {
        studentsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                deleteBt.setDisable(false);
                modifyBt.setDisable(false);
            }
        });
        loadStudentChoices();
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql = "FROM zs1.timetabler.Class cl";
            Query query = session.createQuery(hql);
            List<zs1.timetabler.Class> classes = query.getResultList();
            classFilter.getItems().addAll(classes);
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
            List<ClassStudent> toDelete = query2.getResultList();
            for (ClassStudent eachClassStudent : toDelete) {
                DatabaseLink.setup().inTransaction(session1 -> session1.delete(eachClassStudent));
            }
            DatabaseLink.setup().inTransaction(session2 -> session2.delete(studentsListView.getSelectionModel().getSelectedItem()));
            studentsListView.getItems().remove(studentsListView.getSelectionModel().getSelectedItem());
            deleteBt.setDisable(true);
        }
    }

    @FXML public void modifySelected() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("add_student_fxml.fxml"));
        Parent root = fxmlLoader.load();
        AddStudentController addStudentController = fxmlLoader.getController();
        addStudentController.loadInCurrentValues(studentsListView.getSelectionModel().getSelectedItem().getStudent_id());
        Scene scene = new Scene(root, 500, 275);
        Stage stage = new Stage();
        stage.setTitle("Add New Student");
        stage.setScene(scene);
        stage.show();
        modifyBt.setDisable(true);
    }

    @FXML public void applyFilter() {
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql = "SELECT cs.student FROM zs1.timetabler.ClassStudent cs WHERE cs.classL = :cls";
            Query query = session.createQuery(hql);
            query.setParameter("cls", classFilter.getSelectionModel().getSelectedItem());
            List<Student> students = query.getResultList();
            studentsListView.getItems().removeAll(studentsListView.getItems());
            studentsListView.getItems().addAll(students);
        }
    }

    @FXML public void clearFilter() {
        classFilter.getSelectionModel().clearSelection();
        loadStudentChoices();
    }

    @FXML public void refresh() {
        if (classFilter.getSelectionModel().getSelectedItem() == null) {
            loadStudentChoices();
        }
        else {
            applyFilter();
        }
    }
}
