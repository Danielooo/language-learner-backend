package org.novi.languagelearner.mappers;

import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.models.UserModel;
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

    public UserModel fromEntity(User entity) {
        if (entity == null) {
            return null;
        }
        UserModel model = new UserModel(entity.getId());
        model.setPassword(entity.getPassword());
        model.setUserName(entity.getUserName());
        model.areCredentialsExpired(entity.areCredentialsExpired());
        model.setEnabled(entity.isEnabled());
        model.setExpired(entity.isExpired());
        model.setLocked(entity.isLocked());
        model.setRoles(roleMapper.fromEntities(entity.getRoles()));
        return model;
    }

    public User toEntity(UserModel model) {
        if (model == null) {
            return null;
        }
        User entity = new User(model.getId());
        entity.setPassword(passwordEncoder.encode(model.getPassword()));
        entity.setUserName(model.getUserName());
        entity.setAreCredentialsExpired(model.areCredentialsExpired());
        entity.setEnabled(model.isEnabled());
        entity.setExpired(model.isExpired());
        entity.setLocked(model.isLocked());
        entity.setRoles(roleMapper.toEntities(model.getRoles()));
        return entity;
    }
}

