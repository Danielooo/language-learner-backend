package org.novi.languagelearner.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.novi.languagelearner.dtos.UserChangePasswordRequestDTO;
import org.novi.languagelearner.dtos.UserRequestDTO;
import org.novi.languagelearner.dtos.UserResponseDTO;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.helpers.UrlHelper;
import org.novi.languagelearner.mappers.UserMapper;
import org.novi.languagelearner.security.JwtService;
import org.novi.languagelearner.services.PhotoService;
import org.novi.languagelearner.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserResponseDTO userResponseDTO = userMapper.mapToResponseDTO(user.get());
        return ResponseEntity.ok().body(userResponseDTO);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDTO userDTO) {
        var user = userMapper.mapToEntity(userDTO);
        user.setEnabled(true);
        if (!userService.createUser(user, userDTO.getRoles())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(UrlHelper.getCurrentUrlWithId(request, user.getId())).build();
    }

    // TODO: This is the method for uploading a photo as a user. It is not yet implemented in the service layer.
    @PostMapping("/photo")
    public ResponseEntity<?> uploadPhoto(@RequestBody MultipartFile file) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            userService.uploadPhoto(username, file);

            return ResponseEntity.ok().body("Photo succesfully added to user: " + username);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO: Create method for an admin to change the photo of a user. userId as Pathvariable and photo as RequestBody
    @PostMapping("/{userId}/photo")
    public ResponseEntity<?> uploadPhotoAsAdmin(@PathVariable Long userId, @RequestBody MultipartFile file) {
        try {

            String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/users/{id}/photo")
                    .path(Objects.requireNonNull(userId.toString()))
                    .path("/photo")
                    .toUriString();


            String filename = photoService.storeFile(file);
            User user = userService.assignPhotoToUser(filename, userId);

            userService.uploadPhotoAsAdmin(userId, file);

            return ResponseEntity.ok().body("Photo succesfully added to user with id: " + userId);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody @Valid UserChangePasswordRequestDTO userDTO) {
        var user = userMapper.mapToEntity(userDTO, id);
        if (!userService.updatePassword(user)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }


}
