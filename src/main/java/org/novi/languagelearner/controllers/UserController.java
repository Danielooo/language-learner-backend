package org.novi.languagelearner.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.novi.languagelearner.dtos.UserChangePasswordRequestDTO;
import org.novi.languagelearner.dtos.UserRequestDTO;
import org.novi.languagelearner.dtos.UserResponseDTO;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.helpers.UrlHelper;
import org.novi.languagelearner.mappers.UserMapper;
import org.novi.languagelearner.security.JwtService;
import org.novi.languagelearner.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;
    private final HttpServletRequest request;


    public UserController(UserMapper userMapper, UserService userService, HttpServletRequest request, JwtService jwtService) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.request = request;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if(user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserResponseDTO userResponseDTO = userMapper.mapToResponseDTO(user.get());
        return ResponseEntity.ok().body(userResponseDTO);
    }

    @PostMapping
    public ResponseEntity<?> CreateUser(@RequestBody @Valid UserRequestDTO userDTO)
    {
        var user = userMapper.mapToEntity(userDTO);
        user.setEnabled(true);
        if(!userService.createUser(user, userDTO.getRoles())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(UrlHelper.getCurrentUrlWithId(request, user.getId())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> ChangePassword(@PathVariable Long id, @RequestBody @Valid UserChangePasswordRequestDTO userDTO)
    {
        var user = userMapper.mapToEntity(userDTO, id);
        if(!userService.updatePassword(user)) {
            return ResponseEntity.badRequest().build();
        }
        return  ResponseEntity.ok().build();
    }


}
