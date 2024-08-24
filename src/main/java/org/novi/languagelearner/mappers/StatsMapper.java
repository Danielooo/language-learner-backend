package org.novi.languagelearner.mappers;

import org.novi.languagelearner.dtos.Exercise.ExerciseResponseDTO;
import org.novi.languagelearner.dtos.Stats.StatsResponseDTO;
import org.novi.languagelearner.dtos.UserInputAnswer.UserInputAnswerResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.UserInputAnswer;
import org.novi.languagelearner.repositories.ExerciseRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatsMapper {

    private final ExerciseRepository exerciseRepository;
    private final UserMapper userMapper;
    private final UserInputAnswerMapper userInputAnswerMapper;

    public StatsMapper(ExerciseRepository exerciseRepository, UserMapper userMapper, UserInputAnswerMapper userInputAnswerMapper) {
        this.exerciseRepository = exerciseRepository;
        this.userMapper = userMapper;
        this.userInputAnswerMapper = new UserInputAnswerMapper();
    }

    public StatsResponseDTO toStatResponseDTO(Exercise exercise, List<UserInputAnswer> userInputAnswers) {
        StatsResponseDTO statsResponseDTO = new StatsResponseDTO();

        // create and fill exerciseResponseDTO
        ExerciseResponseDTO exerciseResponseDTO = new ExerciseResponseDTO();
        exerciseResponseDTO.setId(exercise.getId());
        exerciseResponseDTO.setQuestion(exercise.getQuestion());
        exerciseResponseDTO.setAnswer(exercise.getAnswer());

        // create and fill List of userInputAnswerResponseDTOs
        List<UserInputAnswerResponseDTO> userInputAnswerResponseDTOs = userInputAnswers.stream()
                .map(userInputAnswerMapper::mapToUserInputAnswerResponseDTO)
                .collect(Collectors.toList());

        // put exerciseResponseDTO and List of userInputAnswerResponseDTOs in statsResponseDTO



        statsResponseDTO.setExerciseResponseDTO(exerciseResponseDTO);
        statsResponseDTO.setUserInputAnswersResponseDTOs(userInputAnswerResponseDTOs);
        return statsResponseDTO;
    }


}
