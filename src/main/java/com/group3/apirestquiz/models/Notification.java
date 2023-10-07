package com.group3.apirestquiz.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @NotNull(message = "{NotNull.notification.title}")
    @Size(min = 2, max = 200, message = "{Size.notification.title}")
    private String title;

    @NotNull(message = "{NotNull.notification.body}")
    @Size(min = 2, max = 1024)
    private String body;

    private LocalDateTime creationDate = LocalDateTime.now();
}
