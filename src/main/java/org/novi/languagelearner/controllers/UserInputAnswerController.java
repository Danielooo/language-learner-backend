package org.novi.languagelearner.controllers;

import org.novi.languagelearner.dtos.UserInputAnswer.UserInputAnswerRequestDTO;
import org.novi.languagelearner.services.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/answer")
public class UserInputAnswerController {

    private final AnswerService answerService;

    public UserInputAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    // TODO: Feature build; getAnswers of UserController
    // TODO: Feature build; create AnswersOfUserRequestDTO
    // TODO: Feature build; create UserInputAnswerService
    // TODO: Feature build; create UserInputAnswerRepository @Query params userName and exerciseId



    @PostMapping
    public ResponseEntity<?> submitAnswer(@RequestBody UserInputAnswerRequestDTO userInputAnswerRequestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userInputAnswerRequestDTO.setUserName(authentication.getName());

            return ResponseEntity.ok(answerService.submitAnswer(userInputAnswerRequestDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

