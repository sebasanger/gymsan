package com.sanger.gymsan.exceptions;

public class EntitiesNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 43876691117560211L;

    public EntitiesNotFoundException() {
        super("No se encontraron resultados");
    }

}
