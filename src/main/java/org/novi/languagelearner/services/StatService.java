package org.novi.languagelearner.services;

import org.novi.languagelearner.dtos.ExerciseAttemptRequestDTO;
import org.novi.languagelearner.dtos.ExerciseAttemptResponseDTO;
import org.novi.languagelearner.dtos.StatResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.Stat;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.mappers.StatMapper;
import org.novi.languagelearner.repositories.ExerciseRepository;
import org.novi.languagelearner.repositories.StatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// TODO: implement statMapper
// TODO: make adjustStat method that adjusts timesRight, timesWrong

@Service
public class StatService {

    private StatMapper statMapper;
    private StatRepository statRepository;
    private ExerciseRepository exerciseRepository;


    public StatService(StatMapper statMapper, StatRepository statRepository, ExerciseRepository exerciseRepository) {
        this.statMapper = statMapper;
        this.statRepository = statRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public Stat getStatById(Long id) {

        Optional<Stat> stat = statRepository.findById(id);
        if (stat.isEmpty()) {
            throw new RecordNotFoundException("No stat found with id: " + id);
        } else {
            return stat.get();
        }
    }

    public ExerciseAttemptResponseDTO submitExerciseAttempt(ExerciseAttemptRequestDTO requestDTO) {
        Optional<Exercise> exercise =  exerciseRepository.findExerciseById(requestDTO.getExerciseId());
        if (exercise.isEmpty()) {
            throw new RecordNotFoundException("No exercise found with id: " + requestDTO.getExerciseId());
        } else {
            // compare user input with correct answer
            Exercise exerciseFromRepo = exercise.get();
            ExerciseAttemptResponseDTO responseDTO = new ExerciseAttemptResponseDTO();

            // checking if answer is correct
            responseDTO.setCorrect(exerciseFromRepo.getAnswer().equals(requestDTO.getUserInput()));
            responseDTO.setExerciseRepositoryAnswer(exerciseFromRepo.getAnswer());
            return responseDTO;
        }
    }

    public List<StatResponseDTO> getAllStats() {

        List<Stat> stats = statRepository.findAll();

        List<StatResponseDTO> dtos = statMapper.mapToListOfResponseDTOs(stats);
        return dtos;
    }

    public List<StatResponseDTO> getAllStatsOfUser(String username) {
        List<Stat> stats = statRepository.findAllByUser_UserName(username);
        if (stats.isEmpty()) {
            throw new RecordNotFoundException("No stats found for user: " + username);
        } else {
            return statMapper.mapToListOfResponseDTOs(stats);
        }
    }

    public List<StatResponseDTO> getFilteredStats(List<String> username, List<Long> exerciseId) {
        if (username != null && exerciseId != null) {
            List<Stat> stats = statRepository.findAllByUser_UserNameInAndExerciseIdIn(username, exerciseId);
            if (stats.isEmpty()) {
                throw new RecordNotFoundException("No stats found for user: " + username + " ; and exercise: " + exerciseId);
            } else {
                return statMapper.mapToListOfResponseDTOs(stats);
            }
        } else if (username != null) {
            List<Stat> stats = statRepository.findAllByUser_UserNameIn(username);
            if (stats.isEmpty()) {
                throw new RecordNotFoundException("No stats found for user: " + username);
            } else {
                return statMapper.mapToListOfResponseDTOs(stats);
            }
        } else if (exerciseId != null) {
            List<Stat> stats = statRepository.findAllByExerciseIdIn(exerciseId);
            if (stats.isEmpty()) {

                throw new RecordNotFoundException("No stats found for exercise id: " + exerciseId);
            } else {
                return statMapper.mapToListOfResponseDTOs(stats);
            }
        } else {
            throw new BadRequestException("No parameters provided");
        }
    }


    // method that adjusts the stat of the user, timesRight, timesWrong, etc.


}
