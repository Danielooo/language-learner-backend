package org.novi.languagelearner.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.novi.languagelearner.dtos.UserChangePasswordRequestDTO;
import org.novi.languagelearner.dtos.UserRequestDTO;
import org.novi.languagelearner.helpers.UrlHelper;
import org.novi.languagelearner.mappers.UserMapper;
import org.novi.languagelearner.models.UserModel;
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

    // Getmapping getUserInfo
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id) {
        Optional<UserModel> userModel = userService.getUserById(id);
        // make usermodel to userResponseDTO
        if(userModel.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var userResponseDTO = userMapper.mapToResponseDTO(userModel.get());
        return ResponseEntity.ok().body(userResponseDTO);
    }

    // previously /users
    @PostMapping
    public ResponseEntity<?> CreateUser(@RequestBody @Valid UserRequestDTO userDTO)
    {
        var userModel = userMapper.mapToModel(userDTO);
        userModel.setEnabled(true);
        if(!userService.createUser(userModel, userDTO.getRoles())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(UrlHelper.getCurrentUrlWithId(request, userModel.getId())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> ChangePassword(@PathVariable Long id, @RequestBody @Valid UserChangePasswordRequestDTO userDTO)
    {
        var userModel = userMapper.mapToModel(userDTO, id);
        if(!userService.updatePassword(userModel)) {
            return ResponseEntity.badRequest().build();
        }
        return  ResponseEntity.ok().build();
    }


}
