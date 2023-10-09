package com.group3.apirestquiz.controllers;

import com.group3.apirestquiz.models.Notification;
import com.group3.apirestquiz.models.UserNotification;
import com.group3.apirestquiz.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Operation(summary = "Récuperer la liste des notifications reçu par un user")
    @GetMapping("users/{userId}/notifications")
    public List<Notification> getNotificationsForUser(@PathVariable Long userId) {
        return notificationService.getNotificationsForUser(userId);
    }

    @Operation(summary = "Récuperer une notification reçu par un user")
    @GetMapping("users/{userId}/notifications/{notificationId}")
    public Optional<Notification> getOneNotificationForUser(@PathVariable Long userId, @PathVariable Long notificationId) {
        return notificationService.getOneNotificationForUser(userId, notificationId);
    }

    @Operation(summary = "Récuperer les notifications déjà lu par un user")
    @GetMapping("users/{userId}/notifications/read")
    public List<Notification> getNotificationsReadByUser(@PathVariable Long userId) {
        return notificationService.getNotificationsReadByUser(userId);
    }

    @Operation(summary = "Récuperer les notifications qui ne sont pas encore lu par un user")
    @GetMapping("users/{userId}/notifications/unread")
    public List<Notification> getNotificationsUnReadByUser(@PathVariable Long userId) {
        return notificationService.getNotificationsUnReadByUser(userId);
    }

    @Operation(summary = "Confirmer la lecture d'une notification")
    @GetMapping("users/{userId}/notifications/{notificationId}/read")
    public void confirmNotificationRead(@PathVariable Long userId, @PathVariable Long notificationId) {
        notificationService.confirmNotificationRead(userId, notificationId);
    }

    @Operation(summary = "Récuperer la liste des UserNotifications")
    @GetMapping("users/{userId}/usernotifications")
    public List<UserNotification> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }
}
