package com.example.demo.jpa.user.model.entity;

import com.example.demo.jpa.device.model.UserDevice;
import com.example.demo.jpa.model.Location;
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

    protected User(String username, String password, String email, Date birthday, String role, String gender, String nickname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.role = role;
        this.gender = gender;
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

    @Column(nullable = false, length = 13)
    private String role;

    @Column(nullable = false, length = 6)
    @Nullable
    private String gender;

    @Column(nullable = true, length = 20)
    private String nickname;

    @Column(nullable = true, length = 20)
    private String studentGroup;

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


    //--------------------------------------------------------------------------------------------------

//    public void setUsername(String username)
//    {
//        if(!Pattern.matches("^[\\w_]{5,20}$",username))
//        {
//            throw new IllegalArgumentException();
//        }
//        this.username = username;
//    }

//    public void setPassword(String password)
//    {
//        if(!Pattern.matches("^.*(?=^.{8,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",password))
//        {
//            throw new IllegalArgumentException();
//        }
//        this.password = password;
//    }

//    public void setEmail(String email)
//    {
//        if(!Pattern.matches("^[\\da-zA-Z]([-_.]?[\\da-zA-Z])*@[\\da-zA-Z]([-_.]?[\\da-zA-Z])*.[a-zA-Z]{2,3}$",email))
//        {
//            throw new IllegalArgumentException();
//        }
//        this.email = email;
//    }

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
