package org.novi.languagelearner.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.novi.languagelearner.dtos.UserChangePasswordRequestDTO;
import org.novi.languagelearner.mappers.UserDTOMapper;
import org.novi.languagelearner.dtos.UserRequestDTO;
import org.novi.languagelearner.helpers.UrlHelper;
import org.novi.languagelearner.models.UserModel;
import org.novi.languagelearner.security.JwtService;
import org.novi.languagelearner.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserDTOMapper userDTOMapper;
    private final UserService userService;
    private final HttpServletRequest request;
    private final JwtService jwtService;

    public UserController(UserDTOMapper userDTOMapper, UserService userService, HttpServletRequest request, JwtService jwtService) {
        this.userDTOMapper = userDTOMapper;
        this.userService = userService;
        this.request = request;
        this.jwtService = jwtService;
    }

    // Getmapping getUserInfo
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id) {
        Optional<UserModel> userModel = userService.getUserById(id);
        // make usermodel to userResponseDTO
        if(userModel.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var userResponseDTO = userDTOMapper.mapToDTO(userModel.get());
        return ResponseEntity.ok().body(userResponseDTO);
    }

    // previously /users
    @PostMapping
    public ResponseEntity<?> CreateUser(@RequestBody @Valid UserRequestDTO userDTO)
    {
        var userModel = userDTOMapper.mapToModel(userDTO);
        userModel.setEnabled(true);
        if(!userService.createUser(userModel, userDTO.getRoles())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(UrlHelper.getCurrentUrlWithId(request, userModel.getId())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> ChangePassword(@PathVariable Long id, @RequestBody @Valid UserChangePasswordRequestDTO userDTO)
    {
        var userModel = userDTOMapper.mapToModel(userDTO, id);
        if(!userService.updatePassword(userModel)) {
            return ResponseEntity.badRequest().build();
        }
        return  ResponseEntity.ok().build();
    }
}
