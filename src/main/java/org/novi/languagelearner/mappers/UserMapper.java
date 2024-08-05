package org.novi.languagelearner.mappers;

import org.novi.languagelearner.dtos.UserChangePasswordRequestDTO;
import org.novi.languagelearner.dtos.UserRequestDTO;
import org.novi.languagelearner.dtos.UserResponseDTO;
import org.novi.languagelearner.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper{

    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;

    public UserMapper(RoleMapper roleMapper, PasswordEncoder passwordEncoder) {
        this.roleMapper = roleMapper;
        this.passwordEncoder = passwordEncoder;
    }

    //TODO: Delete maybe bc not needed when there's no usermodel
//    public UserModel fromEntity(User entity) {
//        if (entity == null) {
//            return null;
//        }
//        User user = new User(entity.getId());
//        user.setPassword(entity.getPassword());
//        user.setUserName(entity.getUserName());
//        user.areCredentialsExpired(entity.areCredentialsExpired());
//        user.setEnabled(entity.isEnabled());
//        user.setExpired(entity.isExpired());
//        user.setLocked(entity.isLocked());
//        user.setRoles(roleMapper.fromEntities(entity.getRoles()));
//        return user;
//    }

    public User mapToEntity(UserRequestDTO userDTO) {
        var result = new User(-1L);
        result.setUserName(userDTO.getUserName());
        result.setPassword(userDTO.getPassword());
        return result;
    }

    public User mapToEntity(UserChangePasswordRequestDTO userDTO, Long id) {
        var result = new User(id);
        result.setPassword(userDTO.getPassword());
        return result;
    }

    public UserResponseDTO mapToResponseDTO(User user) {
        var userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUserName());
        userResponseDTO.setPassword(user.getPassword());
        userResponseDTO.setRoles(user.getRoles());


        return userResponseDTO;
    }
}

