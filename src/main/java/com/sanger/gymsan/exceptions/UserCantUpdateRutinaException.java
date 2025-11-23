package com.sanger.gymsan.exceptions;

public class UserCantUpdateRutinaException extends RuntimeException {

    private static final long serialVersionUID = 43876691117560211L;

    public UserCantUpdateRutinaException() {
        super("Si no se es administrador o entrenador no se puede actualizar la rutina predeterminada");
    }

}
