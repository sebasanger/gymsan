package com.sanger.gymsan.exceptions;

public class TokenExpiredException extends RuntimeException {

    private static final long serialVersionUID = -7978601526802035152L;

    public TokenExpiredException() {
        super("El token esta expirado");
    }

}
