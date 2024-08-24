package org.novi.languagelearner.controllers;

import org.novi.languagelearner.dtos.Stats.StatsResponseDTO;
import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.mappers.StatsMapper;
import org.novi.languagelearner.services.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/stats")
public class StatsController {


    private final StatsService statsService;
    private final StatsMapper statsMapper;


    public StatsController(StatsService statsService, StatsMapper statsMapper) {
        this.statsService = statsService;
        this.statsMapper = statsMapper;
    }

    @GetMapping
    public ResponseEntity<?> getStats(@RequestParam Long exerciseId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            StatsResponseDTO statsResponseDTO = statsService.getUserInputAnswersByUser(userName, exerciseId);

            return ResponseEntity.ok(statsResponseDTO);

        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("getStats failed: " + e.getMessage());
        }
    }






}
