package com.group3.apirestquiz.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long quizId;

    @NotNull
    @Size(max = 50)
    @Column(name = "titre")
    private String title;

    @NotNull
    //@Size(max = 2)
    @Column(name = "nb_max_question")
    private int nbMaxQuestion;

    @NotNull
    @Size(max = 7)
    @Column(name = "visibilite")
    private String visibility; // Les valeurs possibles sont : "public" et "private"


    @Size(max = 100)
    private String description;

    @Column(name = "date_creation")
    private LocalDate creationDate = LocalDate.now();


    @NotNull
    @Size(max = 20)
    @Column(name = "domaine")
    private String domain; // domaine du quiz. Exemple : informatique, mathematique, etc.

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "utilisateur_id")
    private User user;
}
