package kz.nearbygems.chat.exceptions;

public class IncorrectLoginException extends ChatException {

    public IncorrectLoginException() {
        super("Wrong username or password.\n");
    }

}
