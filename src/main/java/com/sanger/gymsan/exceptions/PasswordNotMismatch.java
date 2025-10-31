package com.sanger.gymsan.exceptions;

public class PasswordNotMismatch extends RuntimeException {

    private static final long serialVersionUID = -7978601526802035152L;

    public PasswordNotMismatch() {
        super("Las contraseñas no coinciden");
    }

    public PasswordNotMismatch(boolean old) {
        super("El password anteriror es incorrecto");
    }

}
