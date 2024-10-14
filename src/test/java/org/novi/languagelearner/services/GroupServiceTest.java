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
import org.novi.languagelearner.exceptions.AccessDeniedException;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.mappers.ExerciseMapper;
import org.novi.languagelearner.mappers.GroupMapper;
import org.novi.languagelearner.repositories.ExerciseRepository;
import org.novi.languagelearner.repositories.GroupRepository;
import org.novi.languagelearner.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.novi.languagelearner.exceptions.BadRequestException; // Add this import


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.*;

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

//    @BeforeEach
//    public void setUp() {
//        // mock user
//        mockUser = new User();
//        mockUser.setUserName("Daniel");
//
//        // Mock List of exercises
//        List<Exercise> exerciseList = new ArrayList<>();
//        Exercise exercise1 = new Exercise();
//        exercise1.setQuestion("Water");
//        exercise1.setAnswer("Acqua");
//
//        Exercise exercise2 = new Exercise();
//        exercise2.setQuestion("Pardon");
//        exercise2.setAnswer("Mi scuzi");
//
//        exerciseList.add(exercise1);
//        exerciseList.add(exercise2);
//
//        // mock GroupRequestDTO
//        mockGroupRequestDTO = new GroupRequestDTO();
//        mockGroupRequestDTO.setGroupName("Nederlands-Italiaans Basis");
//        mockGroupRequestDTO.setUserName("Daniel");
//        mockGroupRequestDTO.setExercises(exerciseList);
//    }


    @Test
    public void createGroup_ShouldReturnGroupResponseDTO() {
        // ARRANGE
        // Mock the userService to return mockUser when getUserByUserName() is called with any String
        when(userService.getUserByUserName(Mockito.anyString())).thenReturn(mockUser);

        // Initialize the mockGroupRequestDTO with the necessary fields
        GroupRequestDTO mockGroupRequestDTO = new GroupRequestDTO();
        mockGroupRequestDTO.setUserName("Mock Username");
        mockGroupRequestDTO.setGroupName("Mock Group Name"); // Set the necessary fields of mockGroupRequestDTO
        // Set other fields as needed, for example: mockGroupRequestDTO.setUserName("testUser");

        // Initialize the mockGroup with the necessary fields
        Group mockGroup = new Group();
        mockGroup.setGroupName("Mock Group Name"); // Set the necessary fields of mockGroup

        // Mock the groupMapper to return mockGroup when mapToEntity() is called with any GroupRequestDTO and the mockUser
        when(groupMapper.mapToEntity(Mockito.any(GroupRequestDTO.class), Mockito.eq(mockUser))).thenReturn(mockGroup);

        // Mock the groupRepository to return mockGroup when save() is called with any Group
        when(groupRepository.save(Mockito.any(Group.class))).thenReturn(mockGroup);

        // Create a mock GroupResponseDTO that the groupMapper will return
        GroupResponseDTO mockGroupResponseDTO = new GroupResponseDTO();
        mockGroupResponseDTO.setGroupName(mockGroup.getGroupName()); // Use the initialized mockGroup

        // Mock the groupMapper to return mockGroupResponseDTO when mapToResponseDTO() is called with any Group
        when(groupMapper.mapToResponseDTO(Mockito.any(Group.class))).thenReturn(mockGroupResponseDTO);

        // ACT
        // Call the method under test with the initialized mockGroupRequestDTO
        GroupResponseDTO groupResponseDTO = groupService.createGroup(mockGroupRequestDTO);

        // ASSERT
        // Verify that the returned GroupResponseDTO is not null
        assertNotNull(groupResponseDTO);

        // Assert that the group name matches the expected group name
        assertEquals(mockGroupRequestDTO.getGroupName(), groupResponseDTO.getGroupName());

        // Optionally, verify that other methods were called as expected (interaction testing)
        verify(userService).getUserByUserName(Mockito.anyString());
        verify(groupMapper).mapToEntity(Mockito.any(GroupRequestDTO.class), Mockito.eq(mockUser));
        verify(groupRepository).save(Mockito.any(Group.class));
        verify(groupMapper).mapToResponseDTO(Mockito.any(Group.class));
    }

    @Test
    public void createGroup_ShouldThrowBadRequestException() {
        // Arrange
        GroupRequestDTO alreadyExistGroupRequestDTO = new GroupRequestDTO();
        // Set invalid fields for invalidGroupRequestDTO if necessary
        alreadyExistGroupRequestDTO.setGroupName("existingGroup");

        // Act
        when(groupRepository.existsByGroupName(anyString())).thenReturn(true);

        // Assert
        assertThrows(BadRequestException.class, () -> groupService.createGroup(alreadyExistGroupRequestDTO));
        verify(groupMapper, never()).mapToEntity(any(GroupRequestDTO.class), any(User.class));
        verify(groupRepository, never()).save(any(Group.class));
    }

    @Test
    public void createGroupsWithJsonFiles_ShouldReturnListOfGroupResponseDTOs() {
        // Arrange
        String userName = "Daniel";
        MultipartFile[] jsonFiles = new MultipartFile[]{mock(MultipartFile.class), mock(MultipartFile.class)};
        GroupRequestDTO groupRequestDTO1 = new GroupRequestDTO();
        groupRequestDTO1.setGroupName("Group 1");
        groupRequestDTO1.setUserName(userName);
        GroupRequestDTO groupRequestDTO2 = new GroupRequestDTO();
        groupRequestDTO2.setGroupName("Group 2");
        groupRequestDTO2.setUserName(userName);

        Group group1 = new Group();
        group1.setGroupName("Group 1");
        Group group2 = new Group();
        group2.setGroupName("Group 2");

        GroupResponseDTO groupResponseDTO1 = new GroupResponseDTO();
        groupResponseDTO1.setGroupName("Group 1");
        GroupResponseDTO groupResponseDTO2 = new GroupResponseDTO();
        groupResponseDTO2.setGroupName("Group 2");

        when(groupMapper.mapJsonFileToGroupRequestDTO(jsonFiles[0], userName)).thenReturn(groupRequestDTO1);
        when(groupMapper.mapJsonFileToGroupRequestDTO(jsonFiles[1], userName)).thenReturn(groupRequestDTO2);
        when(userService.getUserByUserName(userName)).thenReturn(mockUser);
        when(groupMapper.mapToEntity(groupRequestDTO1, mockUser)).thenReturn(group1);
        when(groupMapper.mapToEntity(groupRequestDTO2, mockUser)).thenReturn(group2);
        when(groupRepository.save(group1)).thenReturn(group1);
        when(groupRepository.save(group2)).thenReturn(group2);
        when(groupMapper.mapToResponseDTO(group1)).thenReturn(groupResponseDTO1);
        when(groupMapper.mapToResponseDTO(group2)).thenReturn(groupResponseDTO2);

        // Act
        List<GroupResponseDTO> result = groupService.createGroupsWithJsonFiles(jsonFiles, userName);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Group 1", result.get(0).getGroupName());
        assertEquals("Group 2", result.get(1).getGroupName());

        // Verify interactions
        verify(groupMapper).mapJsonFileToGroupRequestDTO(jsonFiles[0], userName);
        verify(groupMapper).mapJsonFileToGroupRequestDTO(jsonFiles[1], userName);
        verify(userService, times(2)).getUserByUserName(userName);
        verify(groupMapper).mapToEntity(groupRequestDTO1, mockUser);
        verify(groupMapper).mapToEntity(groupRequestDTO2, mockUser);
        verify(groupRepository).save(group1);
        verify(groupRepository).save(group2);
        verify(groupMapper).mapToResponseDTO(group1);
        verify(groupMapper).mapToResponseDTO(group2);
    }

    @Test
    public void createGroupsWithJsonFiles_ShouldThrowBadRequestException() {
        // Arrange
        String userName = "Daniel";
        MultipartFile[] jsonFiles = new MultipartFile[]{mock(MultipartFile.class)};
        when(jsonFiles[0].isEmpty()).thenReturn(true);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> groupService.createGroupsWithJsonFiles(jsonFiles, userName));

        // Verify interactions
        verify(groupMapper, never()).mapJsonFileToGroupRequestDTO(any(MultipartFile.class), eq(userName));
        verify(groupRepository, never()).save(any(Group.class));
    }

    @Test
    public void getGroupById_ShouldGetGroupById() {
        // Arrange
        Long testId = 1L;
        Group testGroup = new Group();
        testGroup.setGroupName("Test Group");
        User testUser = new User();
        testUser.setUserName("testUser");
        testGroup.setUser(testUser);

        GroupResponseDTO mockGroupResponseDTO = new GroupResponseDTO();
        mockGroupResponseDTO.setGroupName("Test Group");

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        SecurityContextHolder.setContext(securityContext);

        when(groupRepository.findById(testId)).thenReturn(Optional.of(testGroup));
        when(userRepository.findUserByUserName("testUser")).thenReturn(Optional.of(testUser));
        when(groupMapper.mapToResponseDTO(testGroup)).thenReturn(mockGroupResponseDTO);

        // Act
        GroupResponseDTO result = groupService.getGroupById(testId);

        // Assert
        assertNotNull(result);
        assertEquals("Test Group", result.getGroupName());

        // Verify interactions
        verify(groupRepository).findById(testId);
        verify(userRepository).findUserByUserName("testUser");
        verify(groupMapper).mapToResponseDTO(testGroup);
    }

    @Test
    public void getAllGroups_Success() {
        // Arrange
        String userName = "testUser";
        Group group1 = new Group();
        group1.setGroupName("Group 1");

        Group group2 = new Group();
        group2.setGroupName("Group 2");

        List<Group> groups = Arrays.asList(group1, group2);

        GroupResponseDTO responseDTO1 = new GroupResponseDTO();
        responseDTO1.setId(1L);
        responseDTO1.setGroupName("Group 1");

        GroupResponseDTO responseDTO2 = new GroupResponseDTO();
        responseDTO2.setId(1L);
        responseDTO2.setGroupName("Group 2");


        // Mock group repository to return the groups
        when(groupRepository.findByUser_UserName(userName)).thenReturn(groups);

        // Mock group mapper to convert Group to GroupResponseDTO


        when(groupMapper.mapToResponseDTO(group1)).thenReturn(responseDTO1);
        when(groupMapper.mapToResponseDTO(group2)).thenReturn(responseDTO2);

        // Act
        List<GroupResponseDTO> response = groupService.getAllGroups(userName);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Group 1", response.get(0).getGroupName());
        assertEquals("Group 2", response.get(1).getGroupName());

        // Verify that the repository was called
        verify(groupRepository, times(1)).findByUser_UserName(userName);
        verify(groupMapper, times(2)).mapToResponseDTO(any(Group.class));
    }

    @Test
    public void getAllGroups_NoGroupsFound() {
        // Arrange
        String userName = "testUser";

        // Mock group repository to return an empty list
        when(groupRepository.findByUser_UserName(userName)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> {
            groupService.getAllGroups(userName);
        });

        // Verify that the repository was called
        verify(groupRepository, times(1)).findByUser_UserName(userName);
    }

    @Test
    public void addExercisesToGroup_Success() {
        // Arrange
        Long groupId = 1L;
        String userName = "testUser";

        // Mock Group and User objects
        Group group = new Group();
        group.setGroupName("Test Group");

        User user = new User();
        user.setUserName(userName);
        group.setUser(user);

        // Mock Exercise objects
        Exercise exercise1 = new Exercise();
        exercise1.setQuestion("Question 1");
        exercise1.setAnswer("Answer 1");

        Exercise exercise2 = new Exercise();
        exercise2.setQuestion("Question 2");
        exercise2.setAnswer("Answer 2");

        GroupRequestDTO groupRequestDTO = new GroupRequestDTO();
        groupRequestDTO.setId(groupId);
        groupRequestDTO.setUserName(userName);
        groupRequestDTO.setExercises(Arrays.asList(exercise1, exercise2));

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        // Mock the save behavior for exercises
        Exercise savedExercise1 = new Exercise();
        savedExercise1.setQuestion("Question 1");
        savedExercise1.setAnswer("Answer 1");
        savedExercise1.setGroup(group);

        Exercise savedExercise2 = new Exercise();
        savedExercise2.setQuestion("Question 2");
        savedExercise2.setAnswer("Answer 2");
        savedExercise2.setGroup(group);

        when(exerciseRepository.save(any(Exercise.class))).thenReturn(savedExercise1, savedExercise2);

        // Mock the save behavior for group
        when(groupRepository.save(group)).thenReturn(group);

        // Mock the mapper to return a GroupResponseDTO
        GroupResponseDTO groupResponseDTO = new GroupResponseDTO();
        groupResponseDTO.setId(groupId);
        groupResponseDTO.setGroupName("Test Group");
        groupResponseDTO.setUserName(userName);
        groupResponseDTO.setExercises(Arrays.asList(savedExercise1, savedExercise2));

        when(groupMapper.mapToResponseDTO(group)).thenReturn(groupResponseDTO);

        // Act
        GroupResponseDTO response = groupService.addExercisesToGroup(groupRequestDTO);

        // Assert
        assertNotNull(response);
        assertEquals("Test Group", response.getGroupName());

        verify(groupRepository, times(1)).findById(groupId);
        verify(exerciseRepository, times(2)).save(any(Exercise.class)); // Called twice, once for each exercise
        verify(groupRepository, times(1)).save(group);
        verify(groupMapper, times(1)).mapToResponseDTO(group);
    }

    @Test
    public void addExercisesToGroup_GroupNotFound() {
        // Arrange
        Long groupId = 1L;
        GroupRequestDTO groupRequestDTO = new GroupRequestDTO();
        groupRequestDTO.setId(groupId);
        groupRequestDTO.setUserName("testUser");

        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> {
            groupService.addExercisesToGroup(groupRequestDTO);
        });

        verify(groupRepository, times(1)).findById(groupId);
        verify(exerciseRepository, never()).save(any(Exercise.class)); // Should not attempt to save exercises
        verify(groupRepository, never()).save(any(Group.class)); // Group should not be saved
    }

    @Test
    public void addExercisesToGroup_UnauthorizedUser() {
        // Arrange
        Long groupId = 1L;
        String userName = "testUser";
        String unauthorizedUserName = "unauthorizedUser";

        Group group = new Group();
        group.setGroupName("Test Group");

        User user = new User();
        user.setUserName(userName);
        group.setUser(user);

        GroupRequestDTO groupRequestDTO = new GroupRequestDTO();
        groupRequestDTO.setId(groupId);
        groupRequestDTO.setUserName(unauthorizedUserName); // Simulate an unauthorized user
        groupRequestDTO.setExercises(Collections.emptyList());

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        // Act & Assert
        assertThrows(AccessDeniedException.class, () -> {
            groupService.addExercisesToGroup(groupRequestDTO);
        });

        verify(groupRepository, times(1)).findById(groupId);
        verify(exerciseRepository, never()).save(any(Exercise.class)); // Should not attempt to save exercises
        verify(groupRepository, never()).save(any(Group.class)); // Group should not be saved
    }


    @Test
    public void getAllGroupsAsAdmin_Success() {
        // Arrange
        Group group1 = new Group();
        group1.setGroupName("Group 1");

        Group group2 = new Group();
        group2.setGroupName("Group 2");

        // Mock repository to return list of groups
        List<Group> groups = Arrays.asList(group1, group2);
        when(groupRepository.findAll()).thenReturn(groups);

        // Mock the mapper to convert groups to GroupResponseDTOs
        GroupResponseDTO responseDTO1 = new GroupResponseDTO();
        responseDTO1.setGroupName("Group 1");

        GroupResponseDTO responseDTO2 = new GroupResponseDTO();
        responseDTO2.setGroupName("Group 2");

        when(groupMapper.mapToResponseDTO(group1)).thenReturn(responseDTO1);
        when(groupMapper.mapToResponseDTO(group2)).thenReturn(responseDTO2);

        // Act
        List<GroupResponseDTO> response = groupService.getAllGroupsAsAdmin();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Group 1", response.get(0).getGroupName());
        assertEquals("Group 2", response.get(1).getGroupName());

        verify(groupRepository, times(1)).findAll();
        verify(groupMapper, times(1)).mapToResponseDTO(group1);
        verify(groupMapper, times(1)).mapToResponseDTO(group2);
    }

    @Test
    public void getAllGroupsAsAdmin_NoGroupsFound() {
        // Arrange
        when(groupRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> {
            groupService.getAllGroupsAsAdmin();
        });

        verify(groupRepository, times(1)).findAll();
        verify(groupMapper, never()).mapToResponseDTO(any(Group.class)); // Mapper should not be called
    }


    @Test
    public void deleteGroupAsAdmin_Success() {
        // Arrange
        Long groupId = 1L;
        Group group = new Group();
        group.setGroupName("Test Group");

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        // Act
        String result = groupService.deleteGroupAsAdmin(groupId);

        // Assert
        assertEquals("Group deleted with following id: " + groupId, result);

        verify(groupRepository, times(1)).findById(groupId);
        verify(groupRepository, times(1)).deleteById(groupId);
    }

    @Test
    public void deleteGroupAsAdmin_GroupNotFound() {
        // Arrange
        Long groupId = 1L;
        when(groupRepository.findById(groupId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> {
            groupService.deleteGroupAsAdmin(groupId);
        });

        verify(groupRepository, times(1)).findById(groupId);
        verify(groupRepository, never()).deleteById(groupId); // Delete should not be called
    }
}


