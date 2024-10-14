package org.novi.languagelearner.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.novi.languagelearner.dtos.Exercise.ExerciseRequestDTO;
import org.novi.languagelearner.dtos.Exercise.ExerciseResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.mappers.ExerciseMapper;
import org.novi.languagelearner.repositories.ExerciseRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest  {

    @Mock
    ExerciseRepository exerciseRepository;

    @Mock
    ExerciseMapper exerciseMapper;

    @InjectMocks
    ExerciseService exerciseService;

    @Test
    public void getExerciseById() {

        // ARRANGE
        var question = "Hello";
        var answer = "Salve";

        Exercise exercise = new Exercise();
        exercise.setQuestion(question);
        exercise.setAnswer(answer);

        // repo wordt niet echt aangeroepen. Resultaat wordt gegeven. Ter isolatie van deze test. Anders zou er misschien iets mis zijn met de repository.
        when(exerciseRepository.findById(anyLong())).thenReturn(Optional.of(exercise));

        // ACT
        var exerciseResult = exerciseService.getExerciseById(1L);

        // ASSERT
        assertEquals(question, exerciseResult.getQuestion());
        assertEquals(answer, exerciseResult.getAnswer());
    }


    @Test
    public void testGetExerciseById_Success() {
        // Arrange
        Long exerciseId = 1L;
        Exercise exercise = new Exercise("Sample Question", "Sample Answer");

        // Create a spy to mock getId() method, as we cannot set the ID directly
        Exercise spyExercise = Mockito.spy(exercise);
        Mockito.doReturn(exerciseId).when(spyExercise).getId();

        // Mock repository to return an exercise when findById is called
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(spyExercise));

        // Act
        Exercise result = exerciseService.getExerciseById(exerciseId);

        // Assert
        assertNotNull(result);
        assertEquals(exerciseId, result.getId());
        assertEquals("Sample Question", result.getQuestion());
        assertEquals("Sample Answer", result.getAnswer());

        verify(exerciseRepository, times(1)).findById(exerciseId);
    }

    @Test
    public void getExerciseById_ExerciseNotFound() {
        // Arrange
        Long exerciseId = 1L;

        // Mock repository to return an empty optional when findById is called
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> {
            exerciseService.getExerciseById(exerciseId);
        });

        verify(exerciseRepository, times(1)).findById(exerciseId);
    }


    @Test
    public void deleteExercise_Succes() {
        // ARRANGE
        var question = "Hello";
        var answer = "Salve";

        Exercise exercise = new Exercise();
        exercise.setQuestion(question);
        exercise.setAnswer(answer);

        when(exerciseRepository.findExerciseByIdAndUserName("Daniel", 1L)).thenReturn(Optional.of(exercise));

        // ACT
        exerciseService.deleteExercise("Daniel", 1L);

        // ASSERT
        // Geen exception betekent dat de test geslaagd is.
    }


    @Test
    public void deleteExerciseAsAdmin_ShouldDeleteIfExists() {
        // Arrange
        Long exerciseId = 1L;
        when(exerciseRepository.existsById(exerciseId)).thenReturn(true);

        // Act
        exerciseService.deleteExerciseAsAdmin(exerciseId);

        // Assert
        verify(exerciseRepository, times(1)).deleteById(exerciseId);
    }


    @Test
   public void deleteExerciseAsAdmin_ShouldThrowExceptionIfNotFound() {
        // Arrange
        Long exerciseId = 1L;
        when(exerciseRepository.existsById(exerciseId)).thenReturn(false);

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> exerciseService.deleteExerciseAsAdmin(exerciseId));
    }

    @Test
    public void updateExercise_ShouldUpdateExerciseIfExists() {
        // Arrange
        Long exerciseId = 1L;
        ExerciseRequestDTO requestDTO = new ExerciseRequestDTO();
        requestDTO.setQuestion("Updated Question");
        requestDTO.setAnswer("Updated Answer");

        Exercise existingExercise = new Exercise();
        existingExercise.setQuestion("Old Question");
        existingExercise.setAnswer("Old Answer");

        Exercise updatedExercise = new Exercise();
        updatedExercise.setQuestion(requestDTO.getQuestion());
        updatedExercise.setAnswer(requestDTO.getAnswer());

        Exercise persistedExercise = new Exercise();
        persistedExercise.setQuestion(requestDTO.getQuestion());
        persistedExercise.setAnswer(requestDTO.getAnswer());

        ExerciseResponseDTO responseDTO = new ExerciseResponseDTO();
        responseDTO.setQuestion(requestDTO.getQuestion());
        responseDTO.setAnswer(requestDTO.getAnswer());

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(existingExercise));
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(persistedExercise);
        when(exerciseMapper.mapToResponseDTO(any(Exercise.class))).thenReturn(responseDTO);

        // Act
        ExerciseResponseDTO result = exerciseService.updateExercise(exerciseId, requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(requestDTO.getQuestion(), result.getQuestion());
        assertEquals(requestDTO.getAnswer(), result.getAnswer());
        verify(exerciseRepository, times(1)).save(any(Exercise.class));
    }

    @Test
    public void updateExercise_ShouldThrowExceptionIfExerciseDoesNotExist() {
        // Arrange
        Long exerciseId = 1L;
        ExerciseRequestDTO requestDTO = new ExerciseRequestDTO();
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> exerciseService.updateExercise(exerciseId, requestDTO));
    }

    @Test
    public void getAllExercises_ShouldGetAllExercises() {
        // Arrange
        Exercise exercise1 = new Exercise();
        exercise1.setQuestion("Water");
        exercise1.setAnswer("Acqua");

        Exercise exercise2 = new Exercise();
        exercise2.setQuestion("Pardon");
        exercise2.setAnswer("Mi scuzi");

        ExerciseResponseDTO responseDTO1 = new ExerciseResponseDTO();
        responseDTO1.setQuestion("Water");
        responseDTO1.setAnswer("Acqua");

        ExerciseResponseDTO responseDTO2 = new ExerciseResponseDTO();
        responseDTO2.setQuestion("Pardon");
        responseDTO2.setAnswer("Mi scuzi");

        when(exerciseRepository.findAll()).thenReturn(List.of(exercise1, exercise2));
        when(exerciseMapper.mapToResponseDTO(exercise1)).thenReturn(responseDTO1);
        when(exerciseMapper.mapToResponseDTO(exercise2)).thenReturn(responseDTO2);

        // Act
        List<ExerciseResponseDTO> result = exerciseService.getAllExercises();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Water", result.get(0).getQuestion());
        assertEquals("Acqua", result.get(0).getAnswer());
        assertEquals("Pardon", result.get(1).getQuestion());
        assertEquals("Mi scuzi", result.get(1).getAnswer());
    }

    @Test
    public void getAllExercises_ShouldThrowExceptionIfRecordNotFound() {
        // Arrange
        when(exerciseRepository.findAll()).thenReturn(List.of());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> exerciseService.getAllExercises());
    }
}