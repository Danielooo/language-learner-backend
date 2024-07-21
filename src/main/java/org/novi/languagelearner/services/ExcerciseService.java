package org.novi.languagelearner.services;

import org.novi.languagelearner.dtos.ExcerciseRequestDTO;
import org.novi.languagelearner.dtos.ExcerciseResponseDTO;
import org.novi.languagelearner.entities.Excercise;
import org.novi.languagelearner.mappers.ExcerciseMapper;
import org.novi.languagelearner.repositories.ExcerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExcerciseService {

    @Autowired
    private ExcerciseRepository repository;

    public ExcerciseResponseDTO createExcercise(ExcerciseRequestDTO requestDTO) {
        Excercise excercise = ExcerciseMapper.toEntity(requestDTO);
        Excercise savedExcercise = repository.save(excercise);
        return ExcerciseMapper.toResponseDTO(savedExcercise) ;
    }

    public List<ExcerciseResponseDTO> getAllExcercises() {
        List<Excercise> excercises = repository.findAll();
        return excercises.stream().map(ExcerciseMapper::toResponseDTO).collect(Collectors.toList());
    }

}
