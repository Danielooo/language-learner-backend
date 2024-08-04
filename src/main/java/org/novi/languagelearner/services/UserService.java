package org.novi.languagelearner.services;

import jakarta.transaction.Transactional;
import org.novi.languagelearner.dtos.UserResponseDTO;
import org.novi.languagelearner.entities.Role;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.mappers.RoleMapper;
import org.novi.languagelearner.mappers.UserMapper;
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
    public boolean createUser(User user, List<String> roles) {
        var validRoles = roleRepository.findByRoleNameIn(roles);
        user.setRoles(validRoles);
//        var user = userMapper.toEntity(user);
        updateRolesWithUser(user);
        var savedUser = userRepository.save(user);
        user.setId(savedUser.getId());
        return savedUser != null;
    }

    private void updateRolesWithUser(User user) {
        for (Role role: user.getRoles()) {
            role.getUsers().add(user);
        }
    }

    @Transactional
    public boolean createUser(User user, String[] roles) {
        return createUser(user, Arrays.asList(roles));
    }



    // TODO: SME >> Wil je Optional terug op Controller/Client niveau of alleen UserResponseDTO teruggeven vanuit Service?
    public Optional<User> getUserByUserName(String username) {
        Optional<User> user = userRepository.findByUserName(username);

        return user;
    }


    public Optional<User> getUserById(Long id) {
        var user = userRepository.findById(id);
        return user;
    }


    public Optional<User> getUserByUserNameAndPassword(String username, String password) {
        var user = userRepository.findByUserNameAndPassword(username, password);
        return user;
    }

    // TODO: migth be obsolete bc UserModel is deleted
//    private Optional<User> getOptionalUser(Optional<User> user) {
//        if (user.isPresent()) {
//            return Optional.of(userMapper.fromEntity(user.get()));
//        }
//        return Optional.empty();
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = getUserByUserName(username);
        if (user.isEmpty()) { throw new UsernameNotFoundException(username);}
        return new ApiUserDetails(user.get());
    }

    public boolean updatePassword(User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(user.getId().toString());
        } else {
            // If save doesn't work it would throw an error. This return is just to give back True on succes.
            return userRepository.save(user) != null;
        }
    }
}

