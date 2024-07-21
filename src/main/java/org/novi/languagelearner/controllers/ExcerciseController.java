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

    @Autowired
    private ExcerciseService excerciseService;

    // Postmapping
    @PostMapping
    public ExcerciseResponseDTO createExcercise(@RequestBody ExcerciseRequestDTO requestDTO) {
        return excerciseService.createExcercise(requestDTO);
    }

    // Getmapping
    @GetMapping
    public List<ExcerciseResponseDTO> getAllExcercises() {
        return excerciseService.getAllExcercises();
    }
}
