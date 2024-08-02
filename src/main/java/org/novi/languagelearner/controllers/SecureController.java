package org.novi.languagelearner.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureController {

    private Authentication authentication;

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
    public ResponseEntity<String> getUserData() {
        setAuthentication(SecurityContextHolder.getContext());
        return ResponseEntity.ok("Dit is beveiligde user data: " + authentication.getName());
    }
}
