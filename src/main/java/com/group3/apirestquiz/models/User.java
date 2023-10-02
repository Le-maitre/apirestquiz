package com.group3.apirestquiz.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
