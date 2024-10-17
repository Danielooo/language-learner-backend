package org.novi.languagelearner.exceptions;

import java.io.Serial;

public class UserNameAlreadyExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserNameAlreadyExistsException() {
        super();
    }

    public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}
