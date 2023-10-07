package com.group3.apirestquiz.services;

import com.group3.apirestquiz.models.Notification;
import com.group3.apirestquiz.models.User;
import com.group3.apirestquiz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private UserRepository userRepository;
    public List<Notification> getNotificationsForUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            return userOptional.get().getNotifications();
        }
        return new ArrayList<>();
    }

    public Optional<Notification> getOneNotificationForUser(Long userId, Long notificationId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            User userPresent = userOptional.get();
            return userPresent.getNotifications().stream().filter(notification -> notification.getNotificationId().equals(notificationId)).findFirst();
        }
        return Optional.empty();
    }
}
