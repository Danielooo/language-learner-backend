package org.novi.languagelearner.controllers;


import jakarta.validation.Valid;
import org.novi.languagelearner.dtos.Photo.PhotoRequestDTO;
import org.novi.languagelearner.dtos.User.UserResponseDTO;
import org.novi.languagelearner.services.PhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
public class PhotoController {


    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }




    // TODO: create GET, PUT, DELETE mappings for photo

    @PostMapping("/upload/photo")
    public ResponseEntity<?> singleFileUpload(@Valid @ModelAttribute  PhotoRequestDTO photoRequestDTO) throws IOException {
        try {
            // get authenticated username
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            photoRequestDTO.setUserName(authentication.getName());

            // return user with photo; for testing purposes
            UserResponseDTO userResponseDTO = photoService.uploadPhoto(photoRequestDTO);
            return ResponseEntity.ok().body(userResponseDTO);
        } catch (Exception e) {
              return ResponseEntity.badRequest().body("No upload possible");
        }
    }

}
