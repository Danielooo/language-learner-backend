package org.novi.languagelearner.services;

import org.novi.languagelearner.dtos.ExcerciseRequestDTO;
import org.novi.languagelearner.dtos.ExcerciseResponseDTO;
import org.novi.languagelearner.entities.Excercise;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.mappers.ExcerciseMapper;
import org.novi.languagelearner.repositories.ExcerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExcerciseService {

    private final ExcerciseRepository excerciseRepository;
    private final ExcerciseMapper excerciseMapper;

    @Autowired
    public ExcerciseService(ExcerciseRepository excerciseRepository, ExcerciseMapper excerciseMapper) {
        this.excerciseRepository = excerciseRepository;
        this.excerciseMapper = excerciseMapper;
    }

    public ExcerciseResponseDTO createExcercise(ExcerciseRequestDTO requestDTO) {
        Excercise excercise = excerciseMapper.toEntity(requestDTO);
        Excercise savedExcercise = excerciseRepository.save(excercise);
        return excerciseMapper.toResponseDTO(savedExcercise) ;
    }

    public List<ExcerciseResponseDTO> getAllExcercises() {
        List<Excercise> excercises = excerciseRepository.findAll();
        return excercises.stream().map(excerciseMapper::toResponseDTO).collect(Collectors.toList());
    }

    public void deleteExcercise(Long id) {
        excerciseRepository.deleteById(id);
    }

    public ExcerciseResponseDTO updateExcercise(Long id, ExcerciseRequestDTO requestDTO) {
        Optional<Excercise> excerciseOptional = excerciseRepository.findById(id);
        if (excerciseOptional.isPresent()) {
            Excercise updatedExcercise = excerciseMapper.toEntity(requestDTO);
            updatedExcercise.setId(id);
            Excercise persistedExcercise =  excerciseRepository.save(updatedExcercise);

            return excerciseMapper.toResponseDTO(persistedExcercise);

        } else {
            throw new RecordNotFoundException("Exercise id not found in database");
        }
    }

//    public ExcerciseResponseDTO updateExcercise(Long id, ExcerciseRequestDTO requestDTO) {

        // Retrieve the existing Excercise entity from the repository using the provided id.
        // Update the specific field(s) of the retrieved Excercise entity.
        // Save the updated Excercise entity back to the repository.
        // Convert the updated Excercise entity to a ExcerciseResponseDTO and return it.

//    }


}
