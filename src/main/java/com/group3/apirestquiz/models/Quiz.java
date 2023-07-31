package com.group3.apirestquiz.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Data
@DynamicUpdate // permet de mettre à jour uniquement la partie modifier
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long quizId;

    @NotNull(message = "{NotNull.quiz.title}")
    @Size(min = 10, max = 50, message = "{Size.quiz.title}")
    @Column(name = "titre")
    private String title;

    @NotNull(message = "{NotNull.quiz.nbMaxQuestion}")
    @Min(value = 2, message = "{Min.quiz.nbMaxQuestion}")
    @Max(value = 50, message = "{Max.quiz.nbMaxQuestion}")
    @Column(name = "nb_max_question")
    private int nbMaxQuestion;

    @NotNull(message = "{NotNull.quiz.visibility}")
    @Size(min = 6, max = 7, message = "{Size.quiz.visibility}")
    @Pattern(regexp = "^(public|private)$")
    @Column(name = "visibilite")
    private String visibility; // Les valeurs possibles sont : "public" et "private"


    @Size(max = 100, message = "{Size.quiz.description}")
    private String description;

    @Column(name = "date_creation")
    private LocalDate creationDate = LocalDate.now();


    @NotNull(message = "{NotNull.quiz.domain}")
    @Size(max = 20, message = "{Size.quiz.domain}")
    @Pattern(regexp = "^(informatique|religion|histoire|science|culture general)$")
    @Column(name = "domaine")
    private String domain; // domaine du quiz. Exemple : informatique, mathematique, etc.

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinColumn(name = "utilisateur_id")
    private User user;
}
