package org.novi.languagelearner.controllers;

import org.novi.languagelearner.exceptions.BadRequestException;
import org.novi.languagelearner.exceptions.RecordNotFoundException;
import org.novi.languagelearner.exceptions.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    // TODO: Frans answered; implementeer zoveel mogelijk exceptions, maar ook RuntimeException, dat is de 'hoogste' en vangt af als er de specifieke exception niet is gedeclareerd. @ControllerAdvice zorgt ervoor dat er een try/catch om het hele project heen staat.
    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<Object> exception(RecordNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = IndexOutOfBoundsException.class)
    public ResponseEntity<Object> exception(IndexOutOfBoundsException exception) {

        return new ResponseEntity<>("Dit id staat niet in de database", HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<String> exception(BadRequestException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<String> exception(UsernameNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

    }

}