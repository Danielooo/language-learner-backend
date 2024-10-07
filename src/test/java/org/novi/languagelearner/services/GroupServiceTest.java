package org.novi.languagelearner.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.novi.languagelearner.dtos.Group.GroupRequestDTO;
import org.novi.languagelearner.dtos.Group.GroupResponseDTO;
import org.novi.languagelearner.entities.Exercise;
import org.novi.languagelearner.entities.Group;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.mappers.ExerciseMapper;
import org.novi.languagelearner.mappers.GroupMapper;
import org.novi.languagelearner.repositories.ExerciseRepository;
import org.novi.languagelearner.repositories.GroupRepository;
import org.novi.languagelearner.repositories.UserRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.jupiter.api.Assertions.*;



import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;



    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ExerciseMapper exerciseMapper;

    @Mock
    private GroupMapper groupMapper;

    @InjectMocks
    private GroupService groupService;


    @Test
    @DisplayName("should create group")
    public void createGroup() {
        List<Exercise> exerciseList = new ArrayList<>();

        User u = new User();
                u.setFirstName("daniel");
        GroupRequestDTO groupRequestDTO = new GroupRequestDTO();
        groupRequestDTO.setGroupName("banaan");
        groupRequestDTO.setUserName("daniel");
        groupRequestDTO.setExercises(exerciseList);

        Group group = new Group();
        group.setGroupName("banaan");
        group.setExercises(exerciseList);

        // when
        Mockito.when(groupRepository.save(Mockito.any())).thenReturn(group);
        Mockito.when(userService.getUserByUserName(Mockito.any())).thenReturn(u);
        GroupResponseDTO groupResponseDTO = groupService.createGroup(groupRequestDTO);
        assertEquals("banaan", groupResponseDTO.getGroupName());
    }

}
