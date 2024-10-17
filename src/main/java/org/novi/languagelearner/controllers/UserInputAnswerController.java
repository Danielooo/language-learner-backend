package org.novi.languagelearner.controllers;

import jakarta.validation.Valid;
import org.novi.languagelearner.dtos.UserInputAnswer.UserInputAnswerRequestDTO;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.services.UserInputAnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// TODO: Change to /user-input-answer
@Controller
@RequestMapping("/answer")
public class UserInputAnswerController {

    private final UserInputAnswerService userInputAnswerService;

    public UserInputAnswerController(UserInputAnswerService userInputAnswerService) {
        this.userInputAnswerService = userInputAnswerService;
    }


    @PostMapping("/user/submit")
    public ResponseEntity<?> submitAnswer(@RequestBody @Valid UserInputAnswerRequestDTO userInputAnswerRequestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userInputAnswerRequestDTO.setUserName(authentication.getName());

            return ResponseEntity.ok(userInputAnswerService.submitAnswer(userInputAnswerRequestDTO));

        } catch (BadRequestException e) {
            throw new BadRequestException("Something went wrong with submitting the answer " + e.getMessage());
        }
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteUserInputAnswer(@PathVariable Long id) {
        try {

            userInputAnswerService.deleteAnswer(id);
            return ResponseEntity.ok().body(String.format("Answer with id %d is deleted", id));


        } catch (Exception e) {
            throw new BadRequestException("Something went wrong de deleting the user input answer" + e.getMessage());
        }
    }
}

