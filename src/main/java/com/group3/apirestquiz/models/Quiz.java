package com.group3.apirestquiz.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Max(value = 20, message = "{Max.quiz.nbQuestion}")
    @Column(name = "nb_question")
    private int nbQuestion=0;

    @Size(min = 6, max = 7, message = "{Size.quiz.visibility}")
    @Pattern(regexp = "^(public|private)$", message = "{Pattern.quiz.visibility}")
    @Column(name = "visibilite")
    private String visibility="public"; // Les valeurs possibles sont : "public" et "private"


    @Size(max = 100, message = "{Size.quiz.description}")
    private String description;

    @Column(name = "date_creation")
    private LocalDate creationDate = LocalDate.now();


    @NotNull(message = "{NotNull.quiz.domain}")
    @Size(max = 20, message = "{Size.quiz.domain}")
    @Pattern(regexp = "^(informatique|religion|histoire|science|culture general|sport|anime|art|geographie|mecanique)$")
    @Column(name = "domaine")
    private String domain; // domaine du quiz. Exemple : informatique, mathematique, etc.

    private String imageUrl; // l'url de l'image
    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinColumn(name = "utilisateur_id")
    private User user;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "quiz_id")
    private List<Question> questions = new ArrayList<>();
}
