package org.novi.languagelearner.controllers;


import jakarta.validation.Valid;
import org.novi.languagelearner.dtos.Unsorted.PhotoRequestDTO;
import org.novi.languagelearner.dtos.Unsorted.UserResponseDTO;
import org.novi.languagelearner.services.PhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
public class UploadDownloadController {


    private final PhotoService photoService;

    public UploadDownloadController(PhotoService photoService) {
        this.photoService = photoService;
    }


    // TODO: create Postmapping for authenticated user to upload photo
    // TODO: make PhotoRequestDTO
    // TODO: make PhotoResponseDTO
    // TODO: make photo return with id
    // TODO: make get request to check if photo was persisted and can be returned



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

    // PostMapping >>
}
