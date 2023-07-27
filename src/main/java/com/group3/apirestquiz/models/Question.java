package com.group3.apirestquiz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long questionId;

    @NotNull(message = "")
    @Size(min = 10, max = 100, message = "")
    @Column(name = "texte")
    private String text;

    @NotNull(message = "")
    @Pattern(regexp = "^(choix-multiple|vrai-faux)$", message = "{}")
    private String type; // Les valeurs possibles sont: "choix-multiple" et "vrai-faux"

    @NotNull(message = "")
    @Min(value = 5, message = "")
    @Max(value = 100, message = "")
    private int point=5;

    @Column(name = "rang")
    private int rank; // le rang de la question. Elle est toujour inferieur ou égale au nombre de question dans le quiz

    @NotNull(message = "")
    @Min(value = 1, message = "") // 1 parce qu'il y'a au moins une question
    @Max(value = 6, message = "") // 6 parce qu'il y'a au plus 6 question
    @Column(name = "rangReponse")
    private int rankResponse; // le rang du choix correspondant à la reponse

    @OneToMany(
            cascade = CascadeType.ALL, // Permet de s'assurer que tous changement effectué sur une question va impacter ses choix et vise versa
            orphanRemoval = true, // Permet de s'assurer que lorqu'on supprime une question par exemple, les choix seront aussi supprimer
            fetch = FetchType.EAGER //Permet d'avoir la liste des choix lorsqu'on appele un objet Question
    )
    @JoinColumn(name = "question_id") // La clé etrangère de chaque choix correspond à l'id de Question
    private List<Choise> choises = new ArrayList<>();

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinColumn(name = "quiz_id") // La clé etrangère de la classe Question correspond à l'id de Quiz
    @JsonIgnore
    private Quiz quiz;
}
