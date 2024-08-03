package org.novi.languagelearner.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.novi.languagelearner.dtos.UserChangePasswordRequestDTO;
import org.novi.languagelearner.dtos.UserDTOMapper;
import org.novi.languagelearner.dtos.UserRequestDTO;
import org.novi.languagelearner.dtos.UserResponseDTO;
import org.novi.languagelearner.helpers.UrlHelper;
import org.novi.languagelearner.security.JwtService;
import org.novi.languagelearner.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// added requestmapping, was empty before
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

    // TODO: getUserInfo schrijven

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); // Remove "Bearer " prefix
        String username = jwtService.extractUsername(token);
        var user = userService.loadUserByUsername(username);
        var userResponseDTO = userDTOMapper.mapToDTO(user);
        return ResponseEntity.ok(userResponseDTO);
    }  }

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
