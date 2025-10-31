package com.sanger.gymsan.exceptions;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2730111931833397903L;

    public UserNotFoundException() {
        super("No se encontraron usuarios");
    }

    public UserNotFoundException(Long id) {
        super("No se ha encontrado ningun usuario con el ID " + id);
    }

}
