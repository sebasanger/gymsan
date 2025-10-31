package com.sanger.gymsan.exceptions;

public class SearchEntityNoResultException extends RuntimeException {

    private static final long serialVersionUID = -889312292404205516L;

    public SearchEntityNoResultException() {
        super("Entity search produced no results");
    }

    public SearchEntityNoResultException(String txt) {
        super(String.format("Search term% s produced no results", txt));
    }

}
