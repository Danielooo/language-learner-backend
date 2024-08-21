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
import org.novi.languagelearner.repositories.UserRepository;
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
    private UserRepository userRepository;
    private UserService userService;
    private ExerciseService exerciseService;


    public StatService(StatMapper statMapper, StatRepository statRepository, ExerciseRepository exerciseRepository, UserRepository userRepository) {
        this.statMapper = statMapper;
        this.statRepository = statRepository;
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
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

    // method krijgt exerciseId en userId (want username kan veranderen en moet dan ook cascade hebben) mee, en een boolean die aangeeft of de poging correct was
    // als er nog geen stat is voor de user en exercise, maak een nieuwe stat aan
    // als er al een stat is, update de timesRight of timesWrong >> if attemptIsCorrect, timesRight + 1, else timesWrong + 1
    // save stat

    // TODO: Ask Frans; wil je hier id's doorgeven of de objecten zelf? // wil je zoveel mogelijk met userId werken of met username? Username krijg je binnen vanuit de Authorization in de controller namelijk, moet je ergens (waar?) omzetten naar userId
    public Stat updateStatAfterExerciseAttempt(Long exerciseId, Long userId, boolean attemptIsCorrect) {

        // TODO: Ask Frans; waarom hier de melding 'reassigned local variable'?
        Stat statToUpdate = findStatByExerciseIdAndUserId(exerciseId, userId);
        if (statToUpdate == null) {
//            statToUpdate = new Stat();
            statToUpdate.setUser(userService.getUserByUserId(userId));
            statToUpdate.setExercise(exerciseService.getExerciseById(exerciseId));
            statToUpdate.setTimesRight(0);
            statToUpdate.setTimesWrong(0);
        }
        if (attemptIsCorrect) {
            statToUpdate.setTimesRight(statToUpdate.getTimesRight() + 1);
        } else {
            statToUpdate.setTimesWrong(statToUpdate.getTimesWrong() + 1);
        }
        return statRepository.save(statToUpdate);
    }

    public Stat findStatByExerciseIdAndUserId(Long exerciseId, Long userId) {
        Optional<Stat> statOptional = statRepository.findStatByExerciseIdAndUserId(exerciseId, userId);
        if (statOptional.isEmpty()) {
            throw new RecordNotFoundException("No stat found for exercise id: " + exerciseId + " and user id: " + userId);
        } else {
            return statOptional.get();
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
