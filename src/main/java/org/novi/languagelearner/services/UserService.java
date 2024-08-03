package org.novi.languagelearner.services;

import jakarta.transaction.Transactional;
import org.novi.languagelearner.entities.Role;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.mappers.RoleMapper;
import org.novi.languagelearner.mappers.UserMapper;
import org.novi.languagelearner.models.UserModel;
import org.novi.languagelearner.repositories.RoleRepository;
import org.novi.languagelearner.repositories.UserRepository;
import org.novi.languagelearner.security.ApiUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Transactional
    public boolean createUser(UserModel userModel, List<String> roles) {
        var validRoles = roleMapper.fromEntities(roleRepository.findByRoleNameIn(roles));
        userModel.setRoles(validRoles);
        var user = userMapper.toEntity(userModel);
        updateRolesWithUser(user);
        var savedUser = userRepository.save(user);
        userModel.setId(savedUser.getId());
        return savedUser != null;
    }

    private void updateRolesWithUser(User user) {
        for (Role role: user.getRoles()) {
            role.getUsers().add(user);
        }
    }

    @Transactional
    public boolean createUser(UserModel userModel, String[] roles) {
        return createUser(userModel, Arrays.asList(roles));
    }

    public Optional<UserModel> getUserByUserName(String username) {
        var user = userRepository.findByUserName(username);
        return getOptionalUserModel(user);
    }


    public Optional<UserModel> getUserById(Long id) {
        var user = userRepository.findById(id);
        return getOptionalUserModel(user);
    }


    public Optional<UserModel> getUserByUserNameAndPassword(String username, String password) {
        var user = userRepository.findByUserNameAndPassword(username, password);
        return getOptionalUserModel(user);
    }

    private Optional<UserModel> getOptionalUserModel(Optional<User> user) {
        if (user.isPresent()) {
            return Optional.of(userMapper.fromEntity(user.get()));
        }
        return Optional.empty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = getUserByUserName(username);
        if (user.isEmpty()) { throw new UsernameNotFoundException(username);}
        return new ApiUserDetails(user.get());
    }

    public boolean updatePassword(UserModel userModel) {
        Optional<User> user = userRepository.findById(userModel.getId());
        if (user.isEmpty()) { throw new UsernameNotFoundException(userModel.getId().toString());}
        // convert to entity to get the encode password
        var update_user = userMapper.toEntity(userModel);
        var entity = user.get();
        entity.setPassword(update_user.getPassword());
        return userRepository.save(entity) != null;
    }
}