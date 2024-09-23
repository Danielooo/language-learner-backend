package org.novi.languagelearner.controllers;

import jakarta.validation.Valid;
import org.novi.languagelearner.dtos.Exercise.ExerciseRequestDTO;
import org.novi.languagelearner.dtos.Exercise.ExerciseResponseDTO;
import org.novi.languagelearner.entities.Exercise;
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




    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteExerciseById(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            exerciseService.deleteExercise(userName, id);

            return ResponseEntity.ok().body(String.format("Exercise deleted with id: %d", id));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body((String.format("Something went wrong when deleting exercise with id: %d", id)));
        }
    }

    @GetMapping("admin/all")
    public ResponseEntity<?> getAllExercisesAsAdmin() {
        try {

            List<ExerciseResponseDTO> exerciseDTOs = exerciseService.getAllExercises();

            return ResponseEntity.ok().body(exerciseDTOs);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body("Something went wrong with retrieving all exercises");
        }
    }


    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteExerciseAsAdmin(@PathVariable Long id) {
        try {

            exerciseService.deleteExerciseAsAdmin(id);
            return ResponseEntity.ok().body(String.format("exercise with id %d is deleted", id));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PatchMapping("/admin/update/{id}")
    public ResponseEntity<?> updateExercise(@PathVariable Long id, @RequestBody @Valid ExerciseRequestDTO requestDTO) {
        try {

                return ResponseEntity.ok().body(exerciseService.updateExercise(id, requestDTO));
        } catch (BadRequestException e) {
            throw new BadRequestException("Something went wrong with updating the exercise");
        }
    }
}
