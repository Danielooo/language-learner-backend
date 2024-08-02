package org.novi.languagelearner.mappers;

import org.novi.languagelearner.entities.Role;
import org.novi.languagelearner.models.RoleModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper{

    public RoleModel fromEntity(Role role) {
        if (role == null) {
            return null;
        }
        RoleModel model = new RoleModel(role.getId());
        model.setDescription(role.getDescription());
        model.setRoleName(role.getRoleName());
        model.setActive(role.isActive());
        return model;
    }

    public Role toEntity(RoleModel model) {
        if (model == null) {
            return null;
        }
        Role entity = new Role(model.getId());
        entity.setDescription(model.getDescription());
        entity.setRoleName(model.getRoleName());
        entity.setActive(model.isActive());
        return entity;
    }

    public List<RoleModel> fromEntities(List<Role> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    public List<Role> toEntities(List<RoleModel> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
