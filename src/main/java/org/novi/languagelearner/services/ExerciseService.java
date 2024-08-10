package org.novi.languagelearner.services;

import org.novi.languagelearner.dtos.ExerciseRequestDTO;
import org.novi.languagelearner.dtos.ExerciseResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.mappers.ExerciseMapper;
import org.novi.languagelearner.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
    }

    public ExerciseResponseDTO createExercise(ExerciseRequestDTO requestDTO) {
        Exercise exercise = exerciseMapper.toEntity(requestDTO);
        Exercise savedExercise = exerciseRepository.save(exercise);
        return exerciseMapper.toResponseDTO(savedExercise) ;
    }

    public List<ExerciseResponseDTO> getAllExercises() {
        List<Exercise> exercises = exerciseRepository.findAll();
        return exercises.stream().map(exerciseMapper::toResponseDTO).collect(Collectors.toList());
    }

    public void deleteExercise(Long id) {
        if (exerciseRepository.existsById(id)) {
            exerciseRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Exercise id is not found in database");
        }

    }

    public ExerciseResponseDTO updateExercise(Long id, ExerciseRequestDTO requestDTO) {
        Optional<Exercise> exerciseOptional = exerciseRepository.findById(id);
        if (exerciseOptional.isPresent()) {
            Exercise updatedExercise = exerciseMapper.toEntity(requestDTO);
            updatedExercise.setId(id);
            Exercise persistedExercise =  exerciseRepository.save(updatedExercise);

            return exerciseMapper.toResponseDTO(persistedExercise);

        } else {
            throw new RecordNotFoundException("Exercise id not found in database");
        }
    }

//    public exerciseResponseDTO updateexercise(Long id, exerciseRequestDTO requestDTO) {

        // Retrieve the existing exercise entity from the repository using the provided id.
        // Update the specific field(s) of the retrieved exercise entity.
        // Save the updated exercise entity back to the repository.
        // Convert the updated exercise entity to a exerciseResponseDTO and return it.

//    }


}
