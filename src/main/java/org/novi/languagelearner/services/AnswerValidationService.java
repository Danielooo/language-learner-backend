package org.novi.languagelearner.services;

import org.novi.languagelearner.dtos.AnswerValidationRequestDTO;
import org.novi.languagelearner.dtos.AnswerValidationResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.Stat;
import org.novi.languagelearner.repositories.ExerciseRepository;
import org.springframework.stereotype.Service;

import static org.novi.languagelearner.utils.AnswerCompare.answerWrongOrRight;
import static org.novi.languagelearner.utils.AnswerCompare.getFeedbackAfterCompare;

@Service
public class AnswerValidationService {

    private final ExerciseService exerciseService;
    private final StatService statService;

    public AnswerValidationService(ExerciseService exerciseService, StatService statService) {
        this.exerciseService = exerciseService;
        this.statService = statService;
    }

    // TODO: Ask Frans; misschien logisch om hier een in de responseDTO een Stat en Feedback mee te geven? Of is dat teveel dataverkeer? >> Geen Stat teruggeven, Feedback is prima
    public AnswerValidationResponseDTO processAnswer(AnswerValidationRequestDTO answerValidationRequestDTO) {

        AnswerValidationResponseDTO answerValidationResponseDTO = new AnswerValidationResponseDTO();

        Exercise exercise = exerciseService.getExerciseById(answerValidationRequestDTO.getExerciseId());

        boolean isCorrect = answerWrongOrRight(answerValidationRequestDTO.getUserInput(), exercise.getAnswer());

        Stat savedStat = statService.updateStatAfterExerciseAttempt(exercise.getId(), answerValidationRequestDTO.getExerciseId(), isCorrect);

        //TODO: Logisch om dit via de mapper te doen?
        answerValidationResponseDTO.setIsCorrect(answerWrongOrRight(answerValidationRequestDTO.getUserInput(), exercise.getAnswer()));
        answerValidationResponseDTO.setFeedback(getFeedbackAfterCompare(answerValidationRequestDTO.getUserInput(), exercise.getAnswer()));

        return answerValidationResponseDTO;
    }

    
}
