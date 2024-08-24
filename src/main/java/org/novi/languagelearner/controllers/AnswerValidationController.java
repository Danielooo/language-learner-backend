//package org.novi.languagelearner.controllers;
//
//import org.novi.languagelearner.dtos.Unsorted.AnswerValidationRequestDTO;
//import org.novi.languagelearner.dtos.Unsorted.AnswerValidationResponseDTO;
//import org.novi.languagelearner.exceptions.BadRequestException;
//import org.novi.languagelearner.services.AnswerValidationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequestMapping("/answer-validation") // beter answer want verwijzend naar de entiteit >> resource based
//public class AnswerValidationController {
//
//    public final AnswerValidationService answerValidationService;
//
//    @Autowired
//    public AnswerValidationController(AnswerValidationService answerValidationService) {
//        this.answerValidationService = answerValidationService;
//    }
//
//    // TODO: UserInputAnswer Frans; als ik het de frontend makkelijk wil maken is het dan logisch om de id in de url te hebben, of moet de FE dit hebben en meesturen? >> moet naar entity UserInputAnswer, maakt steeds een nieuwe entity UserInputAnswer aan met 'userInput' en 'timeStamp' // Is het handig om de AnswerValidationRequestDTO hier aan te vullen met userName en exerciseId? >> Nee, dit wordt dus een enkele json (RequestBody) die gepersist wordt
//    @PostMapping("/{exerciseId}")   // een op veel naar UserInputAnswer
//    public ResponseEntity<?> processAnswer(@PathVariable Long exerciseId, @RequestBody AnswerValidationRequestDTO answerValidationRequestDTO) {
//
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String userName = authentication.getName();
//
//            answerValidationRequestDTO.setExerciseId(exerciseId);
//            answerValidationRequestDTO.setUserName(userName);
//
//            AnswerValidationResponseDTO answerValidationResponseDTO = answerValidationService.processAnswer(answerValidationRequestDTO);
//
//            return ResponseEntity.ok().body(answerValidationResponseDTO);
//        } catch (BadRequestException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//
//
//    }
//}
