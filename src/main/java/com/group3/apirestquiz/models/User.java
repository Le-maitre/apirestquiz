package com.group3.apirestquiz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@Table(name = "utilisateur")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "utilisateur_id")
    private Long userId;

    @NotNull
    @Size(max = 20)
    @Column(name = "prenom")
    private String firstName;

    @NotNull
    @Size(max = 20)
    @Column(name = "nom")
    private String lastName;

    @NotNull
    @Size(max = 30)
    @Column(name = "email")
    private String email;

    @NotNull
    @Size(max = 20)
    private String login;

    @NotNull
    @Column(name = "mot_de_passe")
    private String password;

    private String imageUrl; // l'url de l'image

    @ManyToMany(
            fetch = FetchType.LAZY // à la récupération des followers, les users ne sont pas récupérés. Mais lorsque je ferai appelle à la liste des followers de l'objet User spring exécutera une nouvelle requête pour récupérer les données. Cela permet d'avoir une performance optimale.
            ,cascade = {CascadeType.PERSIST, CascadeType.MERGE} // La cascade s'applique uniquement à la création et à la modification
    )
    @JoinTable(
            name = "follower",
            joinColumns = @JoinColumn(name = "user_id"), // Nom de la colonne pour l'utilisateur suivi
            inverseJoinColumns = @JoinColumn(name = "follower_id") // Nom de la colonne pour l'utilisateur qui suit
    )

    @JsonIgnore
    private List<User> followers = new ArrayList<>();

    @ManyToMany(
            fetch = FetchType.LAZY // à la récupération des followers, les users ne sont pas récupérés. Mais lorsque je ferai appelle à la liste des followers de l'objet User spring exécutera une nouvelle requête pour récupérer les données. Cela permet d'avoir une performance optimale.
            ,cascade = {CascadeType.PERSIST, CascadeType.MERGE} // La cascade s'applique uniquement à la création et à la modification
    )
    @JoinTable(
            name = "following",
            joinColumns = @JoinColumn(name = "user_id"), // Nom de la colonne pour l'utilisateur suivi
            inverseJoinColumns = @JoinColumn(name = "following_id") // Nom de la colonne pour l'utilisateur qui suit
    )

    @JsonIgnore
    private List<User> followings = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<UserNotification> userNotifications = new ArrayList<>();

    @JsonIgnore
    public List<Notification> getAllNotificationsForUser() {
        return userNotifications.stream().map(UserNotification::getNotification).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Notification> getUnreadNotificationsForUser() {
        return userNotifications.stream().filter(userNotification -> !userNotification.isRead())
                .map(UserNotification::getNotification).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Notification> getReadNotificationsForUser() {
        return userNotifications.stream().filter(UserNotification::isRead)
                .map(UserNotification::getNotification).collect(Collectors.toList());
    }
}
