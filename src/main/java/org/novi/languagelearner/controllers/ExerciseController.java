package org.novi.languagelearner.controllers;

import org.novi.languagelearner.dtos.Exercise.ExerciseRequestDTO;
import org.novi.languagelearner.dtos.Exercise.ExerciseResponseDTO;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ExerciseResponseDTO createExercise(@RequestBody ExerciseRequestDTO requestDTO) {
        return exerciseService.createExercise(requestDTO);
    }



    @PutMapping("/{id}")
    public ExerciseResponseDTO updateExercise(@PathVariable Long id, @RequestBody ExerciseRequestDTO requestDTO) {

        return exerciseService.updateExercise(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExercise(@PathVariable Long id) {
        try {
            exerciseService.deleteExercise(id);
            return ResponseEntity.ok().body(String.format("exercise with id %d is deleted", id));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
