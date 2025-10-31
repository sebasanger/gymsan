package com.sanger.gymsan.exceptions;

public class TokenInvalidException extends RuntimeException {

    private static final long serialVersionUID = -7978601526802035152L;

    public TokenInvalidException() {
        super("El token no es valido");
    }

}
