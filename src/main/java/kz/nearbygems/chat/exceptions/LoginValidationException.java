package kz.nearbygems.chat.exceptions;

public class LoginValidationException extends RuntimeException {
    public LoginValidationException() {
        super("Please, write your login and password divided by space.");
    }
}
