package com.example.demo.jpa.user.model.entity;

import com.example.demo.jpa.user.model.enumerate.IsAuthorized;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Educator extends User {
    public Educator() {}

    @Builder(builderMethodName = "educatorBuilder")
    public Educator(String username, String password, String email, Date birthday, String role, String studentGroup, String gender, String nickname, IsAuthorized isAuthorized)
    {
        super(username, password, email, birthday, role,studentGroup, gender, nickname);
        this.isAuthorized = isAuthorized;
    }

    @OneToMany(mappedBy = "educator", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Student_Educator> educator_students;

    @Column(length = 3, nullable = false)
    @Enumerated(EnumType.STRING)
    private IsAuthorized isAuthorized;

    public List<Student_Educator> getEducator_students() {
        return educator_students;
    }

    public IsAuthorized getIsAuthorized() {
        return isAuthorized;
    }

    public void updateAuthorization(IsAuthorized isAuthorized) {
        this.isAuthorized = isAuthorized;
    }
}
