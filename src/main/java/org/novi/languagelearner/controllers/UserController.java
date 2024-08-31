package org.novi.languagelearner.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.novi.languagelearner.dtos.User.UserNameChangeRequestDTO;
import org.novi.languagelearner.dtos.User.UserRequestDTO;
import org.novi.languagelearner.dtos.User.UserResponseDTO;
import org.novi.languagelearner.dtos.User.UserByLastNameAndRoleRequestDTO;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.mappers.UserMapper;
import org.novi.languagelearner.services.PhotoService;
import org.novi.languagelearner.services.UserService;
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

    // TODO: Add putmapping for updating user info >> username has to stay unique
    // TODO: Add admin endpoints >> also delete user

    @GetMapping("/admin/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable("id") Long id) {

        UserResponseDTO userResponseDTO = userService.getUserResponseDTOByIdAsAdmin(id);

        return ResponseEntity.ok().body(userResponseDTO);
    }

    // GetMapping admin getAllUsers

    // GetMapping admin getUsersByRoleAndName
    @GetMapping("/admin/get-users")
    public ResponseEntity<?> getUsersLastNameAndRole( @RequestParam String lastName, @RequestParam String role) {
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
            return ResponseEntity.badRequest().body("Issue with retrieving users by role and username");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserResponseDTO userResponseDTO = userService.getUserResponseDTOByUserName(authentication.getName());
            return ResponseEntity.ok().body(userResponseDTO);
        } catch (BadRequestException e) {
            throw new BadRequestException("Issue with getting profile");
        }
    }


    // TODO: make username has to be unique. First check if username exists, then create user
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDTO userDTO) {
        try {

            UserResponseDTO userResponseDTO = userService.createUser(userDTO);

            return ResponseEntity.ok().body(userResponseDTO);
        } catch (BadRequestException e) {
            throw new BadRequestException("Issue with creating user");
        }
    }


    @PutMapping("/change")
    public ResponseEntity<?> changeUserName(@RequestBody @Valid UserNameChangeRequestDTO requestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            requestDTO.setCurrentUserName(authentication.getName());

            UserResponseDTO userResponseDTO = userService.changeUserName(requestDTO);

            return ResponseEntity.ok().body(userResponseDTO);
        } catch (BadRequestException e) {
            throw new BadRequestException("Issue with changing username");
        }
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody @Valid UserChangePasswordRequestDTO userDTO) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        var user = userMapper.mapToEntity(userDTO, id);
//        if (!userService.updatePassword(user)) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok().build();
//    }


}
