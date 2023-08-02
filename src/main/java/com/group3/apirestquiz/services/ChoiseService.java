package com.group3.apirestquiz.services;

import com.group3.apirestquiz.models.Choise;
import com.group3.apirestquiz.models.Question;
import com.group3.apirestquiz.models.Quiz;
import com.group3.apirestquiz.repositories.ChoiseRepository;
import com.group3.apirestquiz.repositories.QuestionRepository;
import com.group3.apirestquiz.repositories.QuizRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChoiseService {

    @Autowired
    private ChoiseRepository choiseRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizRepository quizRepository;

    public List<Choise> getChoisesByQuizIdAndQuestionId(Long userId, Long quizId, Long questionId) {
        Optional<Quiz> quiz = quizRepository.findByUserUserIdAndQuizId(userId, quizId);
        List<Question> questions = questionRepository.findAllByQuizQuizId(quiz.get().getQuizId());
        Optional<Question> question = questions.stream().filter(q -> q.getQuestionId().equals(questionId)).findFirst();
        return question.get().getChoises();
    }

    public Optional<Choise> getChoiseByUserIdAndQuizIdAndQuestionIdAndChoiseId(Long userId, Long quizId, Long questionId, Long choiseId) {
        // Chercher le quiz par son ID et l'ID de l'utilisateur
        Optional<Quiz> quiz = quizRepository.findByUserUserIdAndQuizId(userId, quizId);

        // Vérifier si le quiz est présent
        if (quiz.isPresent()) {
            // Récupérer le quiz trouvé à partir de l'Optional
            Quiz foundQuiz = quiz.get();

            // Chercher toutes les questions associées au quiz
            List<Question> questions = questionRepository.findAllByQuizQuizId(foundQuiz.getQuizId());

            // Vérifier si des questions sont associées au quiz
            if (!questions.isEmpty()) {
                // Chercher la question spécifique par son ID
                Optional<Question> question = questions.stream().filter(q -> q.getQuestionId().equals(questionId)).findFirst();

                // Vérifier si la question est présente
                if (question.isPresent()) {
                    // Récupérer la question trouvée à partir de l'Optional
                    Question foundQuestion = question.get();

                    // Récupérer toutes les réponses associées à la question
                    List<Choise> choises = foundQuestion.getChoises();

                    // Chercher la réponse spécifique par son ID
                    Optional<Choise> choise = choises.stream().filter(c -> c.getChoiseId().equals(choiseId)).findFirst();

                    // Retourner la réponse si elle est présente, sinon retourner null
                    return choise;
                } else {
                    // Retourner null si la question n'est pas présente
                    return null; // ou lancez une autre exception appropriée ou retournez une valeur par défaut
                }
            } else {
                // Retourner null si aucune question n'est associée au quiz
                return null;
            }
        } else {
            // Retourner null si le quiz n'est pas présent
            return null;
        }
    }


    public void deleteChoise(Long userId, Long quizId, Long questionId, Long choiseId) {
        Optional<Choise> choise = getChoiseByUserIdAndQuizIdAndQuestionIdAndChoiseId(userId, quizId, questionId, choiseId);
        choise.ifPresent(value->choiseRepository.delete(value));
    }
    public Optional<Choise> updateWithPutValueChoise(Long userId, Long quizId, Long questionId, Long choiseId, Choise newChoise) {
        Optional<Choise> choiseOptional = choiseRepository.findByChoiseIdAndQuestionQuestionIdAndQuestionQuizQuizIdAndQuestionQuizUserUserId(choiseId, questionId, quizId, userId);
        if(choiseOptional.isPresent()) {
            Choise existingChoise = choiseOptional.get();

            // Mise à jour du choix
            existingChoise.setText(newChoise.getText());
            existingChoise.setRank(newChoise.getRank());

            Choise updateChoise = choiseRepository.save(existingChoise);
            return Optional.of(choiseRepository.save(updateChoise));
        }
        return Optional.empty(); // On renvoie un instance vide de la classe Optional
    }

    public Choise addChoise(Long userId, Long quizId, Long questionId, Choise choise) {
        Optional<Quiz> quiz = quizRepository.findByUserUserIdAndQuizId(userId, quizId);
        if (quiz.isPresent()) {
            List<Question> questions = questionRepository.findAllByQuizQuizId(quiz.get().getQuizId());
            Optional<Question> question = questions.stream().filter(q -> q.getQuestionId().equals(questionId)).findFirst();
            if (question.isPresent()) {
                choise.setQuestion(question.get());
                return choiseRepository.save(choise);
            }
        } else {
            return null;
        }
        return choise;
    }

    public ResponseEntity<Optional<Choise>> updateWithPathValueQuiz(Long userId, Long quizId, Long questionId, Long choiseId, Map<String, Object> updateChoise) {
        Optional<Choise> choise = choiseRepository.findByChoiseIdAndQuestionQuestionIdAndQuestionQuizQuizIdAndQuestionQuizUserUserId(choiseId, questionId, quizId, userId);
        if(choise.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Mise à jour des données de l'utilisateur
        if(updateChoise.containsKey("text")){
            choise.get().setText((String) updateChoise.get("text"));
        }
        if(updateChoise.containsKey("rank")){
            choise.get().setRank((int) updateChoise.get("rank"));
        }

        choiseRepository.save(choise.get());
        return ResponseEntity.ok(choise);
    }
}
