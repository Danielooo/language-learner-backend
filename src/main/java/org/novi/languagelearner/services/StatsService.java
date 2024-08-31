package org.novi.languagelearner.services;

import org.novi.languagelearner.dtos.Stats.StatsParamRequestDTO;
import org.novi.languagelearner.dtos.Stats.StatsParamResponseDTO;
import org.novi.languagelearner.dtos.Stats.StatsResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.Group;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.entities.UserInputAnswer;
import org.novi.languagelearner.mappers.StatsMapper;
import org.novi.languagelearner.repositories.ExerciseRepository;
import org.novi.languagelearner.repositories.GroupRepository;
import org.novi.languagelearner.repositories.UserInputAnswerRepository;
import org.novi.languagelearner.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Answer Frans; wil je hier id's doorgeven of de objecten zelf? // wil je zoveel mogelijk met userId werken of met username? Username krijg je binnen vanuit de Authorization in de controller namelijk, moet je ergens (waar?) omzetten naar userId >> Niet meer relevant. nieuwe aanpak: UserInputAnswer entities maken en persisten. Database met @Query statistieken laten ophalen. Kan in Stats maar is geen entity (wordt niet gepersist)

@Service
public class StatsService {

    private final StatsMapper statsMapper;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final UserInputAnswerRepository userInputAnswerRepository;
    private final ExerciseService exerciseService;
    private final UserService userService;
    private final GroupRepository groupRepository;



    public StatsService(StatsMapper statsMapper, ExerciseRepository exerciseRepository, UserRepository userRepository, UserInputAnswerRepository userInputAnswerRepository, ExerciseService exerciseService, UserService userService, GroupRepository groupRepository) {
        this.statsMapper = statsMapper;
        this.exerciseRepository = exerciseRepository;
        this.exerciseService = exerciseService;
        this.userRepository = userRepository;
        this.userInputAnswerRepository = userInputAnswerRepository;
        this.userService = userService;
        this.groupRepository = groupRepository;
    }

    public StatsResponseDTO getUserInputAnswersByUser(String userName, Long exerciseId) {

        User user = userService.getUserByUserName(userName);

        List<UserInputAnswer> userInputAnswersList = userInputAnswerRepository.findUserInputAnswersByUserAndExerciseId(user, exerciseId);

        Exercise exercise = exerciseService.getExerciseById(exerciseId);

        return statsMapper.toStatResponseDTO(exercise, userInputAnswersList);
    }


    public List<StatsParamResponseDTO> getStatsByParams(StatsParamRequestDTO statsParamRequestDTO) throws AccessDeniedException {

        // check if group belongs to user
        List<Long> groupsNoAccessForThisUser = findMismatchedGroupIds(statsParamRequestDTO.getUserName(), statsParamRequestDTO.getGroupIds());

        if (!groupsNoAccessForThisUser.isEmpty()) {

            String groupIdsString = groupsNoAccessForThisUser.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            throw new AccessDeniedException("You have no access to these groups: " + groupIdsString);
        }

        List<Group> statsParamResponseDTOlist = groupRepository.findStatsByGroupIdsAndTimeRange(
                statsParamRequestDTO.getGroupIds(),
                statsParamRequestDTO.getUserInputStartTime(),
                statsParamRequestDTO.getUserInputEndTime()
        );


        return null;
    }

    public List<Long> findMismatchedGroupIds(String userName, List<Long> requestGroupIds) {
        List<Long> userGroupIds = userRepository.findGroupIdsByUserName(userName);

        List<Long> mismatchedGroupIds = requestGroupIds.stream()
                .filter(groupId -> !userGroupIds.contains(groupId))
                .toList();

        return mismatchedGroupIds;
    }
}
