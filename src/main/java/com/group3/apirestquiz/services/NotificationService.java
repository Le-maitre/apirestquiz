package com.group3.apirestquiz.services;

import com.group3.apirestquiz.models.Notification;
import com.group3.apirestquiz.models.User;
import com.group3.apirestquiz.models.UserNotification;
import com.group3.apirestquiz.repositories.UserNotificationRepository;
import com.group3.apirestquiz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserNotificationRepository userNotificationRepository;
    public List<Notification> getNotificationsForUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            return userOptional.get().getAllNotificationsForUser();
        }
        return new ArrayList<>();
    }

    public Optional<Notification> getOneNotificationForUser(Long userId, Long notificationId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            User userPresent = userOptional.get();
            return userPresent.getAllNotificationsForUser().stream().filter(notification -> notification.getNotificationId().equals(notificationId)).findFirst();
        }
        return Optional.empty();
    }

    public List<Notification> getNotificationsReadByUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            return userOptional.get().getReadNotificationsForUser();
        }
        return new ArrayList<>();
    }

    public List<Notification> getNotificationsUnReadByUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            return userOptional.get().getUnreadNotificationsForUser();
        }
        return new ArrayList<>();
    }

    public void confirmNotificationRead(Long userId, Long notificationId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            Optional<UserNotification> userNotification = userOptional.get().getUserNotifications().
                    stream().filter(un -> un.getNotification().getNotificationId().equals(notificationId)).
                    findFirst();
            if(userNotification.isPresent()){
                userNotification.get().setRead(true);
                userNotificationRepository.save(userNotification.get());
            }
        }
    }

    public List<UserNotification> getUserNotifications(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            return userOptional.get().getUserNotifications();
        }
        return new ArrayList<>();
    }

    public ResponseEntity<UserNotification> deleteNotificationForUser(Long userId, Long notificationId) {
        Optional<UserNotification> userNotificationOptional = userNotificationRepository.findByUserUserIdAndNotificationNotificationId(userId, notificationId);
        if(userNotificationOptional.isPresent()){
            userNotificationRepository.delete(userNotificationOptional.get());
            return new ResponseEntity<>(userNotificationOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
