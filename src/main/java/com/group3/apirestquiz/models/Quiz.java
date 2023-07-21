package com.group3.apirestquiz.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;

    @Column(name = "titre")
    private String title;

    @Column(name = "nb_max_question")
    private int nbMaxQuestion;

    @Column(name = "visibilite")
    private String visibility;


    private String description;

    @Column(name = "date_creation")
    private LocalDate creationDate = LocalDate.now();

    @Column(name = "domaine")
    private String domain; // domaine du quiz. Exemple : informatique, mathematique, etc.

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "utilisateur_id")
    private User user;
}
