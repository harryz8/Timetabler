package zs1.timetabler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.hibernate.query.Query;
import org.hibernate.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TimetablerController {
    @FXML TextField studentName;
    @FXML GridPane holder;

    @FXML public void findStudent() {
        String[] nameString = studentName.getText().split(" ");
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql = "FROM zs1.timetabler.Student std WHERE std.first_name = :fn AND std.surname = :srn";
            Query query = session.createQuery(hql);
            query.setParameter("fn", nameString[0]);
            query.setParameter("srn", nameString[1]);
            Student student = (Student) query.getSingleResult();
            String hql2 = "SELECT cs.classL FROM zs1.timetabler.ClassStudent cs WHERE cs.student = :sdt";
            Query query2 = session.createQuery(hql2);
            query2.setParameter("sdt", student);
            List<Class> classLs = query2.getResultList();
            ClassSorter clSt = new ClassSorter();
            classLs.sort(clSt);
            for (Class eachClass : classLs) {
                Label name = new Label();
                name.setText(eachClass.getName());
                holder.add(name, eachClass.getDay(), Math.toIntExact(eachClass.getStartTime().getTime()/60000));
            }
        }
    }
    @FXML public void addStudent() throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("add_student_fxml.fxml"));
        Scene scene = new Scene(root, 500, 275);
        Stage stage = new Stage();
        stage.setTitle("Add New Student");
        stage.setScene(scene);
        stage.show();
    }
    @FXML public void addClass() throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("add_class_fxml.fxml"));
        Scene scene = new Scene(root, 500, 275);
        Stage stage = new Stage();
        stage.setTitle("Add New Class");
        stage.setScene(scene);
        stage.show();
    }
    @FXML public void addTeacher() throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("add_teacher_fxml.fxml"));
        Scene scene = new Scene(root, 500, 275);
        Stage stage = new Stage();
        stage.setTitle("Add New Teacher");
        stage.setScene(scene);
        stage.show();
    }
    @FXML public void addStudentToClass() throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("add_student_to_class.fxml"));
        Scene scene = new Scene(root, 500, 275);
        Stage stage = new Stage();
        stage.setTitle("Add Student to Class");
        stage.setScene(scene);
        stage.show();
    }
}
