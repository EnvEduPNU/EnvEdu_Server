package com.example.demo.jpa.device.model;

import com.example.demo.jpa.user.model.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class UserDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20, nullable = false, unique = true)
    private String mac;

    @Column(length = 20)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @CreationTimestamp
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp updatedDate;

    @Column(nullable = false)
    private boolean deviceOn;

    public UserDevice() {}

    @Builder(access = AccessLevel.PRIVATE)
    private UserDevice(long id, String mac, String name, User user, Timestamp createdDate, Timestamp updatedDate, boolean deviceOn) {
        this.id = id;
        this.mac = mac;
        this.name = name;
        this.user = user;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deviceOn = deviceOn;
    }

    public static UserDevice of(String mac) {
        return UserDevice.builder()
                .mac(mac)
                .build();
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void setDeviceOn(boolean b) {this.deviceOn = b; }
}