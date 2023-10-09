package com.group3.apirestquiz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @NotNull(message = "{NotNull.notification.title}")
    @Size(min = 2, max = 200, message = "{Size.notification.title}")
    private String title;

    @NotNull(message = "{NotNull.notification.body}")
    @Size(min = 2, max = 1024)
    private String body;

    private LocalDateTime creationDate = LocalDateTime.now();

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<UserNotification> userNotifications = new ArrayList<>();
}
