package org.novi.languagelearner.controllers;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.novi.languagelearner.dtos.ExerciseAttemptRequestDTO;
import org.novi.languagelearner.dtos.ExerciseAttemptResponseDTO;
import org.novi.languagelearner.dtos.StatResponseDTO;
import org.novi.languagelearner.entities.Stat;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.repositories.StatRepository;
import org.novi.languagelearner.services.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatController {

    private StatService statService;

    @Autowired
    public StatController(StatService statService) {
        this.statService = statService;
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getStatById(Long id) {
        try {
            Stat stat = statService.getStatById(id);
            return ResponseEntity.ok(stat);
        } catch ( RecordNotFoundException e ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch ( BadRequestException e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch ( Exception e ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred, bruh");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllStats() {
        try {
            return ResponseEntity.ok(statService.getAllStats());
        } catch ( RecordNotFoundException e ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch ( BadRequestException e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch ( Exception e ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred, bruh");
        }
    }

    @GetMapping("/user/all")
    public ResponseEntity<?> getAllStatsOfUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            return ResponseEntity.ok(statService.getAllStatsOfUser(username));
        } catch ( RecordNotFoundException e ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch ( BadRequestException e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch ( Exception e ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred, bruh");
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getFilteredStats(@RequestParam(required = false) List<String> username, @RequestParam(required = false) List<Long> exerciseId) {
        try {
            List<StatResponseDTO> stats = statService.getFilteredStats(username, exerciseId);
            return ResponseEntity.ok(stats);

        } catch ( RecordNotFoundException e ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch ( BadRequestException e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch ( Exception e ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred, bruh");
        }
    }

    // TODO: complete this method in service
    @PostMapping("/submit-exercise-attempt")
    public ResponseEntity<?> submitExerciseAttempt(@RequestBody ExerciseAttemptRequestDTO requestDTO) {
        try {
            ExerciseAttemptResponseDTO responseDTO = statService.submitExerciseAttempt(requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch ( RecordNotFoundException e ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch ( BadRequestException e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch ( Exception e ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred, bruh");
        }
    }

    // TODO: Frans answered; GetMapping voor de data die je wil. Kan 1 request methode zijn die in de repository de juiste query doet >> bijv @Query("SELECT * FROM stat WHERE user_id = :userId AND exercise_id = :exerciseId"). Je kan in de query dynamisch injecteren. Er is geen Stat repository, want Stat hoeft niet te worden opgeslagen. // Eigenlijk is Stats een model daardoor, want entities worden opgeslagen in de database. Dus je kan een service maken die de data ophaalt en dan een DTO teruggeeft



}
