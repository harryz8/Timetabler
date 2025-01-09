package zs1.timetabler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
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
    @FXML Label holder;
    @FXML
    ChoiceBox<Student> studentChoice;
    @FXML
    ChoiceBox<Teacher> teacherChoice;
    @FXML BorderPane primaryPane;

    private void updateChoices() {
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql = "FROM zs1.timetabler.Student std";
            Query query = session.createQuery(hql);
            List<Student> students = query.getResultList();
            studentChoice.getItems().removeAll(studentChoice.getItems());
            studentChoice.getItems().addAll(students);
            String hql2 = "FROM zs1.timetabler.Teacher tch";
            Query query2 = session.createQuery(hql2);
            List<Teacher> teachers = query2.getResultList();
            teacherChoice.getItems().removeAll(teacherChoice.getItems());
            teacherChoice.getItems().addAll(teachers);
        }
    }

    @FXML public void initialize() {
        updateChoices();
        studentChoice.showingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    updateChoices();
                }
            }
        });
        teacherChoice.showingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    updateChoices();
                }
            }
        });
    }

    private void displayClassList(List<Class> classLs) {
        ClassSorter clSt = new ClassSorter();
        classLs.sort(clSt);
//            for (Class eachClass : classLs) {
//                Label name = new Label();
//                name.setText(eachClass.getName());
//                holder.add(name, eachClass.getDay(), Math.toIntExact(eachClass.getStartTime().getTime()/60000));
//            }
        holder.setText("");
        for (Class eachClass : classLs) {
            String stmin = String.valueOf(eachClass.getStartTime().getMinutes());
            if (eachClass.getStartTime().getMinutes() < 10) {
                stmin = "0"+stmin;
            }
            String endmin = String.valueOf(eachClass.getEndTime().getMinutes());
            if (eachClass.getEndTime().getMinutes() < 10) {
                endmin = "0"+endmin;
            }
            holder.setText(holder.getText()+eachClass.getName()+"\tDay: "+eachClass.getDay()+"\t"+String.valueOf(eachClass.getStartTime().getHours())+":"+stmin+" - "+String.valueOf(eachClass.getEndTime().getHours())+":"+endmin+"\n");
        }
    }

    @FXML public void findStudentTimetable() {
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql2 = "SELECT cs.classL FROM zs1.timetabler.ClassStudent cs WHERE cs.student = :sdt";
            Query query2 = session.createQuery(hql2);
            query2.setParameter("sdt", studentChoice.getSelectionModel().getSelectedItem());
            List<Class> classLs = query2.getResultList();
            displayClassList(classLs);
        }
    }

    @FXML public void findTeacherTimetable() {
        try (Session session = DatabaseLink.setup().openSession()) {
            String hql = "From zs1.timetabler.Class cl WHERE cl.teacher = :tch";
            Query query = session.createQuery(hql);
            query.setParameter("tch", teacherChoice.getSelectionModel().getSelectedItem());
            List<Class> classLs = query.getResultList();
            displayClassList(classLs);
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

    @FXML public void viewStudents() throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("view_students_fxml.fxml"));
        Scene scene = new Scene(root, 500, 275);
        Stage stage = new Stage();
        stage.setTitle("Students");
        stage.setScene(scene);
        stage.show();
    }

    @FXML public void viewTeachers() throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("view_teachers_fxml.fxml"));
        Scene scene = new Scene(root, 500, 275);
        Stage stage = new Stage();
        stage.setTitle("Teachers");
        stage.setScene(scene);
        stage.show();
    }

    @FXML public void viewClasses() throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("view_class_fxml.fxml"));
        Scene scene = new Scene(root, 500, 275);
        Stage stage = new Stage();
        stage.setTitle("Classes");
        stage.setScene(scene);
        stage.show();
    }

    @FXML public void addTeacherToClass() throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("add_teacher_to_class_fxml.fxml"));
        Scene scene = new Scene(root, 500, 275);
        Stage stage = new Stage();
        stage.setTitle("Change the Teacher assigned to a Class");
        stage.setScene(scene);
        stage.show();
    }
}
