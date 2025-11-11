package com.sanger.gymsan.exceptions;

public class MembresiaNotEncontradaException extends RuntimeException {

    private static final long serialVersionUID = 43876691117560211L;

    public MembresiaNotEncontradaException() {
        super("No se encontro membresia existente");
    }

}
