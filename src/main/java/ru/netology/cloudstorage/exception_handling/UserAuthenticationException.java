package ru.netology.cloudstorage.exception_handling;

/**
 * @author VladSemikin
 */

public class UserAuthenticationException extends RuntimeException{

    public UserAuthenticationException(String message) {
        super(message);
    }
}
