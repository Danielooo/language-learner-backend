package org.novi.languagelearner.services;

import org.novi.languagelearner.dtos.Exercise.ExerciseRequestDTO;
import org.novi.languagelearner.dtos.Exercise.ExerciseResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.exceptions.AccessDeniedException;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.mappers.ExerciseMapper;
import org.novi.languagelearner.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO: Update service functions like group (exception handling)
// TODO: PutMapping updateExercise for partial update (naming 'put' and 'update')

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
    }


    public Exercise getExerciseById(Long id) {
        Optional<Exercise> exerciseOptional = exerciseRepository.findById(id);
        if (exerciseOptional.isPresent()) {
            return exerciseOptional.get();
        } else {
            throw new RecordNotFoundException("Exercise id not found in database");
        }
    }

    public void deleteExercise(String userName, Long id) {
        Exercise exercise = exerciseRepository.findExerciseByIdAndUserName(userName, id).orElseThrow(() -> new AccessDeniedException(String.format("Exercise with id: %d, does not belong to username: %s", id, userName)));

        exerciseRepository.deleteById(id);
    }


    public void deleteExerciseAsAdmin(Long id) {
        if (exerciseRepository.existsById(id)) {
            exerciseRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Exercise id is not found in database");
        }

    }

    public ExerciseResponseDTO updateExercise(Long id, ExerciseRequestDTO requestDTO) {
        Optional<Exercise> exerciseOptional = exerciseRepository.findById(id);
        if (exerciseOptional.isPresent()) {
            Exercise updatedExercise = exerciseOptional.get();
            if (updatedExercise.getUserInputAnswers() != null) {
                updatedExercise.getUserInputAnswers().clear();
            }
            updatedExercise.setQuestion(requestDTO.getQuestion());
            updatedExercise.setAnswer(requestDTO.getAnswer());

            Exercise persistedExercise =  exerciseRepository.save(updatedExercise);

            return exerciseMapper.mapToResponseDTO(persistedExercise);

        } else {
            throw new RecordNotFoundException("Exercise id not found in database");
        }
    }


    public List<ExerciseResponseDTO> getAllExercises() {
        List<Exercise> allExercises = exerciseRepository.findAll();

        if ( allExercises.isEmpty()) {
            throw new RecordNotFoundException("No exercises found in database");
        }

        List<ExerciseResponseDTO> responseDTOList = new ArrayList<>();
        for (Exercise exercise : allExercises) {
            responseDTOList.add(exerciseMapper.mapToResponseDTO(exercise));
        }

        return responseDTOList;
    }
}
