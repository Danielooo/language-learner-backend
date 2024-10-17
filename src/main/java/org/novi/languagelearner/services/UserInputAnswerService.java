package org.novi.languagelearner.services;

import org.novi.languagelearner.dtos.UserInputAnswer.UserInputAnswerFeedbackResponseDTO;
import org.novi.languagelearner.dtos.UserInputAnswer.UserInputAnswerRequestDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.entities.UserInputAnswer;
import org.novi.languagelearner.mappers.UserInputAnswerMapper;
import org.novi.languagelearner.repositories.UserInputAnswerRepository;
import org.springframework.stereotype.Service;

import static org.novi.languagelearner.utils.AnswerCompare.answerWrongOrRight;
import static org.novi.languagelearner.utils.AnswerCompare.getFeedbackAfterCompare;

@Service
public class UserInputAnswerService {

    private final ExerciseService exerciseService;
    private final UserInputAnswerRepository userInputAnswerRepository;
    private final UserService userService;
    private final UserInputAnswerMapper userInputAnswerMapper;

    public UserInputAnswerService(ExerciseService exerciseService, UserInputAnswerRepository userInputAnswerRepository, UserService userService, UserInputAnswerMapper userInputAnswerMapper) {
        this.exerciseService = exerciseService;
        this.userInputAnswerRepository = userInputAnswerRepository;
        this.userService = userService;
        this.userInputAnswerMapper = userInputAnswerMapper;
    }

    public UserInputAnswerFeedbackResponseDTO submitAnswer(UserInputAnswerRequestDTO userInputAnswerRequestDTO) {
        Exercise exercise = exerciseService.getExerciseById(userInputAnswerRequestDTO.getExerciseId());
        User user = userService.getUserByUserName(userInputAnswerRequestDTO.getUserName());

        UserInputAnswer userInputAnswer = new UserInputAnswer();
        userInputAnswer.setExercise(exercise);
        userInputAnswer.setUser(user);
        userInputAnswer.setUserInput(userInputAnswerRequestDTO.getUserInput());

        userInputAnswerRepository.save(userInputAnswer);

        // UserInputAnswer check logic
        String correctAnswer = exercise.getAnswer();
        String userInput = userInputAnswer.getUserInput();


        // creates an response dto where the userInputAnswer is checked
        return userInputAnswerMapper.mapToAnswerSubmitResponseDTO(exercise.getAnswer(),
                answerWrongOrRight(exercise.getAnswer(), userInputAnswerRequestDTO.getUserInput()),
                getFeedbackAfterCompare(answerWrongOrRight(exercise.getAnswer(), userInputAnswerRequestDTO.getUserInput())));
    }

    public void deleteAnswer(Long id) {
        userInputAnswerRepository.deleteById(id);
    }
}
