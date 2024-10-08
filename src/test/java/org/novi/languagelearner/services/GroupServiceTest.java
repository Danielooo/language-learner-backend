package org.novi.languagelearner.services;

import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.*;



import java.util.ArrayList;
import java.util.List;

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

    private User mockUser;
    private GroupRequestDTO mockGroupRequestDTO;
    private Group mockGroup;

    @BeforeEach
    public void setUp() {
    // mock user
        mockUser = new User();
        mockUser.setUserName("Daniel");

    // Mock List of exercises
    List<Exercise> exerciseList = new ArrayList<>();
    Exercise exercise1 = new Exercise();
        exercise1.setQuestion("Water");
        exercise1.setAnswer("Acqua");

    Exercise exercise2 = new Exercise();
        exercise2.setQuestion("Pardon");
        exercise2.setAnswer("Mi scuzi");

        exerciseList.add(exercise1);
        exerciseList.add(exercise2);

    // mock GroupRequestDTO
    mockGroupRequestDTO = new GroupRequestDTO();
        mockGroupRequestDTO.setGroupName("Nederlands-Italiaans Basis");
        mockGroupRequestDTO.setUserName("Daniel");
        mockGroupRequestDTO.setExercises(exerciseList);
    }

    @Test
    @DisplayName("should create group")
    public void createGroup() {
        // ARRANGE
        // Mock the userService to return mockUser when getUserByUserName() is called with any String
        Mockito.when(userService.getUserByUserName(Mockito.anyString())).thenReturn(mockUser);

        // Mock the groupMapper to return mockGroup when toGroup() is called with any GroupRequestDTO and the mockUser
        Mockito.when(groupMapper.mapToEntity(Mockito.any(GroupRequestDTO.class), Mockito.eq(mockUser))).thenReturn(mockGroup);

        // Mock the groupRepository to return mockGroup when save() is called with any Group
        Mockito.when(groupRepository.save(Mockito.any(Group.class))).thenReturn(mockGroup);

        // Create a mock GroupResponseDTO that the groupMapper will return
        GroupResponseDTO mockGroupResponseDTO = new GroupResponseDTO();
        mockGroupResponseDTO.setGroupName(mockGroup.getGroupName());
        // Set other fields of mockGroupResponseDTO if necessary

        // Mock the groupMapper to return mockGroupResponseDTO when mapToResponseDTO() is called with any Group
        Mockito.when(groupMapper.mapToResponseDTO(Mockito.any(Group.class))).thenReturn(mockGroupResponseDTO);

        // ACT
        // Call the method under test with the mockGroupRequestDTO
        GroupResponseDTO groupResponseDTO = groupService.createGroup(mockGroupRequestDTO);

        // ASSERT
        // Verify that the returned GroupResponseDTO is not null
        assertNotNull(groupResponseDTO);

        // Assert that the group name matches the expected group name
        assertEquals(mockGroupRequestDTO.getGroupName(), groupResponseDTO.getGroupName());

        // Optionally, verify that other methods were called as expected (interaction testing)
        Mockito.verify(userService).getUserByUserName(Mockito.anyString());
        Mockito.verify(groupMapper).mapToEntity(Mockito.any(GroupRequestDTO.class), Mockito.eq(mockUser));
        Mockito.verify(groupRepository).save(Mockito.any(Group.class));
        Mockito.verify(groupMapper).mapToResponseDTO(Mockito.any(Group.class));
    }
}
