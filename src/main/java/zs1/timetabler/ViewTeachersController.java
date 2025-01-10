package zs1.timetabler;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class ViewTeachersController {
    @FXML
    ListView<Teacher> teachersListView;
    @FXML
    Button deleteBt;
    @FXML
    Button modifyBt;

    private void loadTeachers() {
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql = "FROM zs1.timetabler.Teacher tch";
            Query query = session.createQuery(hql);
            List<Teacher> teachers = query.getResultList();
            teachersListView.getItems().removeAll(teachersListView.getItems());
            teachersListView.getItems().addAll(teachers);
        }
    }

    @FXML public void initialize() {
        teachersListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                deleteBt.setDisable(false);
                modifyBt.setDisable(false);
            }
        });
        loadTeachers();
    }

    @FXML
    public void close() {
        Stage stage = (Stage) teachersListView.getScene().getWindow();
        stage.close();
    }

    @FXML public void deleteSelected() {
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql2 = "FROM zs1.timetabler.Class cl WHERE cl.teacher=:tch";
            Query query2 = session.createQuery(hql2);
            query2.setParameter("tch", teachersListView.getSelectionModel().getSelectedItem());
            List<Class> toEdit = query2.getResultList();
            for (Class eachClass : toEdit) {
                eachClass.setTeacher(null);
                DatabaseLink.setup().inTransaction(session1 -> session1.update(eachClass));
            }
            DatabaseLink.setup().inTransaction(session2 -> session2.delete(teachersListView.getSelectionModel().getSelectedItem()));
            teachersListView.getItems().remove(teachersListView.getSelectionModel().getSelectedItem());
            deleteBt.setDisable(true);
        }
    }

    @FXML public void refresh() {
        loadTeachers();
    }

    @FXML public void modifySelected() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("add_teacher_fxml.fxml"));
        Parent root = fxmlLoader.load();
        AddTeacherController addTeacherController = fxmlLoader.getController();
        addTeacherController.loadInCurrentValues(teachersListView.getSelectionModel().getSelectedItem().getTeacher_id());
        Scene scene = new Scene(root, 500, 275);
        Stage stage = new Stage();
        stage.setTitle("Add New Teacher");
        stage.setScene(scene);
        stage.show();
        modifyBt.setDisable(true);
    }
}
