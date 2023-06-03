package kz.nearbygems.chat.exceptions;

public class IncorrectLoginException extends RuntimeException {

    public IncorrectLoginException() {
        super("Wrong username or password.\n");
    }

}
