package com.sanger.gymsan.services;

public interface SoftDeletableInterface {
    boolean isDeleted();

    void setDeleted(boolean deleted);
}