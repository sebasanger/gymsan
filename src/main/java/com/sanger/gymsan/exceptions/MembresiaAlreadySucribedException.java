package com.sanger.gymsan.exceptions;

public class MembresiaAlreadySucribedException extends RuntimeException {

    private static final long serialVersionUID = 43876691117560211L;

    public MembresiaAlreadySucribedException() {
        super("Ya se encuentra suscripto a esta membresia");
    }

}
