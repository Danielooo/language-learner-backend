package org.novi.languagelearner.controllers;

import org.novi.languagelearner.dtos.UserResponseDTO;
import org.novi.languagelearner.mappers.UserDTOMapper;
import org.novi.languagelearner.models.UserModel;
import org.novi.languagelearner.security.JwtService;
import org.novi.languagelearner.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class SecureController {

    private Authentication authentication;

    private UserService userService;
    private UserDTOMapper userDTOMapper;
    private JwtService jwtService;

    public SecureController(UserService userService, UserDTOMapper userDTOMapper, JwtService jwtService) {
        this.userService = userService;
        this.userDTOMapper = userDTOMapper;
        this.jwtService = jwtService;
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
    public ResponseEntity<UserResponseDTO> getUserData(@RequestHeader("Authorization") String token) {
        setAuthentication(SecurityContextHolder.getContext());

        String username = jwtService.extractUsername(token);

        Optional<UserModel> userModel = userService.getUserByUserName(username);

        UserResponseDTO userResponseDTO = userDTOMapper.mapToDTO(userModel.get());
        System.out.println(authentication.getName());

        return ResponseEntity.ok(userResponseDTO);
    }
}
