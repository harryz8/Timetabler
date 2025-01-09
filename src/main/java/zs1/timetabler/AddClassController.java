package zs1.timetabler;

import jakarta.persistence.Query;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;

public class AddClassController {
    @FXML
    ChoiceBox<StringIntTuple> daysOfTheWeek;
    @FXML ChoiceBox<Integer> hoursInDayStart;
    @FXML ChoiceBox<Integer> hoursInDayEnd;
    @FXML ChoiceBox<Integer> minsInHourStart;
    @FXML ChoiceBox<Integer> minsInHourEnd;
    @FXML
    TextField className;
    @FXML ChoiceBox<Teacher> teacherChoice;

    @FXML void initialize() {
        ObservableList<StringIntTuple> oi = daysOfTheWeek.getItems();
        oi.add(new StringIntTuple("Monday", 1));
        oi.add(new StringIntTuple("Tuesday", 2));
        oi.add(new StringIntTuple("Wednesday", 3));
        oi.add(new StringIntTuple("Thursday", 4));
        oi.add(new StringIntTuple("Friday", 5));
        for (int i=1; i<=24; i++) {
            hoursInDayEnd.getItems().add(i);
            hoursInDayStart.getItems().add(i);
        }
        for (int i=0; i<60; i++) {
            minsInHourStart.getItems().add(i);
            minsInHourEnd.getItems().add(i);
        }
        try (Session session = DatabaseLink.setup().openSession()) {
            //find teacher object
            String hql = "FROM zs1.timetabler.Teacher tch";
            Query query = session.createQuery(hql);
            List<Teacher> teachers = query.getResultList();
            teacherChoice.getItems().addAll(teachers);
        }
    }
    @FXML void submit() {
        Teacher teacher = teacherChoice.getSelectionModel().getSelectedItem();
        //add record
        SessionFactory sf = DatabaseLink.setup();
        sf.inTransaction(session2 -> session2.persist(new Class(daysOfTheWeek.getSelectionModel().getSelectedItem().getInteger(), className.getText(), teacher, Time.valueOf(LocalTime.of(hoursInDayStart.getSelectionModel().getSelectedItem(), minsInHourStart.getSelectionModel().getSelectedItem())), Time.valueOf(LocalTime.of(hoursInDayEnd.getSelectionModel().getSelectedItem(), minsInHourEnd.getSelectionModel().getSelectedItem())))));
        cancel();
    }
    @FXML void cancel() {
        Stage stage = (Stage) daysOfTheWeek.getScene().getWindow();
        stage.close();
    }
}
