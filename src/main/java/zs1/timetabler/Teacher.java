package zs1.timetabler;

import jakarta.persistence.*;

@Entity
@Table(name = "teacher_table")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int teacher_id;

    @Column
    private String surname;

    @Column
    private String first_name;

    public Teacher() {

    }

    public Teacher(String first_name, String surname) {
        this.first_name = first_name;
        this.surname = surname;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override public String toString() {
        return getFirst_name() + " " + getSurname();
    }
}
