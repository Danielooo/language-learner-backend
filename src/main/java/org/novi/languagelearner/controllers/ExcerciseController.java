package org.novi.languagelearner.controllers;

import org.novi.languagelearner.dtos.ExcerciseRequestDTO;
import org.novi.languagelearner.dtos.ExcerciseResponseDTO;
import org.novi.languagelearner.services.ExcerciseService;
import org.springframework.beans.factory.annotation.Autowired;
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
}