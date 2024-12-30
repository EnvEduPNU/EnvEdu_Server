//package com.example.demo.jpa.user.model.entity;
//
//import com.example.demo.jpa.user.model.enumerate.Gender;
//import com.example.demo.jpa.user.model.enumerate.Role;
//import com.example.demo.jpa.user.model.enumerate.State;
//import lombok.Builder;
//import java.time.LocalDate;
//
//import javax.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Table("students")
//@Getter
//@Setter
//public class Student extends User {
//
//    @Builder(builderMethodName = "studentBuilder")
//    public Student(String username, String password, String email, LocalDate birthday, String role, String gender, String state, String nickname) {
//        super(username, password, email, birthday, role, gender, state, nickname);
//    }
//
////    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
////    private List<Student_Educator> student_educators;
////
////    public List<Student_Educator> getStudent_educators() {
////        return student_educators;
////    }
//}
