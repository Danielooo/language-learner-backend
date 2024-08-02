package org.novi.languagelearner.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @GetMapping("/public")
    public ResponseEntity<String> getPublicData()
    {
        return ResponseEntity.ok("Dit is publieke data : <root>");
    }

    @GetMapping("/public/start")
    public ResponseEntity<String> getStartPublicData()
    {
        return ResponseEntity.ok("Dit is publieke data : start");
    }

    @GetMapping("/public/more")
    public ResponseEntity<String> getMorePublicData()
    {
        return ResponseEntity.ok("Dit is publieke data : more");
    }
}

