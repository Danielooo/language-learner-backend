package org.novi.languagelearner.mappers;

import org.novi.languagelearner.dtos.ExerciseRequestDTO;
import org.novi.languagelearner.dtos.ExerciseResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper {

    public Exercise toEntity(ExerciseRequestDTO requestDTO) {
        Exercise exercise = new Exercise();
        exercise.setQuestion(requestDTO.getQuestion());
        exercise.setAnswer(requestDTO.getAnswer());
        return exercise;
    }


    public ExerciseResponseDTO mapToResponseDTO(Exercise exercise) {
        ExerciseResponseDTO responseDTO = new ExerciseResponseDTO();
        responseDTO.setId(exercise.getId());
        responseDTO.setQuestion(exercise.getQuestion());
        responseDTO.setAnswer(exercise.getAnswer());
        return responseDTO;
    }

//    // ExercisePutDTO
//    public exercise toexercisePutDTO(exercisePutDTO exercisePutDTO) {
//
//
//    }

}
