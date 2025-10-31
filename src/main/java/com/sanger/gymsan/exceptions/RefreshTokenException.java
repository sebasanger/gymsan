package com.sanger.gymsan.exceptions;

public class RefreshTokenException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 3342034462033626819L;

    public RefreshTokenException() {
        super("Error al resfrescar el token");
    }

}
