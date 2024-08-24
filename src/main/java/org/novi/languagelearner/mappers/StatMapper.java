package org.novi.languagelearner.mappers;

import org.novi.languagelearner.dtos.Unsorted.ExerciseResponseDTO;
import org.novi.languagelearner.dtos.Stat.StatResponseDTO;
import org.novi.languagelearner.dtos.UserInputAnswer.UserInputAnswerResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.UserInputAnswer;
import org.novi.languagelearner.repositories.ExerciseRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatMapper {

    private final ExerciseRepository exerciseRepository;
    private final UserMapper userMapper;
    private final UserInputAnswerMapper userInputAnswerMapper;

    public StatMapper (ExerciseRepository exerciseRepository, UserMapper userMapper, UserInputAnswerMapper userInputAnswerMapper) {
        this.exerciseRepository = exerciseRepository;
        this.userMapper = userMapper;
        this.userInputAnswerMapper = new UserInputAnswerMapper();
    }

    public StatResponseDTO toStatResponseDTO(Exercise exercise, List<UserInputAnswer> userInputAnswers) {
        StatResponseDTO statResponseDTO = new StatResponseDTO();

        // create and fill exerciseResponseDTO
        ExerciseResponseDTO exerciseResponseDTO = new ExerciseResponseDTO();
        exerciseResponseDTO.setId(exercise.getId());
        exerciseResponseDTO.setQuestion(exercise.getQuestion());
        exerciseResponseDTO.setAnswer(exercise.getAnswer());

        // create and fill List of userInputAnswerResponseDTOs
        List<UserInputAnswerResponseDTO> userInputAnswerResponseDTOs = userInputAnswers.stream()
                .map(userInputAnswerMapper::mapToUserInputAnswerResponseDTO)
                .collect(Collectors.toList());

        // put exerciseResponseDTO and List of userInputAnswerResponseDTOs in statResponseDTO



        statResponseDTO.setExerciseResponseDTO(exerciseResponseDTO);
        statResponseDTO.setUserInputAnswersResponseDTOs(userInputAnswerResponseDTOs);
        return statResponseDTO;
    }


}
