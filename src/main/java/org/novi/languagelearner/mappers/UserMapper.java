package org.novi.languagelearner.mappers;

import org.novi.languagelearner.dtos.User.UserRequestDTO;
import org.novi.languagelearner.dtos.User.UserResponseDTO;
import org.novi.languagelearner.entities.Role;
import org.novi.languagelearner.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper{

    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;


    public UserMapper(RoleMapper roleMapper, PasswordEncoder passwordEncoder) {
        this.roleMapper = roleMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User mapToEntity(UserRequestDTO userDTO) {
        User newUser = new User();
        newUser.setUserName(userDTO.getUserName());
        newUser.setPassword(userDTO.getPassword());
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());

        return newUser;
    }

    public UserResponseDTO mapToResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUserName());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());

        List<String> userRoles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            userRoles.add(role.getRoleName());
        }
        userResponseDTO.setRoles(userRoles);
        userResponseDTO.setPhoto(user.getPhoto());

        return userResponseDTO;
    }

    public List<UserResponseDTO> mapToListOfResponseDTOs(List<User> users) {
        List<UserResponseDTO> userResponseDTOs = new ArrayList<>();

        for (User user : users) {
            userResponseDTOs.add(mapToResponseDTO(user));
        }

        return userResponseDTOs;
    }
}

