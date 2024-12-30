//package com.example.demo.jpa.user.model.entity;
//
//import com.example.demo.jpa.user.model.enumerate.Gender;
//import com.example.demo.jpa.user.model.enumerate.IsAuthorized;
//import com.example.demo.jpa.user.model.enumerate.Role;
//import com.example.demo.jpa.user.model.enumerate.State;
//import lombok.*;
//import java.time.LocalDate;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "educator") // JPA 테이블 매핑
//@Getter
//@Setter
//@NoArgsConstructor
//@ToString(callSuper = true) // 부모 클래스의 toString 포함
//public class Educator extends User {
//
//    @Builder(builderMethodName = "educatorBuilder")
//    public Educator(String username, String password, String email, LocalDate birthday, Role role, Gender gender, State state, String nickname, IsAuthorized isAuthorized)
//    {
//        super(username, password, email, birthday, role, gender, state, nickname);
//        this.isAuthorized = isAuthorized;
//    }
//
//    @Column("is_authorized")
//    private IsAuthorized isAuthorized;
//
//}
//
