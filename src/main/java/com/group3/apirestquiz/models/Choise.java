package com.group3.apirestquiz.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
@Table(name = "choix")
public class Choise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choix_id")
    private Long id;

    @NonNull
    @Size(max = 100)
    @Column(name = "texte")
    private String texte;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "question_id") // La clé etrangère de la classe Choose correspond à l'id de Question
    private Question question;
}
