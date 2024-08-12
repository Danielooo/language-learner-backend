package org.novi.languagelearner.mappers;

import org.novi.languagelearner.dtos.StatResponseDTO;
import org.novi.languagelearner.entities.Stat;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatMapper {

    private final ExerciseMapper exerciseMapper;
    private final UserMapper userMapper;

    public StatMapper (ExerciseMapper exerciseMapper, ExerciseMapper exerciseMapper1, UserMapper userMapper) {
        this.exerciseMapper = exerciseMapper1;
        this.userMapper = userMapper;
    }

    public List<StatResponseDTO> mapToListOfResponseDTOs(List<Stat> stats) {

        return stats.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public StatResponseDTO mapToResponseDTO(Stat stat) {
        var statResponseDTO = new StatResponseDTO();
        statResponseDTO.setExerciseResponseDTO(exerciseMapper.mapToResponseDTO(stat.getExercise()));
        statResponseDTO.setUsername(stat.getUser().getUserName());

        statResponseDTO.setTimesRight(stat.getTimesRight());
        statResponseDTO.setTimesWrong(stat.getTimesWrong());

        return statResponseDTO;
    }

    // mapToEntity


    // mapToResponseDTO
    // mapToResponseDTOList


}
