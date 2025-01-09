package zs1.timetabler;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "class_table")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int class_id;

    @Column
    private int day;

    @Column
    private String name;

    @ManyToOne
    private Teacher teacher;

    @Column
    private java.sql.Time startTime;

    @Column
    private java.sql.Time endTime;

    public Class() {

    }

    public Class(int day, String name, Teacher teacher, java.sql.Time startTime, java.sql.Time endTime) {
        this.day = day;
        this.name = name;
        this.teacher = teacher;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getDay() {
        return day;
    }

    public String getName() {
        return name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    @Override public String toString() {
        return getName()+" Day: "+getDay()+" Start time: "+getStartTime();
    }
}
