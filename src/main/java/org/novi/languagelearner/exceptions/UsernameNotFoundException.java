package org.novi.languagelearner.exceptions;

import java.io.Serial;

public class UsernameNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException() {
        super();
    }

    public UsernameNotFoundException(String username) {
        super("Can't find user " + username);
    }


}