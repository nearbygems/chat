package kz.nearbygems.chat.exceptions;

public class AlreadyAuthException extends RuntimeException {

    public AlreadyAuthException() {
        super("You've already logged in.\n");
    }

}
