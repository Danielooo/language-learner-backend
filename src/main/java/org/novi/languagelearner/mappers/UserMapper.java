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
        var result = new User();
        result.setUserName(userDTO.getUserName());
        result.setPassword(userDTO.getPassword());
        return result;
    }

    public UserResponseDTO mapToResponseDTO(User user) {
        var userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUserName());
        userResponseDTO.setPassword(user.getPassword());
        List<String> userRoles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            userRoles.add(role.getRoleName());
        }
        userResponseDTO.setRoles(userRoles);
        userResponseDTO.setPhoto(user.getPhoto());

        return userResponseDTO;
    }
}

