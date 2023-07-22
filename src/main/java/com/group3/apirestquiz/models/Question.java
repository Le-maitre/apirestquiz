package com.group3.apirestquiz.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @NonNull
    @Size(max = 100)
    @Column(name = "texte")
    private String text;

    @NonNull
    @Size(max = 15)
    private String type; // Les valeurs possibles sont: "choix-multiple" et "vrai-faux"

    @NonNull
    @Size(max = 2)
    private int point;

    @NonNull
    @Size(max = 2)
    @Column(name = "reponse")
    private int response; // le numero du choix correspondant à la reponse

    @OneToMany(
            cascade = CascadeType.ALL, // Permet de s'assurer que tous changement effectué sur une question va impacter ses choix et vise versa
            orphanRemoval = true, // Permet de s'assurer que lorqu'on supprime une question par exemple, les choix seront aussi supprimer
            fetch = FetchType.EAGER //Permet d'avoir la liste des choix lorsqu'on appele un objet Question
    )
    @JoinColumn(name = "question_id") // La clé etrangère de chaque choix correspond à l'id de Question
    private List<Choise> choises = new ArrayList<>();

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "quiz_id") // La clé etrangère de la classe Question correspond à l'id de Quiz
    private Quiz quiz;
}
