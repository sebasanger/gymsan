package com.sanger.gymsan.exceptions;

public class NewUserWithDifferentPasswordsException extends RuntimeException {

    private static final long serialVersionUID = -7978601526802035152L;

    public NewUserWithDifferentPasswordsException() {
        super("Password not missmatch");
    }

}
