package org.novi.languagelearner.controllers;

import org.novi.languagelearner.dtos.Exercise.ExerciseRequestDTO;
import org.novi.languagelearner.dtos.Exercise.ExerciseResponseDTO;
import org.novi.languagelearner.exceptions.BadRequestException;
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

    // ADMIN

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteExercise(@PathVariable Long id) {
        try {

            exerciseService.deleteExercise(id);
            return ResponseEntity.ok().body(String.format("exercise with id %d is deleted", id));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PutMapping("/admin/{id}")
    public ExerciseResponseDTO updateExercise(@PathVariable Long id, @RequestBody ExerciseRequestDTO requestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return exerciseService.updateExercise(id, requestDTO);
            } else {
                throw new BadRequestException("You are not authorized to update exercises");
            }
        } catch (BadRequestException e) {
            throw new BadRequestException("Something went wrong with updating the exercise");
        }
    }
}
