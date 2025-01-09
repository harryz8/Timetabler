package zs1.timetabler;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ViewTeachersController {
    @FXML
    ListView<Teacher> teachersListView;
    @FXML
    Button deleteBt;

    @FXML public void initialize() {
        teachersListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                deleteBt.setDisable(false);
            }
        });
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql = "FROM zs1.timetabler.Teacher tch";
            Query query = session.createQuery(hql);
            List<Teacher> teachers = query.getResultList();
            teachersListView.getItems().addAll(teachers);
        }
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
}
