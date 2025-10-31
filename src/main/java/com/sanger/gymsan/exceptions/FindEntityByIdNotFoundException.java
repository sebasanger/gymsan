package com.sanger.gymsan.exceptions;

public class FindEntityByIdNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 43876691117560211L;

    public FindEntityByIdNotFoundException(Long id) {
        super("Cannot find entity with ID: " + id);
    }

    public FindEntityByIdNotFoundException() {
        super("No results can be found");
    }

}
