package org.novi.languagelearner.controllers;

import org.novi.languagelearner.dtos.ExcerciseRequestDTO;
import org.novi.languagelearner.dtos.ExcerciseResponseDTO;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.services.ExcerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/excercises")
public class ExcerciseController {

    private final ExcerciseService excerciseService;

    @Autowired
    public ExcerciseController(ExcerciseService excerciseService) {
        this.excerciseService = excerciseService;
    }

    @PostMapping
    public ExcerciseResponseDTO createExcercise(@RequestBody ExcerciseRequestDTO requestDTO) {
        return excerciseService.createExcercise(requestDTO);
    }

    @GetMapping
    public List<ExcerciseResponseDTO> getAllExcercises() {
        return excerciseService.getAllExcercises();
    }

    // TODO: putmapping updateExcercise
    @PutMapping("/{id}")
    public ExcerciseResponseDTO updateExcercise(@PathVariable Long id, @RequestBody ExcerciseRequestDTO requestDTO) {

        return excerciseService.updateExcercise(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExcercise(@PathVariable Long id) {
        try {
            excerciseService.deleteExcercise(id);
            return ResponseEntity.ok().body(String.format("Excercise with id %d is deleted", id));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // TODO: Bulk excercise creation PostMapping

}
