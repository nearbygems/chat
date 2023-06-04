package kz.nearbygems.chat.exceptions;

public class JoinValidationException extends ChatException {

    public JoinValidationException() {
        super("Please, write chat name without spaces.\n");
    }

}
