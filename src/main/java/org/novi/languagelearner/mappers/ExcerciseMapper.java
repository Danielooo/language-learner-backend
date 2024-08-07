package org.novi.languagelearner.mappers;

import org.novi.languagelearner.dtos.ExcerciseRequestDTO;
import org.novi.languagelearner.dtos.ExcerciseResponseDTO;
import org.novi.languagelearner.entities.Excercise;
import org.springframework.stereotype.Component;

@Component
public class ExcerciseMapper {

    public Excercise toEntity(ExcerciseRequestDTO requestDTO) {
        Excercise excercise = new Excercise();
        excercise.setQuestion(requestDTO.getQuestion());
        excercise.setAnswer(requestDTO.getAnswer());
        return excercise;
    }


    public ExcerciseResponseDTO toResponseDTO(Excercise excercise) {
        ExcerciseResponseDTO responseDTO = new ExcerciseResponseDTO();
        responseDTO.setId(excercise.getId());
        responseDTO.setQuestion(excercise.getQuestion());
        responseDTO.setAnswer(excercise.getAnswer());
        return responseDTO;
    }

//    // ExercisePutDTO
//    public Excercise toExcercisePutDTO(ExcercisePutDTO excercisePutDTO) {
//
//
//    }

}
