package com.sanger.gymsan.exceptions;

public class UltimoCheckInNoRegistradoException extends RuntimeException {

    private static final long serialVersionUID = 43876691117560211L;

    public UltimoCheckInNoRegistradoException() {
        super("No se realizo el ultimo check out");
    }

}
