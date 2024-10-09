package org.novi.languagelearner.services;

import jakarta.transaction.Transactional;
import org.novi.languagelearner.dtos.User.*;
import org.novi.languagelearner.entities.Role;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.exceptions.AccessDeniedException;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.exceptions.UserNameAlreadyExistsException;
import org.novi.languagelearner.mappers.RoleMapper;
import org.novi.languagelearner.mappers.UserMapper;
import org.novi.languagelearner.repositories.RoleRepository;
import org.novi.languagelearner.repositories.UserRepository;
import org.novi.languagelearner.security.ApiUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        for (Role role : user.getRoles()) {
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

    public Long getUserIdByUserName(String userName) {
        User user = getUserByUserName(userName);

        return user.getId();
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
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
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

    @Transactional
    public void changePassword(UserChangePasswordRequestDTO requestDTO) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Username: %s, not found in database", userName)));

        user.setPassword(bCryptPasswordEncoder.encode(requestDTO.getPassword()));

        userRepository.save(user);
    }



    public User getUserByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RecordNotFoundException("User not found");
        } else {
            return user.get();
        }
    }

    public List<UserResponseDTO> getUserResponseDTOByLastNameAndRole(UserByLastNameAndRoleRequestDTO requestDTO) {

        // TODO: Ask Frans: I get a toString() error here. Probably infinite recursion User <> Role. Tried 'reference' annotations etc. but didn't work. Unidirectional relation no dice. Result ResponseEntity is fine though :O
        Optional<User> userOptional = userRepository.findUserByUserName(requestDTO.getAdminUserName());

        if(userOptional.isEmpty()) {
            throw new RecordNotFoundException("Username not found for Admin check");
        }

        User userAdmin = userOptional.get();

        if( !isUserAdmin(userAdmin) ) {
            throw new AccessDeniedException("User is no admin");
        }

        List<User> userResults = userRepository.findByLastNameContainingIgnoreCaseAndRoles_RoleName(requestDTO.getLastName(), requestDTO.getRole());

        List<UserResponseDTO> responseDTOs = userMapper.mapToListOfResponseDTOs(userResults);

        if (responseDTOs.isEmpty()) {
            throw new RecordNotFoundException("No users found with given criteria. Last name: " + requestDTO.getLastName() + " and role: " + requestDTO.getRole());
        }

        return responseDTOs;
    }

    public boolean isUserAdmin(User userAdmin) {
        List<Role> roles = userAdmin.getRoles();

        for (Role role : roles) {
            if (role.getRoleName().equals("ROLE_ADMIN")) {
                return true;
            }
        };

        return false;
    }


    public List<UserResponseDTO> getAll() {

        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new RecordNotFoundException("No users found in repository");
        }

        return userMapper.mapToListOfResponseDTOs(users);
    }
}

