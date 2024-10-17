package org.novi.languagelearner.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.novi.languagelearner.dtos.User.*;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.mappers.UserMapper;
import org.novi.languagelearner.services.PhotoService;
import org.novi.languagelearner.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;
    private final HttpServletRequest request;
    private final PhotoService photoService;


    public UserController(UserMapper userMapper, UserService userService, HttpServletRequest request, PhotoService photoService) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.request = request;
        this.photoService = photoService;
    }


    @GetMapping("/admin/{id}")
    public ResponseEntity<?> getUserInfoAsAdmin(@PathVariable("id") Long id) {

        UserResponseDTO userResponseDTO = userService.getUserResponseDTOByIdAsAdmin(id);

        return ResponseEntity.ok().body(userResponseDTO);
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<?> getAllUsersAsAdmin() {
        try {

            List<UserResponseDTO> listOfUserResponseDTOs = userService.getAll();

            return ResponseEntity.ok().body(listOfUserResponseDTOs);
        } catch (BadRequestException e) {
            throw new BadRequestException("An unforeseen error occurred while retrieving all users. System message: " + e.getMessage());
        }
    }


    @GetMapping("/admin/get-users")
    public ResponseEntity<?> getUsersLastNameAndRoleAsAdmin(@RequestParam String lastName, @RequestParam String role) {
        try {
            UserByLastNameAndRoleRequestDTO requestDTO = new UserByLastNameAndRoleRequestDTO();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userNameOfAdmin = authentication.getName();

            requestDTO.setAdminUserName(userNameOfAdmin);
            requestDTO.setLastName(lastName);
            requestDTO.setRole(role);

            // to service
            List<UserResponseDTO> responseDTOList = userService.getUserResponseDTOByLastNameAndRole(requestDTO);

            return ResponseEntity.ok().body(responseDTOList);

        } catch (BadRequestException e) {
            throw new BadRequestException("Issue with retrieving users by role and username " + e.getMessage() );
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserResponseDTO userResponseDTO = userService.getUserResponseDTOByUserName(authentication.getName());
            return ResponseEntity.ok().body(userResponseDTO);
        } catch (BadRequestException e) {
            throw new BadRequestException("Issue with getting profile " + e.getMessage());
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDTO userDTO) {
        try {

            UserResponseDTO userResponseDTO = userService.createUser(userDTO);

            return ResponseEntity.ok().body(userResponseDTO);
        } catch (BadRequestException e) {
            throw new BadRequestException("Issue with creating user " + e.getMessage());
        }
    }


    @PutMapping("/change-username")
    public ResponseEntity<?> changeUserName(@RequestBody @Valid UserNameChangeRequestDTO requestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            requestDTO.setCurrentUserName(authentication.getName());

            UserResponseDTO userResponseDTO = userService.changeUserName(requestDTO);

            return ResponseEntity.ok().body(userResponseDTO);
        } catch (BadRequestException e) {
            throw new BadRequestException("Issue with changing username " + e.getMessage());
        }
    }


    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid UserChangePasswordRequestDTO requestDTO) {
        try {
            userService.changePassword(requestDTO);

            return ResponseEntity.ok().body("Password is successfully changed");
        } catch (BadRequestException e) {
            throw new BadRequestException("Something went wrong while changing the password" + e.getMessage());
        }
    }
}
