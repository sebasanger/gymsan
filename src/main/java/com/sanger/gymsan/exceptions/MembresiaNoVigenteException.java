package com.sanger.gymsan.exceptions;

public class MembresiaNoVigenteException extends RuntimeException {

    private static final long serialVersionUID = 43876691117560211L;

    public MembresiaNoVigenteException() {
        super("Membresia no vigente");
    }

}
