package kz.nearbygems.chat.exceptions;

public class AuthException extends RuntimeException {

    public AuthException() {
        super("You haven't logged in yet.\n");
    }

}
