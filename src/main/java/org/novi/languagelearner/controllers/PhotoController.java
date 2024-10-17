package org.novi.languagelearner.controllers;


import jakarta.validation.Valid;
import org.novi.languagelearner.dtos.Photo.PhotoRequestDTO;
import org.novi.languagelearner.dtos.Photo.PhotoResponseDTO;
import org.novi.languagelearner.dtos.User.UserResponseDTO;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.services.PhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/photo")
public class PhotoController {


    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }



    @PostMapping
    public ResponseEntity<?> singleFileUpload(@ModelAttribute PhotoRequestDTO photoRequestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            photoRequestDTO.setUserName(authentication.getName());
            photoRequestDTO.setFileName(photoRequestDTO.getPhotoData().getOriginalFilename());
            photoRequestDTO.setFileType(photoRequestDTO.getPhotoData().getContentType());

            UserResponseDTO userResponseDTO = photoService.uploadPhoto(photoRequestDTO);
            return ResponseEntity.ok().body(userResponseDTO);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("No upload possible");
        }
    }

    @GetMapping
    public ResponseEntity<?> getPhoto() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            PhotoResponseDTO photoResponseDTO = photoService.getPhoto(userName);
            return ResponseEntity.ok().body(photoResponseDTO);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body("No photo found");
        }
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadPhoto() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            PhotoResponseDTO photoResponseDTO = photoService.getPhoto(userName);
            byte[] photoData = photoResponseDTO.getData();

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + photoResponseDTO.getFileName() + "\"")
                    .header("Content-Type", photoResponseDTO.getFileType())
                    .body(photoData);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body("No photo found");
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deletePhoto() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            photoService.deletePhoto(userName);
            return ResponseEntity.ok().body("Photo deleted");
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body("No photo found");
        }
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deletePhotoAsAdmin(@PathVariable Long id) {
        try {

            String message = photoService.deletePhotoAsAdmin(id);

            return ResponseEntity.ok().body(message);

        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body("Something went wrong with deleting photo with id: " + id);
        }
    }
}
