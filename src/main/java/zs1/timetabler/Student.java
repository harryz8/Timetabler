package zs1.timetabler;

import jakarta.persistence.*;

@Entity
@Table(name = "student_table")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int student_id;

    @Column private String first_name;

    @Column private String surname;

    @Column private int year;

    public Student() {

    }

    public Student(String first_name, String surname, int year) {
        this.first_name = first_name;
        this.surname = surname;
        this.year = year;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getSurname() {
        return surname;
    }

    public int getYear() {
        return year;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
