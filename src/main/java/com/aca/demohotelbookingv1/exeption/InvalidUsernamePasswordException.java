package com.aca.demohotelbookingv1.exeption;

public class InvalidUsernamePasswordException extends Exception{
    public InvalidUsernamePasswordException(String message) {
        super(message);
    }
}
