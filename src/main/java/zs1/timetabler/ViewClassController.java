package zs1.timetabler;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class ViewClassController {
    @FXML ListView<Class> classListView;
    @FXML Button deleteBt;
    @FXML Button modifyBt;

    private void loadClasses() {
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql = "FROM zs1.timetabler.Class cl";
            Query query = session.createQuery(hql);
            List<zs1.timetabler.Class> classes = query.getResultList();
            classListView.getItems().removeAll(classListView.getItems());
            classListView.getItems().addAll(classes);
        }
    }

    @FXML public void initialize() {
        classListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                deleteBt.setDisable(false);
                modifyBt.setDisable(false);
            }
        });
        loadClasses();
    }

    @FXML public void close() {
        Stage stage = (Stage) classListView.getScene().getWindow();
        stage.close();
    }

    @FXML public void deleteSelected() {
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql2 = "FROM zs1.timetabler.ClassStudent cs WHERE cs.classL=:cl";
            Query query2 = session.createQuery(hql2);
            query2.setParameter("cl", classListView.getSelectionModel().getSelectedItem());
            List<ClassStudent> toDelete = query2.getResultList();
            for (ClassStudent eachClassStudent : toDelete) {
                DatabaseLink.setup().inTransaction(session1 -> session1.delete(eachClassStudent));
            }
            DatabaseLink.setup().inTransaction(session2 -> session2.delete(classListView.getSelectionModel().getSelectedItem()));
            classListView.getItems().remove(classListView.getSelectionModel().getSelectedItem());
            deleteBt.setDisable(true);
        }
    }

    @FXML public void refresh() {
        loadClasses();
    }

    @FXML public void modifySelected() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("add_class_fxml.fxml"));
        Parent root = fxmlLoader.load();
        AddClassController addClassController = fxmlLoader.getController();
        addClassController.loadInCurrentValues(classListView.getSelectionModel().getSelectedItem().getClass_id());
        Scene scene = new Scene(root, 500, 275);
        Stage stage = new Stage();
        stage.setTitle("Add New Class");
        stage.setScene(scene);
        stage.show();
        modifyBt.setDisable(true);
    }
}
