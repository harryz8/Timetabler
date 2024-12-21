package zs1.timetabler;

import jakarta.persistence.*;

@Entity
@Table(name = "class_student_table")
public class ClassStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int classStudentID;

    @ManyToOne
    private Class classL;

    @ManyToOne
    private Student student;

    public ClassStudent() {

    }

    public ClassStudent(Class classL, Student student) {
        this.classL = classL;
        this.student = student;
    }

    public void setClassL(Class classL) {
        this.classL = classL;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Class getClassL() {
        return classL;
    }

    public Student getStudent() {
        return student;
    }
}
