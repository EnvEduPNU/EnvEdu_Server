package com.example.demo.jpa.user.model.entity;

import com.example.demo.jpa.device.model.UserDevice;
import com.example.demo.jpa.model.Location;
import com.example.demo.jpa.user.model.enumerate.Gender;
import com.example.demo.jpa.user.model.enumerate.Role;
import com.example.demo.jpa.user.model.enumerate.State;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    public User() {}

    protected User(String username, String password, String email, Date birthday, String role, String studentGroup, String gender, String state, String nickname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.role = role;
        this.studentGroup = studentGroup;
        this.gender = gender;
        this.state = state;
        this.nickname = nickname;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(nullable = false, length = 500)
    private String password;

    @Column(nullable = false, length = 40, unique = true)
    private String email;

    @Column(nullable = false)
    private Date birthday;

    @Column(nullable = false, length = 20)
    private String role;

    @Column(nullable = false, length = 10)
    @Nullable
    private String gender;

    @Column(nullable = true, length = 20)
    private String nickname;

    @Column(nullable = true, length = 20)
    private String studentGroup;

    @Column(nullable = true)
    private String state;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Location> locations;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserDevice> devices;

    @CreationTimestamp
    private Timestamp createdTime;

    @UpdateTimestamp
    private Timestamp updatedTime;

    @Column(nullable = true)
    private Long sessionId;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", role=" + role +
                ", gender=" + gender +
                ", nickname='" + nickname + '\'' +
                ", studentGroup='" + studentGroup + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", sessionId=" + sessionId +
                '}';
    }
}
