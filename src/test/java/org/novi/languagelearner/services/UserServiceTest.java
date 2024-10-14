package org.novi.languagelearner.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.novi.languagelearner.dtos.User.UserRequestDTO;
import org.novi.languagelearner.dtos.User.UserResponseDTO;
import org.novi.languagelearner.entities.Role;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.exceptions.UserNameAlreadyExistsException;
import org.novi.languagelearner.mappers.RoleMapper;
import org.novi.languagelearner.mappers.UserMapper;
import org.novi.languagelearner.repositories.RoleRepository;
import org.novi.languagelearner.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;



    @Test
    public void createUser_ShouldThrowException_WhenUsernameAlreadyExists() {
        // Arrange
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserName("existingUser");
        when(userRepository.findByUserName(userRequestDTO.getUserName())).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(UserNameAlreadyExistsException.class, () -> userService.createUser(userRequestDTO));
    }

    @Test
    public void createUser_ShouldCreateUser_WhenUsernameDoesNotExist() {
        // Arrange
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserName("newUser");
        userRequestDTO.setPassword("password");

        User user = new User();
        user.setUserName("newUser");
        user.setPassword("encodedPassword");
        user.setEnabled(true);
        user.setRoles(List.of(new Role("ROLE_USER")));

        User savedUser = new User();
        savedUser.setUserName("newUser");
        savedUser.setPassword("encodedPassword");
        savedUser.setEnabled(true);
        savedUser.setRoles(List.of(new Role("ROLE_USER")));

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername("newUser");

        when(userRepository.findByUserName(userRequestDTO.getUserName())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(userRequestDTO.getPassword())).thenReturn("encodedPassword");
        when(userMapper.mapToEntity(userRequestDTO)).thenReturn(user);
        when(roleRepository.findByRoleNameIn(List.of("ROLE_USER"))).thenReturn(List.of(new Role("ROLE_USER")));
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.mapToResponseDTO(savedUser)).thenReturn(userResponseDTO);

        // Act
        UserResponseDTO result = userService.createUser(userRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("newUser", result.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void getUserResponseDTOByUserName_ShouldReturnUserResponseDTO_WhenUserExists() {
        // Arrange
        String username = "existingUser";
        User user = new User();
        user.setUserName(username);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername(username);
        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        when(userMapper.mapToResponseDTO(user)).thenReturn(userResponseDTO);

        // Act
        UserResponseDTO result = userService.getUserResponseDTOByUserName(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    public void getUserResponseDTOByUserName_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        String username = "nonExistingUser";
        when(userRepository.findByUserName(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> userService.getUserResponseDTOByUserName(username));
    }
}