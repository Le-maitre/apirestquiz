package com.group3.apirestquiz.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "choix")
public class Choise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choix_id")
    private Long choiseId;

    @NotNull
    @Size(max = 100)
    @Column(name = "texte")
    private String text;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "question_id") // La clé etrangère de la classe Choose correspond à l'id de Question
    @JsonIgnore
    private Question question;
}
