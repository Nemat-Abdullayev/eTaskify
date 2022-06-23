package com.etaskify.exception.custom;

public class RefreshTokenInvalidException extends RuntimeException{
    public RefreshTokenInvalidException(String message){
        super(message);
    }
}
