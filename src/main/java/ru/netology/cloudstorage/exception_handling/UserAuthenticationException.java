package ru.netology.cloudstorage.exception_handling;

public class UserAuthenticationException extends RuntimeException{

    public UserAuthenticationException(String message) {
        super(message);
    }
}
