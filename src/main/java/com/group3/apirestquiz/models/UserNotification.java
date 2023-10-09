package com.group3.apirestquiz.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "user_notification")
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNotificationId;

    @NotNull(message = "{NotNull.usernotification.notification}")
    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @NotNull(message = "{NotNull.usernotification.user}")
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private User user;

    private boolean isRead=false;


}
