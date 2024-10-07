package org.novi.languagelearner.mappers;

import org.novi.languagelearner.dtos.Stats.StatsOfExerciseResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.UserInputAnswer;
import org.novi.languagelearner.repositories.ExerciseRepository;
import org.novi.languagelearner.utils.AnswerCompare;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatsMapper {

    private final ExerciseRepository exerciseRepository;
    private final UserMapper userMapper;
    private final UserInputAnswerMapper userInputAnswerMapper;

    public StatsMapper(ExerciseRepository exerciseRepository, UserMapper userMapper, UserInputAnswerMapper userInputAnswerMapper) {
        this.exerciseRepository = exerciseRepository;
        this.userMapper = userMapper;
        this.userInputAnswerMapper = userInputAnswerMapper;
    }

    public StatsOfExerciseResponseDTO toStatResponseDTO(Exercise exercise, List<UserInputAnswer> userInputAnswers) {
        StatsOfExerciseResponseDTO responseDTO = new StatsOfExerciseResponseDTO();

        responseDTO.setQuestion(exercise.getQuestion());
        responseDTO.setAnswer(exercise.getAnswer());
        responseDTO.setTimesRight(0);
        responseDTO.setTimesWrong(0);

        for (UserInputAnswer userInputAnswer : userInputAnswers ) {
            AnswerCompare.AnswerState answer = AnswerCompare.answerWrongOrRight(exercise.getAnswer(), userInputAnswer.getUserInput());
            if (answer.allGood() || answer.ignoreAccentGood()) {

                responseDTO.setTimesRight(responseDTO.getTimesRight() + 1);
            } else {
                responseDTO.setTimesWrong(responseDTO.getTimesWrong() + 1);
            }
        }


        // create and fill exerciseResponseDTO
        return responseDTO;
    }


}
