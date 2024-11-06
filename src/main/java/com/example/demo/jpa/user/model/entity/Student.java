package com.example.demo.jpa.user.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.List;

@Entity
public class Student extends User {
    public Student() {}

    @Builder(builderMethodName = "studentBuilder")
    public Student(String username, String password, String email, Date birthday, String role, String studentGroup, String gender,  String nickname)
    {
        super(username, password, email, birthday, role, studentGroup, gender, nickname);
    }

//    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
//    private List<Student_Educator> student_educators;
//
//    public List<Student_Educator> getStudent_educators() {
//        return student_educators;
//    }
}
