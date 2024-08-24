package org.novi.languagelearner.services;

import org.novi.languagelearner.dtos.Unsorted.ExerciseRequestDTO;
import org.novi.languagelearner.dtos.Unsorted.ExerciseResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.mappers.ExerciseMapper;
import org.novi.languagelearner.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ExerciseResponseDTO createExercise(ExerciseRequestDTO requestDTO) {
        Exercise exercise = exerciseMapper.toEntity(requestDTO);
        Exercise savedExercise = exerciseRepository.save(exercise);
        return exerciseMapper.mapToResponseDTO(savedExercise) ;
    }

    public Exercise getExerciseById(Long id) {
        Optional<Exercise> exerciseOptional = exerciseRepository.findById(id);
        if (exerciseOptional.isPresent()) {
            return exerciseOptional.get();
        } else {
            throw new RecordNotFoundException("Exercise id not found in database");
        }
    }

    // just get exercise without the userInputAnswers
    public Exercise getExerciseWithoutUserInputAnswersById(Long id) {

        Optional<Exercise> exerciseOptional = exerciseRepository.findExerciseWithoutUserInputAnswersById(id);
        if (exerciseOptional.isEmpty()) {
            throw new RecordNotFoundException("Exercise id not found in database");
        } else {
            return exerciseOptional.get();
        }
    }

    public List<ExerciseResponseDTO> getAllExercises() {
        List<Exercise> exercises = exerciseRepository.findAll();
        return exercises.stream().map(exerciseMapper::mapToResponseDTO).collect(Collectors.toList());
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

            return exerciseMapper.mapToResponseDTO(persistedExercise);

        } else {
            throw new RecordNotFoundException("Exercise id not found in database");
        }
    }




}
