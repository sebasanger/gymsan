package com.sanger.gymsan.exceptions;

public class MembresiaNotSucribedException extends RuntimeException {

    private static final long serialVersionUID = 43876691117560211L;

    public MembresiaNotSucribedException() {
        super("No se encuentra suscripto a esta membresia");
    }

}
