package org.novi.languagelearner.controllers;

import org.novi.languagelearner.dtos.UserResponseDTO;
import org.novi.languagelearner.entities.User;
import org.novi.languagelearner.mappers.UserMapper;
import org.novi.languagelearner.security.JwtService;
import org.novi.languagelearner.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

// TODO: Test of User en Secure endpoints goed werken,
// TODO: RoleModel verwijderen en bugfixen
// TODO: Endpoints testen >> weggooien overbodige bestanden
// TODO: Mergen naar Main

@RestController
public class SecureController {

    private final UserMapper userMapper;
    private Authentication authentication;

    private final UserService userService;

    public SecureController(UserService userService, UserMapper userMapper, JwtService jwtService) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/secure")
    public ResponseEntity<String> getSecureData() {
         setAuthentication(SecurityContextHolder.getContext());
         return ResponseEntity.ok("Dit is beveiligde data: " + authentication.getName());
    }

    private void setAuthentication(SecurityContext context) {
        this.authentication =context.getAuthentication();
    }

    @GetMapping("/secure/admin")
    public ResponseEntity<String> getAdminData() {
        setAuthentication(SecurityContextHolder.getContext());
        return ResponseEntity.ok("Dit is beveiligde admin data: " + authentication.getName());
    }


    @GetMapping("/secure/user")
    public ResponseEntity<?> getUserData() {
        setAuthentication(SecurityContextHolder.getContext());

        Optional<User> user = userService.getUserByUserName(authentication.getName());
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            UserResponseDTO userResponseDTO = userMapper.mapToResponseDTO(user.get());
            return ResponseEntity.ok().body(userResponseDTO);
        }
    }

//    @GetMapping("/secure/user")
//    public ResponseEntity<UserResponseDTO> getUserData(@RequestHeader("Authorization") String token) {
//        setAuthentication(SecurityContextHolder.getContext());
//
//        String username = jwtService.extractUsername(token);
//
//        Optional<UserModel> userModel = userService.getUserByUserName(username);
//
//        UserResponseDTO userResponseDTO = userDTOMapper.mapToDTO(userModel.get());
//        System.out.println(authentication.getName());
//
//        return ResponseEntity.ok(userResponseDTO);
//    }
}
