package org.novi.languagelearner.services;

import org.novi.languagelearner.dtos.Stats.StatsParamRequestDTO;
import org.novi.languagelearner.dtos.Stats.StatsParamResponseDTO;
import org.novi.languagelearner.dtos.Stats.StatsOfExerciseResponseDTO;
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

    public StatsOfExerciseResponseDTO getStatsOfExercise(String userName, Long exerciseId) throws AccessDeniedException {

        // check if user has access to exercise
        User user = userService.getUserByUserName(userName);


        if ( !isExerciseFromUser(user, exerciseId ) ) {
            throw new AccessDeniedException("This exercise does not belong to user: " + userName);
        }

        Exercise exercise = exerciseService.getExerciseById(exerciseId);


        List<UserInputAnswer> userInputAnswersList = userInputAnswerRepository.findUserInputAnswersByUserAndExerciseId(user, exerciseId);

        return statsMapper.toStatResponseDTO(exercise, userInputAnswersList);
    }

    // can go to util?
    private boolean isExerciseFromUser(User user, Long exerciseId) {
        for (Group group : user.getGroups()) {
            for (Exercise exercise : group.getExercises()) {
                if (exercise.getId().equals(exerciseId)) {
                    return true;
                }
            }
        }
        return false;
    }

    // TODO: kill for time reasons, not working now.
    public StatsOfExerciseResponseDTO getUserInputAnswersByUser(String userName, Long exerciseId) {

        User user = userService.getUserByUserName(userName);

        List<UserInputAnswer> userInputAnswersList = userInputAnswerRepository.findUserInputAnswersByUserAndExerciseId(user, exerciseId);

        Exercise exercise = exerciseService.getExerciseById(exerciseId);

        return statsMapper.toStatResponseDTO(exercise, userInputAnswersList);
    }


    // TODO: Kill bc it's not working
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

    // TODO: Kill bc it's not working
    public List<Long> findMismatchedGroupIds(String userName, List<Long> requestGroupIds) {
        List<Long> userGroupIds = userRepository.findGroupIdsByUserName(userName);

        List<Long> mismatchedGroupIds = requestGroupIds.stream()
                .filter(groupId -> !userGroupIds.contains(groupId))
                .toList();

        return mismatchedGroupIds;
    }
}
