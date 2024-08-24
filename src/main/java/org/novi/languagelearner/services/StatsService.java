package org.novi.languagelearner.services;

import org.novi.languagelearner.dtos.Stats.StatsResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.entities.UserInputAnswer;
import org.novi.languagelearner.mappers.StatsMapper;
import org.novi.languagelearner.repositories.ExerciseRepository;
import org.novi.languagelearner.repositories.UserInputAnswerRepository;
import org.novi.languagelearner.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: Answer Frans; wil je hier id's doorgeven of de objecten zelf? // wil je zoveel mogelijk met userId werken of met username? Username krijg je binnen vanuit de Authorization in de controller namelijk, moet je ergens (waar?) omzetten naar userId >> Niet meer relevant. nieuwe aanpak: UserInputAnswer entities maken en persisten. Database met @Query statistieken laten ophalen. Kan in Stats maar is geen entity (wordt niet gepersist)

@Service
public class StatsService {

    private StatsMapper statsMapper;
    private ExerciseRepository exerciseRepository;
    private UserRepository userRepository;
    private UserInputAnswerRepository userInputAnswerRepository;
    private final ExerciseService exerciseService;
    private final UserService userService;


    public StatsService(StatsMapper statsMapper, ExerciseRepository exerciseRepository, UserRepository userRepository, UserInputAnswerRepository userInputAnswerRepository, ExerciseService exerciseService, UserService userService) {
        this.statsMapper = statsMapper;
        this.exerciseRepository = exerciseRepository;
        this.exerciseService = exerciseService;
        this.userRepository = userRepository;
        this.userInputAnswerRepository = userInputAnswerRepository;
        this.userService = userService;
    }

    public StatsResponseDTO getUserInputAnswersByUser(String userName, Long exerciseId) {

        User user = userService.getUserByUserName(userName);

        List<UserInputAnswer> userInputAnswersList = userInputAnswerRepository.findUserInputAnswersByUserAndExerciseId(user, exerciseId);



//        Exercise exercise = exerciseService.getExerciseWithoutUserInputAnswersById(exerciseId);
        Exercise exercise = exerciseService.getExerciseById(exerciseId);

        return statsMapper.toStatResponseDTO(exercise, userInputAnswersList) ;
    }






}
