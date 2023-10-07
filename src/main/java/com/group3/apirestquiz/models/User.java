package com.group3.apirestquiz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
            fetch = FetchType.LAZY
            ,cascade = {CascadeType.PERSIST, CascadeType.MERGE} // La cascade s'applique uniquement à la création et à la modification
    )
    @JoinTable(
            name = "user_notification",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id")
    )
    List<Notification> notifications = new ArrayList<>();
}
