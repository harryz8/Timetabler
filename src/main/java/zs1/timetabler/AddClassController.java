package zs1.timetabler;

import jakarta.persistence.Query;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;

public class AddClassController {
    @FXML
    ComboBox<StringIntTuple> daysOfTheWeek;
    @FXML ChoiceBox<Integer> hoursInDayStart;
    @FXML ChoiceBox<Integer> hoursInDayEnd;
    @FXML ChoiceBox<Integer> minsInHourStart;
    @FXML ChoiceBox<Integer> minsInHourEnd;
    @FXML
    TextField className;
    @FXML ComboBox<Teacher> teacherChoice;
    int toAdd;

    public void loadInCurrentValues(int toAdd) {
        teacherChoice.setPromptText("Choose teacher");
        daysOfTheWeek.setPromptText("Choose day of the week");
        this.toAdd = toAdd;
        this.className.setText("Class name");
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
        if (toAdd != -1) {
            try (Session session = DatabaseLink.setup().openSession()) {
                //find teacher object
                String hql = "FROM zs1.timetabler.Teacher tch";
                Query query = session.createQuery(hql);
                List<Teacher> teachers = query.getResultList();
                teacherChoice.getItems().addAll(teachers);
                System.out.println(toAdd);
                String hql2 = "FROM zs1.timetabler.Class cl WHERE cl.class_id = :cid";
                Query query2 = session.createQuery(hql2);
                query2.setParameter("cid", toAdd);
                Class theClass = (Class) query2.getSingleResult();
                if (theClass.getName() != null) {
                    className.setText(theClass.getName());
                }
                if (theClass.getTeacher() != null) {
                    teacherChoice.getSelectionModel().select(theClass.getTeacher());
                }
                if (theClass.getDay() != 0) {
                    for (StringIntTuple each : daysOfTheWeek.getItems()) {
                        if (theClass.getDay() == each.getInteger()) {
                            daysOfTheWeek.getSelectionModel().select(each);
                            break;
                        }
                    }
                }
                if (theClass.getStartTime() != null) {
                    hoursInDayStart.getSelectionModel().select(Integer.valueOf(theClass.getStartTime().getHours()));
                    minsInHourStart.getSelectionModel().select(Integer.valueOf(theClass.getStartTime().getMinutes()));
                }
                if (theClass.getEndTime() != null) {
                    hoursInDayEnd.getSelectionModel().select(Integer.valueOf(theClass.getEndTime().getHours()));
                    minsInHourEnd.getSelectionModel().select(Integer.valueOf(theClass.getEndTime().getMinutes()));
                }
            }
        }
        className.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                className.clear();
            }
        });
    }

    @FXML void submit() {
        //add record
        if (toAdd != -1) {
            try (Session session = DatabaseLink.setup().openSession()) {
                System.out.println(toAdd);
                String hql = "FROM zs1.timetabler.Class st WHERE st.class_id = :sid";
                Query query = session.createQuery(hql);
                query.setParameter("sid", toAdd);
                Class toAddClass = (Class) query.getSingleResult();
                SessionFactory sf = DatabaseLink.setup();
                toAddClass.setTeacher(teacherChoice.getSelectionModel().getSelectedItem());
                toAddClass.setDay(daysOfTheWeek.getSelectionModel().getSelectedItem().getInteger());
                toAddClass.setName(className.getText());
                toAddClass.setStartTime(Time.valueOf(LocalTime.of(hoursInDayStart.getSelectionModel().getSelectedItem(), minsInHourStart.getSelectionModel().getSelectedItem())));
                toAddClass.setEndTime(Time.valueOf(LocalTime.of(hoursInDayEnd.getSelectionModel().getSelectedItem(), minsInHourEnd.getSelectionModel().getSelectedItem())));
                sf.inTransaction(session1 -> session1.update(toAddClass));
            }
        }
        else {
            Class newClass = new Class();
            newClass.setTeacher(teacherChoice.getSelectionModel().getSelectedItem());
            newClass.setDay(daysOfTheWeek.getSelectionModel().getSelectedItem().getInteger());
            newClass.setName(className.getText());
            newClass.setStartTime(Time.valueOf(LocalTime.of(hoursInDayStart.getSelectionModel().getSelectedItem(), minsInHourStart.getSelectionModel().getSelectedItem())));
            newClass.setEndTime(Time.valueOf(LocalTime.of(hoursInDayEnd.getSelectionModel().getSelectedItem(), minsInHourEnd.getSelectionModel().getSelectedItem())));
            SessionFactory sf = DatabaseLink.setup();
            sf.inTransaction(session1 -> session1.persist(newClass));
        }
        close();
    }

    private void close() {
        Stage stage = (Stage) daysOfTheWeek.getScene().getWindow();
        stage.close();
    }

    @FXML public void cancel() {
//        try (Session session = DatabaseLink.setup().openSession()) {
//            System.out.println(toAdd);
//            String hql = "FROM zs1.timetabler.Class st WHERE st.class_id = :sid";
//            Query query = session.createQuery(hql);
//            query.setParameter("sid", toAdd);
//            Class toAddClass = (Class) query.getSingleResult();
//            SessionFactory sf = DatabaseLink.setup();sf.inTransaction(session1 -> session1.delete(toAddClass));
//            close();
//        }
        close();
    }
}
