package org.novi.languagelearner.services;

import jakarta.transaction.Transactional;
import org.novi.languagelearner.dtos.User.UserNameChangeRequestDTO;
import org.novi.languagelearner.dtos.User.UserRequestDTO;
import org.novi.languagelearner.dtos.User.UserResponseDTO;
import org.novi.languagelearner.entities.Role;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.exceptions.UserNameAlreadyExistsException;
import org.novi.languagelearner.mappers.RoleMapper;
import org.novi.languagelearner.mappers.UserMapper;
import org.novi.languagelearner.repositories.RoleRepository;
import org.novi.languagelearner.repositories.UserRepository;
import org.novi.languagelearner.security.ApiUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, RoleMapper roleMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userDTO) {
        Optional<User> userOptional = userRepository.findByUserName(userDTO.getUserName());
        if (userOptional.isPresent()) {
            throw new UserNameAlreadyExistsException("Username already exists");
        } else {
            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            User user = userMapper.mapToEntity(userDTO);
            user.setEnabled(true);
            user.setRoles(roleRepository.findByRoleNameIn(List.of("ROLE_USER")));
            User savedUser = userRepository.save(user);
            return userMapper.mapToResponseDTO(savedUser);
        }
    }

    private void updateRolesWithUser(User user) {
        for (Role role: user.getRoles()) {
            role.getUsers().add(user);
        }
    }


    public UserResponseDTO getUserResponseDTOByUserName(String username) {
        Optional<User> userOptional = userRepository.findByUserName(username);
        if (userOptional.isEmpty()) {
            throw new RecordNotFoundException("User not found");
        } else {
            User user = userOptional.get();
            return userMapper.mapToResponseDTO(user);
        }
    }

    public UserResponseDTO getUserResponseDTOByIdAsAdmin(Long id) {

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new RecordNotFoundException("User not found");
        } else {
            User user = userOptional.get();
            return userMapper.mapToResponseDTO(user);
        }
    }

    public User getUserByUserName(String username) {
        Optional<User> user = userRepository.findUserByUserName(username);
        if (user.isEmpty()) {
            throw new RecordNotFoundException("User not found");
        } else {
            return user.get();
        }
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new RecordNotFoundException("User not found");
        } else {
            return userOptional.get();
        }
    }

    public Optional<User> getUserByUserNameAndPassword(String username, String password) {
        var user = userRepository.findByUserNameAndPassword(username, password);
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        if (user.isEmpty()) { throw new UsernameNotFoundException(username);}
        return new ApiUserDetails(user.get());
    }

    public UserResponseDTO changeUserName(UserNameChangeRequestDTO requestDTO) {
        Optional<User> currentUserOptional = userRepository.findByUserName(requestDTO.getCurrentUserName());
        if (currentUserOptional.isEmpty()) {
            throw new RecordNotFoundException("Current user not found");
        }

        if (userRepository.findByUserName(requestDTO.getNewUserName()).isPresent()) {
            throw new UserNameAlreadyExistsException("Chosen username already exists, please choose another one");
        } else {
            User currentUser = currentUserOptional.get();
            currentUser.setUserName(requestDTO.getNewUserName());
            User userSaved = userRepository.save(currentUser);

            return userMapper.mapToResponseDTO(userSaved);
        }
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


    public User getUserByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RecordNotFoundException("User not found");
        } else {
            return user.get();
        }
    }
}

