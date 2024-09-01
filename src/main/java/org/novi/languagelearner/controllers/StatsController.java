package org.novi.languagelearner.controllers;

import jakarta.validation.Valid;
import org.novi.languagelearner.dtos.Stats.StatsParamRequestDTO;
import org.novi.languagelearner.dtos.Stats.StatsParamResponseDTO;
import org.novi.languagelearner.dtos.Stats.StatsOfExerciseResponseDTO;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.mappers.StatsMapper;
import org.novi.languagelearner.services.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;


@RestController
@RequestMapping("/stats")
public class  StatsController {


    private final StatsService statsService;
    private final StatsMapper statsMapper;


    public StatsController(StatsService statsService, StatsMapper statsMapper) {
        this.statsService = statsService;
        this.statsMapper = statsMapper;
    }

    // edit or delete, has to return stats as question, answer, creationdate, editdate, timesright out of total, lastresult (right or wrong and timestamp)
    @GetMapping("/user/exercise/{exerciseId}")
    public ResponseEntity<?> getStatsOfExercise(@PathVariable Long exerciseId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            StatsOfExerciseResponseDTO statsResponseDTO = statsService.getStatsOfExercise(userName, exerciseId);

            return ResponseEntity.ok(statsResponseDTO);

        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("getStats failed: " + e.getMessage());
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }


    // get stats of group owned by user
    @GetMapping("user")
    public ResponseEntity<?> getStatsViaParams(@Valid StatsParamRequestDTO statsParamRequestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            statsParamRequestDTO.setUserName(userName);

            List<StatsParamResponseDTO> responseDTOList = statsService.getStatsByParams(statsParamRequestDTO);

            return ResponseEntity.ok().body(responseDTOList);

        } catch (BadRequestException e) {

            return ResponseEntity.badRequest().body("Something went wrong while getting stats of user ");
            // TODO: Ask Frans; waarom moet ik AccesDenied clausule er hier bij doen (throw m in de service). Krijg foutmelding zonder
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }
}









