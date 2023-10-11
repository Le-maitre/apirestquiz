package com.group3.apirestquiz.repositories;

import com.group3.apirestquiz.models.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    //void deleteByUserUserIdAndNotificationNotificationId(Long userId, Long notificationId);

    Optional<UserNotification> findByUserUserIdAndNotificationNotificationId(Long userId, Long notificationId);
}
