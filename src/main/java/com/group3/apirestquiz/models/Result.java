package com.group3.apirestquiz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resultat_id")
    private Long resultId;

    private int score=0;

    @Column(name = "etat")
    private boolean state=false; // Pour savoir si la partie est fini ou non

    private LocalDate date=LocalDate.now();

    private int nbCorrectQuestion=0; // le nombre de question correcte
    private int nbIncorrectQuestion=0; // le nombre de question incorrecte

    // Rélation unidirectionnelle. Ceci est un choix
    @ManyToMany(
            fetch = FetchType.LAZY // à la récupération du Resultat, les questions ne sont pas récupérés. Mais lorsque je ferai appele à la liste de question de l'objet résultat spring executera une nouvelle réquête pour recuperer la données. Cela permet d'avoir une performance optimal.
            ,cascade = {CascadeType.PERSIST, CascadeType.MERGE} // La cascade s'applique uniquement à la création et à la modification de Result ou Question
    )
    @JoinTable(
            name = "resultat_question",
            joinColumns = @JoinColumn(name = "resultat_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions = new ArrayList<>();

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinColumn(name = "utilisateur_id")
    @JsonIgnore
    private User user;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private Quiz quiz;


}
