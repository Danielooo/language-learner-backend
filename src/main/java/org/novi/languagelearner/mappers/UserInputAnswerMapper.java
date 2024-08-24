package org.novi.languagelearner.mappers;

import org.novi.languagelearner.dtos.UserInputAnswer.UserInputAnswerFeedbackResponseDTO;
import org.novi.languagelearner.dtos.UserInputAnswer.UserInputAnswerResponseDTO;

import org.novi.languagelearner.entities.UserInputAnswer;
import org.springframework.stereotype.Component;

@Component
public class UserInputAnswerMapper {

    //TODO: Feature build; Stat just contains the user input, should contain the Feedback dto, so it's clear how the user is doing

    public UserInputAnswerFeedbackResponseDTO mapToAnswerSubmitResponseDTO(String exerciseAnswer, boolean isCorrect, String feedback) {
        UserInputAnswerFeedbackResponseDTO userInputAnswerFeedbackResponseDTO = new UserInputAnswerFeedbackResponseDTO();
        userInputAnswerFeedbackResponseDTO.setExerciseAnswer(exerciseAnswer);
        userInputAnswerFeedbackResponseDTO.setIsCorrect(isCorrect);
        userInputAnswerFeedbackResponseDTO.setFeedback(feedback);

        return userInputAnswerFeedbackResponseDTO;
    }

    // create responseDTO for UserInputAnswer
    public UserInputAnswerResponseDTO mapToUserInputAnswerResponseDTO(UserInputAnswer userInputAnswer) {
        UserInputAnswerResponseDTO userInputAnswerResponseDTO = new UserInputAnswerResponseDTO();
        userInputAnswerResponseDTO.setExerciseAnswer(userInputAnswer.getUserInput());

        return userInputAnswerResponseDTO;
    }
}
